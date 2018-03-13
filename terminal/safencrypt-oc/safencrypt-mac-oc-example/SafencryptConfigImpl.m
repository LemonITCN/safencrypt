//
//  SafencryptConfigImpl.m
//  safencrypt-mac-oc-example
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "SafencryptConfigImpl.h"
#import "SafencryptConfig.h"

@implementation SafencryptConfigImpl

/**
 当前是否为调试模式
 调试模式显示日志，非调试非调试模式不显示日志
 
 @return 是否为调试模式的布尔值
 */
- (BOOL)isDebugMode{
    return YES;
}

/**
 申请公钥的URL
 
 @return 申请公钥的URL
 */
- (NSURL *)applyPublicKeyURL{
    return [NSURL URLWithString: @"http://localhost:8080/safencrypt/apply-public-key?type=1"];
}

/**
 注册客户端的URL
 
 @return 注册客户端的URL
 */
- (NSURL *)signUpClientURL{
    return [NSURL URLWithString: @"http://localhost:8080/safencrypt/sign-up-client"];
}

/**
 需要进行加密处理的域名列表
 若网络请求的URL中存在此字符串则进行后续的加密处理，否则直接放过不加密
 
 @return 待判断的加密域名列表
 */
- (NSArray<NSString *> *)encryptDomainList{
    return @[@"localhost:8080"];
}

/**
 不加密的请求关键字列表
 若请求的域名符合加密请求域名列表，但是包含此不加密关键字列表中的某一项，也放过不进行加密
 
 @return 不加密请求的关键字列表
 */
- (NSArray<NSString *> *)notEncryptKeywordList{
    return @[];
}

/**
 基于客户端的请求关键字列表
 符合加密域名且不符合不加密关键字的请求将会进入此阶段进行判断，如果URL中有此列表中的关键字，那么认为是基于客户端的请求
 
 @return 基于客户端请求的关键字列表
 */
- (NSArray<NSString *> *)baseOnClientEncryptKeywordList{
    return @[];
}

@end
