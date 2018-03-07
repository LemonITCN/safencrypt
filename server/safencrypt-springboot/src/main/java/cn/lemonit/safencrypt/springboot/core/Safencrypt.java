package cn.lemonit.safencrypt.springboot.core;

import cn.lemonit.safencrypt.springboot.core.exception.DelegateInvalidException;
import cn.lemonit.safencrypt.springboot.core.interfaces.SafencryptUserProxy;
import cn.lemonit.safencrypt.springboot.core.interfaces.SafencryptClientProxy;

public class Safencrypt {

    private static SafencryptClientProxy clientProxy;
    private static SafencryptUserProxy userProxy;

    private Safencrypt() {
    }

    public synchronized static void init(SafencryptClientProxy clientProxy, SafencryptUserProxy userProxy) throws DelegateInvalidException {
        if (clientProxy == null || userProxy == null)
            throw new DelegateInvalidException();
        Safencrypt.clientProxy = clientProxy;
        Safencrypt.userProxy = userProxy;
    }

    public static SafencryptClientProxy getClientProxy() {
        return clientProxy;
    }

    public static SafencryptUserProxy getUserProxy() {
        return userProxy;
    }
}
