package win.liuri.safencrypt.core.service;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import win.liuri.safencrypt.core.Safencrypt;
import win.liuri.safencrypt.core.bean.SafencryptResponse;
import win.liuri.safencrypt.core.util.AES;

import java.util.Map;

/**
 * safencrypt的响应加密业务类
 */
public class SafencryptResponseService {

    public String encryptResponse(String content, Integer type, String flag) {
        if (type > 1) {
            switch (type) {
                case 2:
                    content = encryptSignUpClient(content, flag);
                    break;
                case 3:
                    content = encryptBasedClient(content, flag);
                    break;
                default:
                    content = encryptBasedUser(content, flag);
            }
            return new SafencryptResponse(type, content).toString();
        }
        // type为1，不加密，不需要进行json嵌套
        return content;
    }

    /**
     * 加密注册客户端的响应
     * {"identifier":"xxx","ctoken":"xxx"}
     * 用identifier加密整个json
     */
    private String encryptSignUpClient(String content, String flag) {
        Map<String, String> map = new GsonBuilder().create().fromJson(content, new TypeToken<Map<String, String>>() {
        }.getType());
        return AES.encrypt(content, map.get("identifier"));
    }

    /**
     * 加密基于设备有关业务的响应
     */
    private String encryptBasedClient(String content, String flag) {
        return AES.encrypt(content, Safencrypt.getClientProxy().getClientIdentifierWithCToken(flag));
    }

    /**
     * 加密基于用户有关业务的响应
     */
    private String encryptBasedUser(String content, String flag) {
        return AES.encrypt(content, Safencrypt.getUserProxy().getUTokenWithCToken(flag));
    }

}
