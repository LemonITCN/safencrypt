//
//  RandomUtil.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "StringUtil.h"

@implementation StringUtil

+ (NSString *)randomStr: (NSInteger)length{
    NSMutableString *result = [[NSMutableString alloc] initWithCapacity: length];
    for (NSInteger i = 0 ; i < length ; i ++){
        int number = arc4random() % 36;
        if (number < 10) {
            int figure = arc4random() % 10;
            [result appendString: [NSString stringWithFormat:@"%d", figure]];
        }else {
            int figure = (arc4random() % 26) + 97;
            char character = figure;
            [result appendString: [NSString stringWithFormat:@"%c", character]];
        }
    }
    return result;
}

+ (NSString *)reverse:(NSString *)str{
    NSMutableString *result = [[NSMutableString alloc] initWithCapacity: [str length]];
    for (NSInteger i = str.length - 1; i >= 0; i --) {
        [result appendFormat: @"%c" , [str characterAtIndex: i]];
    }
    return result;
}

@end
