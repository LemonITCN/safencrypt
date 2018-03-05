package win.liuri.safencrypt.core.interfaces;

import win.liuri.safencrypt.core.bean.ClientInfo;

public interface SafencryptClientProxy {

    /**
     * 添加客户端的代理函数
     *
             * @param clientInfo 客户端信息对象
     */
    void addClient(ClientInfo clientInfo);

    /**
     * 根据CToken获取客户端标识
     *
     * @param cToken 要获取的客户端的token令牌
     * @return 客户端标识字符串
     */
    String getClientIdentifierWithCToken(String cToken);

}
