//
//  rsa.h
//  safencrypt
//
//  Created by 1iURI on 2018/3/7.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 核心算法 - RSA
 */
@interface SRSA : NSObject

/**
 根据RSA公钥的模和指数对数据进行RSA加密

 @param content 要加密的数据内容
 @param modulus RSA公钥的模 - 十六进制字符串
 @param exponent RSA公钥的指数
 @return 加密后的结果字符串（hex）
 */
+ (NSString *)encryptString:(NSString *)content modulus:(NSString *)modulus exponent: (NSString *)exponent;

@end
