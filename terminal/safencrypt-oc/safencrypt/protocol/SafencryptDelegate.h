//
//  SafencryptDelegate.h
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol SafencryptDelegate <NSObject>

/**
 创建客户端标识时候的回调函数
 此处需要SDK使用者将客户端标识进行存储

 @param identifier 客户端标识
 */
- (void)onCreateIdentifier: (NSString *)identifier;
/**
 创建CToken时候的回调函数
 此处需要SDK使用者将CToken（客户端Token）进行存储

 @param cToken 客户端Token
 */
- (void)onCreateCToken: (NSString *)cToken;
/**
 读取客户端标识时候的回调函数
 此处需要SDK使用者将之前存储的客户端标识作为返回值返回

 @return 客户端标识
 */
- (NSString *)onReadIdentifier;
/**
 读取客户端Token（CToken）时候的回调函数
 此处需要SDK使用者将之前存储的客户端Token作为返回值返回

 @return 客户端Token
 */
- (NSString *)onReadCToken;
/**
 读取用户Tokenu（UToken）时候的回调函数
 此处需要SDK使用者将之前存储的用户Token作为返回值返回

 @return 用户Token
 */
- (NSString *)onReadUToken;
/**
 当发生错误的时候的回调函数
 */
- (void)onError: (NSError *)error;


/**
 当Safencrypt有日志输出时候的回调函数
 无论是否开启调试模式，该回调均有效

 @param type 日志类型，分为：info/warn/erro三种
 @param msg 消息的内容
 */
- (void)onLog: (NSString *)type
          msg: (NSString *)msg;

@end
