package info.itvincent.rsaandroid;

/**
 * RSA异常
 * @author zhongyongsheng
 */

public class RSAException extends Exception {

    public RSAException() {
    }

    public RSAException(String message) {
        super(message);
    }

    public RSAException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSAException(Throwable cause) {
        super(cause);
    }
}
