//
//  NetworkInject.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/7.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "NetworkInject.h"
#import "Safencrypt.h"
#import "SAES.h"

@interface NetworkInject()

@end

@implementation NetworkInject

+ (BOOL)canInitWithRequest:(NSURLRequest *)request{
    // 防止NSURLProtocol的死循环
    if ([NSURLProtocol propertyForKey:@"protocolKey" inRequest:request]) {
        return NO;
    }
    
    NSArray<NSString *> *requestTypeNameArr = @[@"申请公钥串",@"注册客户端",@"基于客户端",@"基于用户"];
    NSString *urlStr = [[request URL] absoluteString];
    if (![Safencrypt isNeedEncrypt: urlStr]){
        return NO;
    }
    NSInteger reqType = [Safencrypt queryRequestType: urlStr];
    [LogUtil info: [NSString stringWithFormat: @"监测到【%@】的请求 - %@", requestTypeNameArr[(reqType - 1)] ,urlStr]];
    return reqType > 2 ? YES : NO;// 3 4 是基于客户端和用户的请求，需要加密
}

- (void)startLoading{
    NSMutableURLRequest *newReq = [self.request mutableCopy];
    NSData *encryptedBody = [SAES encryptWithData: [newReq HTTPBody] Key: [Safencrypt getIdentifier]];
    // 设置标识，方式NSProtocol死循环拦截
    [NSURLProtocol setProperty:@(YES) forKey: @"protocolKey" inRequest:newReq];
    // 创建一个新的NSURLSession进行发送
    NSURLSessionConfiguration *config = [NSURLSessionConfiguration ephemeralSessionConfiguration];
    NSOperationQueue *mainQueue = [NSOperationQueue mainQueue];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:config delegate:self delegateQueue:mainQueue];
    NSURLSessionDataTask *task = [session dataTaskWithRequest:newReq];
    [task resume];
}

-(void)stopLoading{
    
}

+ (NSURLRequest *)canonicalRequestForRequest:(NSURLRequest *)request{
    return request;
}


-(void)URLSession:(NSURLSession *)session task:(NSURLSessionTask *)task didCompleteWithError:(NSError *)error {
    if (error) {
        [self.client URLProtocol:self didFailWithError:error];
    } else {
        [self.client URLProtocolDidFinishLoading:self];
    }
}

-(void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveResponse:(NSURLResponse *)response completionHandler:(void (^)(NSURLSessionResponseDisposition))completionHandler {
    [self.client URLProtocol:self didReceiveResponse:response cacheStoragePolicy:NSURLCacheStorageNotAllowed];
    completionHandler(NSURLSessionResponseAllow);
}

-(void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveData:(NSData *)data {
    NSError *respJSONReadError = nil;
    // 将Safencrypt报文格式数据解析成字典
    NSDictionary *respData = [NSJSONSerialization JSONObjectWithData: data options: NSJSONReadingMutableContainers error: &respJSONReadError];
    // 将safencrypt中规定的两个字段取出来
    NSNumber *type = respData[@"type"];
    NSString *contentStr = respData[@"data"];
    // 如果字段数量为2、并且type值为 > 0的有效整数 且 respData不为空的时候认为响应为safencrypt报文响应
    if (!respJSONReadError && [respData count] == 2 && type && [type integerValue] > 0 && respData) {
        // 根据type类型3 、 4判断是基于客户端加密还是基于用户加密，然后根据type取密钥进行解密
        NSString *decryptContentStr = [SAES decryptWithStr: contentStr Key: ([type integerValue] == 3 ? [Safencrypt getIdentifier] : [Safencrypt getUToken])];
        // 将解密后的数据交给请求者，继续操作
        [self.client URLProtocol:self didLoadData: [decryptContentStr dataUsingEncoding: NSUTF8StringEncoding]];
        return;
    }
    [self.client URLProtocol:self didLoadData:data];
}

- (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask willCacheResponse:(NSCachedURLResponse *)proposedResponse completionHandler:(void (^)(NSCachedURLResponse *cachedResponse))completionHandler{
    completionHandler(proposedResponse);
}


@end
