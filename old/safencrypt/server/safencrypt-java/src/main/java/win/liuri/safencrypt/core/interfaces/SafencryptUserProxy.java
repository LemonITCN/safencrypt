package win.liuri.safencrypt.core.interfaces;

public interface SafencryptUserProxy {

    /**
     * 通过CToken获取对应关联的UToken
     *
     * @param cToken 客户端的CToken
     * @return 该用户的对应UToken
     */
    String getUTokenWithCToken(String cToken);

}
