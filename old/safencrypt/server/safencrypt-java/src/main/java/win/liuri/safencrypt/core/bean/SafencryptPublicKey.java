package win.liuri.safencrypt.core.bean;

import org.apache.commons.codec.binary.Hex;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * 公钥信息对象
 */
public class SafencryptPublicKey {

    private String modulus;
    private String exponent;
    private String flag;

    public String getFlag() {
        return flag;
    }

    public String getModulus() {
        return modulus;
    }

    public String getExponent() {
        return exponent;
    }

    private SafencryptPublicKey() {
    }

    public SafencryptPublicKey(KeyPair keyPair) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        modulus = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
        exponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
        flag = UUID.randomUUID().toString();
    }
}
