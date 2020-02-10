package exception;

public class SmtpException extends Exception {
    public SmtpException() {
    }

    public SmtpException(String message) {
        super(message);
    }

    public SmtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmtpException(Throwable cause) {
        super(cause);
    }

    public SmtpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}