//
//  LogUtil.m
//  safencrypt
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "LogUtil.h"
#import "Safencrypt.h"

@implementation LogUtil

static char *system_name = "Safencrypt";

+ (void)info:(NSString *)msg{
    [self log: msg type: "info"];
}

+ (void)warn:(NSString *)msg{
    [self log: msg type: "warn"];
}

+ (void)err:(NSString *)msg{
    [self log: msg type: "erro"];
}

+ (void)log: (NSString *)msg type: (char *)type{
    if ([[Safencrypt config] isDebugMode]){
        printf("【%s - %s】%s\n" , system_name ,type ,[msg UTF8String]);
    }
    [[Safencrypt delegate] onLog: [NSString stringWithCString: type encoding: NSUTF8StringEncoding] msg: msg];
}

@end
