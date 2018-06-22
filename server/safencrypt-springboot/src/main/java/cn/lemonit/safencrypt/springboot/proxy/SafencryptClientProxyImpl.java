package cn.lemonit.safencrypt.springboot.proxy;

import cn.lemonit.safencrypt.springboot.core.bean.ClientInfo;
import cn.lemonit.safencrypt.springboot.core.interfaces.SafencryptClientProxy;

import java.util.HashMap;

public class SafencryptClientProxyImpl implements SafencryptClientProxy {

    HashMap<String, String> clientPool = new HashMap();

    @Override
    public void addClient(ClientInfo clientInfo) {
        clientPool.put(clientInfo.getCToken(), clientInfo.getIdentifier());
    }

    @Override
    public String getClientIdentifierWithCToken(String cToken) {
        return clientPool.get(cToken);
    }
}
