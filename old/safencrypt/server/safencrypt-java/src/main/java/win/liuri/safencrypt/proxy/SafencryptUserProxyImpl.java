package win.liuri.safencrypt.proxy;

import win.liuri.safencrypt.core.interfaces.SafencryptUserProxy;

public class SafencryptUserProxyImpl implements SafencryptUserProxy {

    @Override
    public String getUTokenWithCToken(String cToken) {
        return "1234567890123456";
    }

}
