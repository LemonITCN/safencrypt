package win.liuri.safencrypt.core.exception;

/**
 * 启动Safencrypt时候传如的代理函数无效
 */
public class DelegateInvalidException extends Exception {

    public DelegateInvalidException() {
        super("Sorry. Invalid proxy function.");
    }

}
