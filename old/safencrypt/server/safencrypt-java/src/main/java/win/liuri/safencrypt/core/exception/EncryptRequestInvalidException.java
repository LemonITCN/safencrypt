package win.liuri.safencrypt.core.exception;

/**
 * 加密请求无效异常
 * 通常指谣传的参数不全
 */
public class EncryptRequestInvalidException extends Exception {

    public EncryptRequestInvalidException() {
        super("Sorry. Your request is invalid.");
    }

}
