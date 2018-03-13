//
//  safencrypt.h
//  safencrypt
//
//  Created by 1iURI on 2018/3/7.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NetworkInject.h"
#import "SafencryptDelegate.h"
#import "SafencryptConfig.h"

@interface Safencrypt : NSObject

/**
 启动Safencrypt安全系统

 @param delegate 代理对象
 @param config 配置对象
 */
+ (void) startUp: (NSObject<SafencryptDelegate> *) delegate config: (NSObject<SafencryptConfig> *)config;

/**
 获取配置对象

 @return 配置对象
 */
+ (NSObject<SafencryptConfig> *) config;

/**
 获取代理对象

 @return 代理对象
 */
+ (NSObject<SafencryptDelegate> *)delegate;

/**
 判断当前请求URL是否需要加密

 @param urlStr 请求的URL字符串
 @return 是否需要加密的布尔值
 */
+ (BOOL)isNeedEncrypt: (NSString *)urlStr;

/**
 是否是基于客户端的请求

 @param urlStr 请求的URL字符串
 @return 是否基于客户端的请求
 */
+ (BOOL)isBaseOnClientRequest: (NSString *)urlStr;

/**
 判断请求的类型（不包含不加密的请求）
 本函数本身不判断是否跳过加密！！
 1：申请公钥
 2：注册客户端
 3：基于客户端的请求
 4：基于用户的请求

 @param urlStr 待请求的URL字符串
 @return 请求的类型数值
 */
+ (NSInteger)queryRequestType: (NSString *)urlStr;

@end
