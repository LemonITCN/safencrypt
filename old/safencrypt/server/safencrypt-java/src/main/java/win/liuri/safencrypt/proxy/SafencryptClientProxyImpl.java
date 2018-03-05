package win.liuri.safencrypt.proxy;

import win.liuri.safencrypt.core.bean.ClientInfo;
import win.liuri.safencrypt.core.interfaces.SafencryptClientProxy;

public class SafencryptClientProxyImpl implements SafencryptClientProxy {

    @Override
    public void addClient(ClientInfo clientInfo) {

    }

    @Override
    public String getClientIdentifierWithCToken(String cToken) {
        return "e59609c2a86746f6";
    }
}
