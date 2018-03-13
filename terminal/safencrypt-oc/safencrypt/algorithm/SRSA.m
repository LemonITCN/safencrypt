//
//  rsa.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/7.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "SRSA.h"
#import <openssl/pem.h>
#import "NSData+OpenSSL.h"
#import "HexUtil.h"

@implementation SRSA

+ (NSString *)encryptString:(NSString *)content modulus:(NSString *)modulus exponent: (NSString *)exponent{
    NSData *_encryptData = [[content dataUsingEncoding: NSUTF8StringEncoding] OpenSSL_RSA_DataWithPublicModulus:[HexUtil dataFromHexString: modulus] exponent:[HexUtil dataFromHexString: exponent] isDecrypt:NO];
    NSString *result = [HexUtil hexStringFromData: _encryptData];
    return result;
}



@end
