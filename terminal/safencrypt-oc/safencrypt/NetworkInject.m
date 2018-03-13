//
//  NetworkInject.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/7.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "NetworkInject.h"
#import "Safencrypt.h"

@implementation NetworkInject

+ (BOOL)canInitWithRequest:(NSURLRequest *)request{
    NSArray<NSString *> *requestTypeNameArr = @[@"申请公钥串",@"注册客户端",@"基于客户端",@"基于用户"];
    NSString *urlStr = [[request URL] absoluteString];
    if (![Safencrypt isNeedEncrypt: urlStr]){
        return NO;
    }
    NSInteger reqType = [Safencrypt queryRequestType: urlStr];
    [LogUtil info: [NSString stringWithFormat: @"监测到【%@】的请求 - %@", requestTypeNameArr[(reqType - 1)] ,urlStr]];
    return reqType > 2 ? YES : NO;// 3 4 是基于客户端和用户的请求，需要加密
}

@end
