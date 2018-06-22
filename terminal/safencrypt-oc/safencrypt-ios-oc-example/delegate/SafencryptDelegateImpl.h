//
//  SafecryptDelegateImpl.h
//  safencrypt-mac-oc-example
//
//  Created by 1iURI on 2018/3/12.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import <UIKit/UIKit.h>
@protocol SafencryptDelegate;

@interface SafencryptDelegateImpl<SafencryptDelegate> : NSObject

/**
 初始化并传入日志控件。为了在界面上打印日志用

 @param logView 日志显示textView
 @return Safencrypt代理实例
 */
- (instancetype)initWithLogView: (UITextView *)logView;

@end
