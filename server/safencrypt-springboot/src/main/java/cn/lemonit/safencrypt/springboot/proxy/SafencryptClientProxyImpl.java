package cn.lemonit.safencrypt.springboot.proxy;

import cn.lemonit.safencrypt.springboot.core.bean.ClientInfo;
import cn.lemonit.safencrypt.springboot.core.interfaces.SafencryptClientProxy;

public class SafencryptClientProxyImpl implements SafencryptClientProxy {

    @Override
    public void addClient(ClientInfo clientInfo) {

    }

    @Override
    public String getClientIdentifierWithCToken(String cToken) {
        return "f9acd70afd1ed1ef";
    }
}
