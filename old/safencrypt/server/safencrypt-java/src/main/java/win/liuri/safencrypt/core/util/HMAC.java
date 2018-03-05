package win.liuri.safencrypt.core.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {

    public static final String KEY_MAC = "HmacMD5";

    private static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public static String hmacMD5(String str, String key) {
        try {
            return byteArrayToHexString(encryptHMAC(str.getBytes(), key));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
