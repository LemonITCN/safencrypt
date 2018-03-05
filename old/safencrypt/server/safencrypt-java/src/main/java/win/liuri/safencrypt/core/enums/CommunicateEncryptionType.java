package win.liuri.safencrypt.core.enums;

/**
 * 通信加密类型枚举
 */
public enum CommunicateEncryptionType {

    /**
     * 没有加密
     */
    NO_ENCRYPTION(1),
    /**
     * 注册客户端模式加密
     * 请求基于RSA公钥加密，响应基于客户端identifier加密，通过RSA-FLAG识别
     */
    CLIENT_SIGN_UP_ENCRYPTION(2),
    /**
     * 基于客户端的加密解密
     * 双向均为基于客户端identifier加密，通过ctoken识别
     */
    CLIENT_BASED_ENCRYPTION(3),
    /**
     * 基于用户的加密解密
     * 双向均为基于utoken加密，通过ctoken识别
     */
    USER_BASED_ENCRYPTION(4);

    int value;

    public int getValue() {
        return value;
    }

    CommunicateEncryptionType(int value) {
        this.value = value;
    }

}
