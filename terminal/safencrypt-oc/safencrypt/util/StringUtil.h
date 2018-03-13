//
//  RandomUtil.h
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface StringUtil : NSObject

/**
 创建一个随机字符串

 @param length 随机字符串的长度
 @return 生成的随机字符串
 */
+ (NSString *)randomStr: (NSInteger)length;

/**
 字符串逆序排列
 如：abc处理后即变成：cba
 
 @param str 要逆序排列的字符串
 @return 逆序后的字符串
 */
+ (NSString *)reverse: (NSString *)str;

@end
