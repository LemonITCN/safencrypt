//
//  SafecryptDelegateImpl.m
//  safencrypt-mac-oc-example
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "SafencryptDelegateImpl.h"
#import "SafencryptDelegate.h"

@interface SafencryptDelegateImpl()

@property NSString *_identifier;
@property NSString *_cToken;

@end

@implementation SafencryptDelegateImpl

- (void)onCreateIdentifier:(NSString *)identifier{
    self._identifier = identifier;
    NSLog(@"创建客户端标识：%@" , identifier);
}

- (void)onCreateCToken:(NSString *)cToken{
    self._cToken = cToken;
    NSLog(@"创建客户端Token：%@" , cToken);
}

- (NSString *)onReadIdentifier{
    return self._identifier;
}

- (NSString *)onReadCToken{
    return self._cToken;
}

- (NSString *)onReadUToken{
    return @"1234567890123456";
}

/**
 当发生错误的时候的回调函数
 */
- (void)onError: (NSError *)error{
    
}


/**
 当Safencrypt有日志输出时候的回调函数
 无论是否开启调试模式，该回调均有效
 
 @param type 日志类型，分为：info/warn/erro三种
 @param msg 消息的内容
 */
- (void)onLog: (NSString *)type
          msg: (NSString *)msg{
    
}

@end
