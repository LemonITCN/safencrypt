//
//  main.m
//  safencrypt-mac-oc-example
//
//  Created by 1iURI on 2018/3/7.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Safencrypt.h"
#import "SafencryptDelegateImpl.h"
#import "SafencryptConfigImpl.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        // 启动Safencrypt安全系统
        [Safencrypt startUp: [[SafencryptDelegateImpl alloc] init]
                     config: [[SafencryptConfigImpl alloc] init]];
    }
    return 0;
}
