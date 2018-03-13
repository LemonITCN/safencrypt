//
//  SAES.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "SAES.h"
#import <CommonCrypto/CommonCryptor.h>
#import "HexUtil.h"

@implementation SAES

//加密 128位 此处默认向量和key相同
+ (NSString *)encryptWithData: (NSString *)data Key:(NSString *)key {
    NSData *resultData = [self AES128operation:kCCEncrypt key:key iv:key data: [[NSData alloc] initWithBase64EncodedString: data options: 0]];
    return [[NSString alloc] initWithData: resultData encoding: NSUTF8StringEncoding];
}

//解密 128位 此处默认向量和key相同
+ (NSString *)decryptWithData: (NSString *)data Key:(NSString *)key {
    NSData *resultData = [self AES128operation:kCCDecrypt key:key iv:key data: [[NSData alloc] initWithBase64EncodedString: data options: 0]];
    return [[NSString  alloc] initWithData: resultData encoding:NSUTF8StringEncoding];
}

+ (NSData *)AES128operation:(CCOperation)operation key:(NSString *)key iv:(NSString *)iv data: (NSData *)data {
    char keyPtr[kCCKeySizeAES128 + 1];
    bzero(keyPtr, sizeof(keyPtr));
    [key getCString:keyPtr maxLength:sizeof(keyPtr) encoding:NSUTF8StringEncoding];
    
    // IV
    char ivPtr[kCCBlockSizeAES128 + 1];
    bzero(ivPtr, sizeof(ivPtr));
    [iv getCString:ivPtr maxLength:sizeof(ivPtr) encoding:NSUTF8StringEncoding];
    
    size_t bufferSize = [data length] + kCCBlockSizeAES128;
    void *buffer = malloc(bufferSize);
    size_t numBytesEncrypted = 0;
    
    
    CCCryptorStatus cryptorStatus = CCCrypt(operation, kCCAlgorithmAES128, kCCOptionPKCS7Padding,
                                            keyPtr, kCCKeySizeAES128,
                                            ivPtr,
                                            [data bytes], [data length],
                                            buffer, bufferSize,
                                            &numBytesEncrypted);
    
    return [NSData dataWithBytesNoCopy:buffer length:numBytesEncrypted];
    
    free(buffer);
    return nil;
}

@end
