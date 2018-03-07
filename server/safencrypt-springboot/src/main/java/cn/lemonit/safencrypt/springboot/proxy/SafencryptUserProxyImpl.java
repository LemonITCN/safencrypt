package cn.lemonit.safencrypt.springboot.proxy;

import cn.lemonit.safencrypt.springboot.core.interfaces.SafencryptUserProxy;

public class SafencryptUserProxyImpl implements SafencryptUserProxy {

    @Override
    public String getUTokenWithCToken(String cToken) {
        return "1234567890123456";
    }

}
