package cn.lemonit.safencrypt.android.safencrypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    private static final String default_charset = "UTF-8";

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     加密密码
     * @param iv      加密向量
     * @return 加密后的字节数据
     */
    public static byte[] encrypt(String mode, byte[] content, byte[] key, byte[] iv) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/" + mode);
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] decrypt(String mode, byte[] content, byte[] key, byte[] iv) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/" + mode);
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串进行AES加密
     *
     * @param mode    模式，格式为 模式/补码方式，如：CBC/ISO10126Padding
     * @param content 要加密的字符串
     * @param key     密钥
     * @return 加密后的字符串（BASE64）
     */
    public static String encrypt(String mode, String content, String key) {
        try {
            byte[] content_bytes = content.getBytes("UTF-8");
            byte[] key_bytes = key.getBytes("UTF-8");
            byte[] result_bytes = encrypt(mode, content_bytes, key_bytes, key_bytes);
//            return Base64.encodeBase64String(result_bytes);
            return Base64.encodeToString(result_bytes, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串进行AES解密
     *
     * @param mode    模式，格式为 模式/补码方式，如：CBC/ISO10126Padding
     * @param content 要解密的字符串
     * @param key     密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String mode, String content, String key) {
        try {
            content = content.replaceAll(" ", "");
            System.out.println("content current: " + content);
            byte[] content_bytes = Base64.decode(content.getBytes("UTF-8"), Base64.DEFAULT);
            byte[] key_bytes = key.getBytes("UTF-8");
            byte[] result_bytes = decrypt(mode, content_bytes, key_bytes, key_bytes);
            if (result_bytes != null) {
                return new String(result_bytes, "UTF-8");
            } else {
                return "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以CBC模式 - ISO10126Padding补码方式进行加密
     *
     * @param content 要加密的字符串
     * @param key     加密密钥16位字符串
     * @return 加密后的字符串
     */
    public static String encrypt_CBC_ISO10126Padding(String content, String key) {
        return encrypt("CBC/ISO10126Padding", content, key);
    }

    /**
     * 以CBC模式 - ISO10126Padding补码方式进行解密
     *
     * @param content 要解密的字符串
     * @param key     解密密钥16位字符串
     * @return 解密后的字符串
     */
    public static String decrypt_CBC_ISO10126Padding(String content, String key) {
        return decrypt("CBC/ISO10126Padding", content, key);
    }

}
