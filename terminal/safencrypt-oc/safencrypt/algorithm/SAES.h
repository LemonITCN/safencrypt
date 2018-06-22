//
//  SAES.h
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SAES : NSObject

+ (NSString *)encryptWithStr: (NSString *)data Key:(NSString *)key;

+ (NSString *)decryptWithStr: (NSString *)data Key:(NSString *)key;

+ (NSData *)encryptWithData: (NSData *)data Key:(NSString *)key;

+ (NSData *)decryptWithData: (NSData *)data Key:(NSString *)key;

@end
