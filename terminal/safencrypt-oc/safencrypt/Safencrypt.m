//
//  safencrypt.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/7.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "Safencrypt.h"
#import "SRSA.h"
#import "StringUtil.h"
#import "SAES.h"

@implementation Safencrypt

static NSObject<SafencryptDelegate> * _delegate;
static NSObject<SafencryptConfig> * _config;

+ (NSObject<SafencryptConfig> *) config{
    return _config;
}

+ (NSObject<SafencryptDelegate> *)delegate{
    return _delegate;
}

+ (void) startUp: (NSObject<SafencryptDelegate> *) delegate config: (NSObject<SafencryptConfig> *)config{
    [LogUtil info: @"正在启动中...[author:1iURI]"];
    _delegate = delegate;
    _config = config;
    [NSURLProtocol registerClass: [NetworkInject class]];
    NSString *t_cToken = [delegate onReadCToken];
    if (!t_cToken){// 未注册至服务器端
        [LogUtil err: @"检测到当前浏览器未注册至服务器端，启动客户端注册机制..."];
        [self applyPublickeyWhenSuccess:^(NSString *modulus, NSString *exponent, NSString *flag) {
            // 申请公钥成功
            NSString *t_identifier = [self getIdentifier];
            [self signUpClientWithFlag:flag
                               modulus:modulus
                              exponent:exponent
                            identifier:t_identifier
                               success:^(NSString *cToken) {
                                   [LogUtil info: [NSString stringWithFormat: @"注册客户端成功，CToken: %@" , cToken]];
                                   [delegate onCreateCToken: cToken];
                               } failed:^(NSError *error) {
                                   [delegate onError: error];
                               }];
        } failed:^(NSError *err){
            // 申请公钥失败
            [delegate onError: err];
        }];
    }
    [LogUtil info:@"安全系统启动完毕"];
    while(true){}
}

+ (NSString *)getIdentifier{
    NSString *t_identifier = [_delegate onReadIdentifier];
    if (!t_identifier) {
        t_identifier = [StringUtil randomStr: 16];
        [LogUtil info: [NSString stringWithFormat: @"创建新的客户端标识：%@" , t_identifier]];
        [_delegate onCreateIdentifier: t_identifier];
    }
    return t_identifier;
}

/**
 申请公钥

 @param success 从公钥服务器申请成功的回调函数
 @param failed 从公钥服务器申请失败的回调函数
 */
+ (void)applyPublickeyWhenSuccess: (void (^)(NSString *modulus , NSString *exponent , NSString *flag)) success
                           failed: (void (^)(NSError *error))failed{
    [LogUtil info: @"正在向服务器端申请公钥..."];
    NSURLRequest *request =[NSURLRequest requestWithURL: [_config applyPublicKeyURL]];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *sessionDataTask = [session dataTaskWithRequest:request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if (error){
            
            [LogUtil err: [NSString stringWithFormat: @"申请公钥失败，链接至公钥服务器时出错：%@" , [[_config applyPublicKeyURL] absoluteString]]];
            NSLog(@"%@" , error);
            failed(error);
        }
        else{
            NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:(NSJSONReadingMutableLeaves) error:nil];
            [LogUtil info: [NSString stringWithFormat: @"公钥申请成功，PUB-MOD：%@" , dict[@"modulus"]]];
            success(dict[@"modulus"] , dict[@"exponent"] , dict[@"flag"]);
        }
    }];
    [sessionDataTask resume];
}

/**
 注册客户端

 @param flag 公钥flag回执
 @param identifier 客户端标识
 @param success 注册成功的回调函数
 @param failed 注册失败的回调函数
 */
+ (void)signUpClientWithFlag: (NSString *)flag
                     modulus: (NSString *)modulus
                    exponent: (NSString *)exponent
                  identifier: (NSString *)identifier
                     success: (void(^)(NSString *cToken))success
                      failed: (void(^)(NSError *error))failed{
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL: [_config signUpClientURL] cachePolicy:0 timeoutInterval:2.0f];
    request.HTTPMethod = @"POST";
    [request setValue: @"application/json;charset=utf-8" forHTTPHeaderField: @"Content-Type"];
    // 根据RSA模和指数计算客户端标识的密文
    NSString *identifier_encoded = [SRSA encryptString: [StringUtil reverse: identifier] modulus: modulus exponent: exponent];
    [LogUtil info: [NSString stringWithFormat:@"生成注册客户端报文成功：%@" ,identifier_encoded]];
    // 组装数据并发送
    request.HTTPBody = [NSJSONSerialization dataWithJSONObject: @{@"type" : @2 , @"flag" : flag , @"data" : identifier_encoded} options:0 error:NULL];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        if (error) {// 请求失败
            failed(error);
        }
        NSError *jsonReadErr = nil;
        NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:(NSJSONReadingMutableLeaves) error: &jsonReadErr];
        if (jsonReadErr) {// JSON解析失败
            failed(jsonReadErr);
        }
        NSString *resultJSON = [SAES decryptWithData: dict[@"data"] Key: [self getIdentifier]];
        if (!resultJSON){
            // AES解密失败
            [LogUtil err: @"对注册客户端的结果进行AES解密时发生错误"];
            failed([[NSError alloc] initWithDomain: @"Safencrypt" code: 99 userInfo:  @{@"time": @"对注册客户端的结果进行AES解密时发生错误" , @"detail" : [[NSString alloc] initWithData: data encoding: NSUTF8StringEncoding]}]);
        }
        NSDictionary *resultDict = [NSJSONSerialization JSONObjectWithData: [resultJSON dataUsingEncoding: NSUTF8StringEncoding]
                                                                   options: NSJSONReadingMutableLeaves error: &jsonReadErr];
        if (jsonReadErr) {// JSON解析失败
            failed(jsonReadErr);
        }
        success(resultDict[@"ctoken"]);
    }];
    [dataTask resume];
}

+ (BOOL)isNeedEncrypt: (NSString *)urlStr{
    NSArray<NSString *> *domains = [_config encryptDomainList];
    if (!domains) {// 未设置加密域名列表，拒绝所有加密
        [LogUtil warn: @"当前没有设置要加密的域名列表[未实现SafencryptConfig.encryptDomainList]，忽略所有加密。"];
        return NO;
    }
    for (NSString *domain in domains) {
        if ([urlStr containsString: domain]) {
            goto CHECK_DOMAIN_SUCCESS;
        }
    }
    [LogUtil info: [NSString stringWithFormat: @"当前访问的URL不在待加密域范围内：%@" , urlStr]];
CHECK_DOMAIN_SUCCESS:{// 在加密域名范围内
    NSArray<NSString *> *notEncryptKeywords = [_config notEncryptKeywordList];
    if (!notEncryptKeywords){
        return YES;
    }
    for (NSString *notEncryptKeyword in notEncryptKeywords) {
        if ([urlStr containsString:notEncryptKeyword]) {// 存在不加密关键字
            return NO;
        }
    }
}
    return YES;
}

+ (BOOL)isBaseOnClientRequest: (NSString *)urlStr{
    NSArray<NSString *> *baseOnClientKeywords = [_config baseOnClientEncryptKeywordList];
    if (!baseOnClientKeywords){// 基于客户端的关键字列表为nil，认为无客户端请求，即当前请求不是基于客户端的请求
        return NO;
    }
    for (NSString *baseOnClientKeyword in baseOnClientKeywords) {
        if ([urlStr containsString: baseOnClientKeyword]) {// 存在基于客户端请求的关键字
            return YES;
        }
    }
    return NO;
}

+ (NSInteger)queryRequestType:(NSString *)urlStr{
    if([urlStr containsString: [[_config applyPublicKeyURL] absoluteString]]){
        return 1;
    }
    else if([urlStr containsString: [[_config signUpClientURL] absoluteString]]){
        return 2;
    }
    else if([self isBaseOnClientRequest: urlStr]){
        return 3;
    }
    else{
        return 4;
    }
}

@end
