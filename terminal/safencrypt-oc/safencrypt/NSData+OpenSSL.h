//
//  NSData+OpenSSL.h
//  QuickRSA
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//


#import <Foundation/Foundation.h>

#define USE_OPENSSL 1

@interface NSData(OpenSSL)

#if USE_OPENSSL
//Default padding is RSA_PKCS1_PADDING

//Use PEM format, Pub(Pri) Enc -> Pri(Pub) Dec
- (NSData *)OpenSSL_RSA_EncryptDataWithPEM:(NSData *)pemData isPublic:(BOOL)isPublic;//PEM key
- (NSData *)OpenSSL_RSA_DecryptDataWithPEM:(NSData *)pemData isPublic:(BOOL)isPublic;//PEM key

//Use DER format, Pub(Pri) Enc -> Pri(Pub) Dec
- (NSData *)OpenSSL_RSA_EncryptDataWithDER:(NSData *)derData isPublic:(BOOL)isPublic;//DER key
- (NSData *)OpenSSL_RSA_DecryptDataWithDER:(NSData *)derData isPublic:(BOOL)isPublic;//DER key

//Use modulus and exponent
- (NSData *)OpenSSL_RSA_DataWithPublicModulus:(NSData *)modulus exponent:(NSData *)exponent isDecrypt:(BOOL)isDecrypt;
//- (NSData *)OpenSSL_RSA_DataWithPrivateModulus:...
#endif

@end
