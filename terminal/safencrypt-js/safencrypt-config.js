var safencrypt_config = {
    // 申请公钥的URL
    apply_public_key_url: "http://localhost:8080/safencrypt/apply-public-key",
    // 注册客户端的URL
    sign_up_client_url: "http://localhost:8080/safencrypt/sign-up-client",
    // 基于客户端的请求地址数组
    base_on_client_urls: [
        'http://localhost:8080/client-msg',
        'http://localhost:8080/put-info',
        'http://localhost:8080/get'
    ],
    // 不加密的请求地址数组
    non_encrypt_urls: ['http://localhost:8080/non-encrypt-msg'],
    // 是否开启调试
    debug: true,
    // 来源
    from: 0
};