//
//  LogUtil.h
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LogUtil : NSObject

/**
 打印一条普通的信息日志

 @param msg 要打印的消息内容
 */
+ (void)info: (NSString *)msg;

/**
 打印一条警告日志

 @param msg 要打印的消息内容
 */
+ (void)warn: (NSString *)msg;


/**
 打印一条错误日志

 @param msg 要打印的消息内容
 */
+ (void)err: (NSString *)msg;

@end
