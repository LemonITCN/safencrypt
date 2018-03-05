package win.liuri.safencrypt.core.service;

import org.springframework.stereotype.Service;
import win.liuri.safencrypt.core.Safencrypt;
import win.liuri.safencrypt.core.bean.ClientInfo;
import win.liuri.safencrypt.core.bean.SafencryptPublicKey;
import win.liuri.safencrypt.core.exception.FlagInvalidException;

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
