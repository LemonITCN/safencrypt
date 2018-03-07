package cn.lemonit.safencrypt.springboot.core.service;

import cn.lemonit.safencrypt.springboot.core.bean.ClientInfo;
import org.springframework.stereotype.Service;
import cn.lemonit.safencrypt.springboot.core.Safencrypt;
import cn.lemonit.safencrypt.springboot.core.bean.SafencryptPublicKey;
import cn.lemonit.safencrypt.springboot.core.exception.FlagInvalidException;

@Service
public class SafencryptRestService {


    /**
     * 申请公钥
     *
     * @return 返回公钥
     */
    public SafencryptPublicKey applyPublicKey() {
        return RSAEncryptService.generatePublicKey();
    }

    /**
     * 注册客户端
     *
     * @param identifier 客戶端标识字符串
     * @return
     */
    public Object signUpClient(String identifier) throws FlagInvalidException {
        ClientInfo clientInfo = new ClientInfo(identifier);
        Safencrypt.getClientProxy().addClient(clientInfo);
        return clientInfo;
    }

}
