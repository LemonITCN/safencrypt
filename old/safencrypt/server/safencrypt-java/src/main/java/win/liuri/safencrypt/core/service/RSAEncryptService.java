package win.liuri.safencrypt.core.service;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import win.liuri.safencrypt.core.bean.SafencryptPublicKey;
import win.liuri.safencrypt.core.exception.FlagInvalidException;
import win.liuri.safencrypt.core.util.RSA;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

public class RSAEncryptService {

    private static Map<String, KeyPair> keyPairPool;

    /**
     * 获取非对称密钥对存储池
     */
    public synchronized static Map<String, KeyPair> getKeyPairPool() {
        if (keyPairPool == null)
            keyPairPool = new HashMap<>();
        return keyPairPool;
    }

    /**
     * 生成一个公钥信息对象
     *
     * @return 公钥信息对象
     */
    public static SafencryptPublicKey generatePublicKey() {
        // 创建一个非对称加密密钥对
        KeyPair keyPair = RSA.generateKeyPair();
        SafencryptPublicKey safencryptPublicKey = new SafencryptPublicKey(keyPair);
        getKeyPairPool().put(safencryptPublicKey.getFlag(), keyPair);
        return safencryptPublicKey;
    }

    /**
     * 判断是否存在这个flag
     *
     * @param flag flag字符串
     * @return 是否存在这个flag的布尔值
     */
    public static boolean containFlag(String flag) {
        return getKeyPairPool().containsKey(flag);
    }

    public static String decryptJSRequest(String flag, String content) throws FlagInvalidException {
        if (flag == null || flag.length() <= 0 || !RSAEncryptService.containFlag(flag))
            throw new FlagInvalidException();
        KeyPair keyPair = getKeyPairPool().get(flag);
        try {
            content = content.replaceAll(" ", "");// 清除密文中的空格
            byte[] en_data = Hex.decodeHex(content.toCharArray());
            byte[] result_bytes = RSA.decrypt(keyPair.getPrivate(), en_data);
            return StringUtils.reverse(new String(result_bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}