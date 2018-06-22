//
//  ViewController.m
//  safencrypt-ios-oc-example
//
//  Created by 1iURI on 2018/3/13.
//  Copyright © 2018年 LemonIT.CN. All rights reserved.
//

#import "ViewController.h"
#import "Safencrypt.h"
#import "delegate/SafencryptDelegateImpl.h"
#import "SafencryptConfigImpl.h"
#import "AFHTTPSessionManager.h"

#define HOST_IP @"10.39.8.212"

@interface ViewController ()

@property (weak, nonatomic) IBOutlet UITextView *logView;
@property AFHTTPSessionManager *defaultManager;

@end

@implementation ViewController

- (AFHTTPSessionManager *)defaultManager{
    if (!self->_defaultManager) {
        _defaultManager = [AFHTTPSessionManager manager];
        _defaultManager.requestSerializer = [AFJSONRequestSerializer serializer];
        _defaultManager.responseSerializer = [AFJSONResponseSerializer serializer];
    }
    return _defaultManager;
}

- (void)viewDidAppear:(BOOL)animated{
    // 启动Safencrypt安全系统
    [Safencrypt startUp: [[SafencryptDelegateImpl alloc] initWithLogView: self.logView]
                 config: [[SafencryptConfigImpl alloc] init]];
}

- (IBAction)sendBaseClientReq:(UIButton *)sender {
    [self.defaultManager POST: [NSString stringWithFormat: @"http://%@:8080/client-msg" , HOST_IP] parameters: @{@"msg":@"你好Safencrypt，一条基于客户端的消息来自iOS客户端"} progress:^(NSProgress * _Nonnull uploadProgress) {
        NSLog(@"hello!!!!!!!ppp");
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"hello!!!!!!!ssss:%@" , responseObject);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"hello!!!!!!!eee: %@" , error);
    }];
}

- (IBAction)sendBaseUserReq:(UIButton *)sender {
    [self.defaultManager POST: [NSString stringWithFormat: @"http://%@:8080/user-msg" , HOST_IP] parameters: @{@"msg":@"你好Safencrypt，一条基于用户的消息来自iOS客户端"} progress:^(NSProgress * _Nonnull uploadProgress) {
        NSLog(@"hello!!!!!!!ppp");
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"hello!!!!!!!ssss:%@" , responseObject);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"hello!!!!!!!eee: %@" , error);
    }];
}

- (IBAction)sendBaseClientGetReq:(UIButton *)sender {
    AFHTTPSessionManager *session = [AFHTTPSessionManager manager];
    [session GET: @"" parameters: @{} progress:^(NSProgress * _Nonnull downloadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        
    }];
}

@end
