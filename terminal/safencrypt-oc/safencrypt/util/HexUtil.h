//
//  HexUtil.h
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HexUtil : NSObject

/**
 NSData转十六进制字符串
 
 @param data 带转换的NSData对象
 @return 转换完毕的NSString字符串
 */
+ (NSString *)hexStringFromData:(NSData *)data;

/**
 将十六进制字符串转换成NSData
 
 @param str 十六进制字符串
 @return 转换成的NSData
 */
+ (NSData *)dataFromHexString: (NSString *)str;

@end
