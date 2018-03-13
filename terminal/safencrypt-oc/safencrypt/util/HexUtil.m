//
//  HexUtil.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "HexUtil.h"

@implementation HexUtil

/**
 NSData转十六进制字符串

 @param data 带转换的NSData对象
 @return 转换完毕的NSString字符串
 */
+ (NSString *)hexStringFromData:(NSData *)data{
    Byte *bytes = (Byte *)[data bytes];
    NSString *hexStr=@"";
    for(NSInteger i = 0 ; i < [data length] ; i ++){
        NSString *newHexStr = [NSString stringWithFormat:@"%x",bytes[i]&0xff];
        if([newHexStr length]==1)
            hexStr = [NSString stringWithFormat:@"%@0%@",hexStr,newHexStr];
        else
            hexStr = [NSString stringWithFormat:@"%@%@",hexStr,newHexStr];
    }
    return hexStr;
}

/**
 将十六进制字符串转换成NSData

 @param str 十六进制字符串
 @return 转换成的NSData
 */
+ (NSData *)dataFromHexString: (NSString *)str{
    const char *string = [str cStringUsingEncoding:NSUTF8StringEncoding];
    size_t length = strlen(string);
    if (length % 2 != 0) {
        return nil;
    }
    NSMutableData *data = [NSMutableData data];
    for (NSInteger i = 0; i < length; i += 2) {
        char byte1 = *(string + i);
        char byte2 = *(string + i + 1);
        int8_t value1 = 0;
        int8_t value2 = 0;
        if (byte1 >= 'a') {
            value1 = byte1 - 'a' + 10;
        }
        else if (byte1 >= 'A'){
            value1 = byte1 - 'A' + 10;
        }
        else if (byte1 >= '0'){
            value1 = byte1 - '0';
        }
        if (byte2 >= 'a') {
            value2 = byte2 - 'a' + 10;
        }
        else if (byte2 >= 'A'){
            value2 = byte2 - 'A' + 10;
        }
        else if (byte2 >= '0'){
            value2 = byte2 - '0';
        }
        if (value1 < 0 || value1 > 15 || value2 < 0 || value2 > 15) {
            data = nil;
            break;
        }
        int8_t value = value1 * 16 + value2;
        [data appendBytes:&value length:1];
    }
    return data;
}

@end
