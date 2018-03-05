package win.liuri.safencrypt.core.exception;

/**
 * 注册客户端时候提供的flag无效异常
 */
public class FlagInvalidException extends Exception {

    public FlagInvalidException(){
        super("Sorry. The flag you submitted is invalid");
    }

}
