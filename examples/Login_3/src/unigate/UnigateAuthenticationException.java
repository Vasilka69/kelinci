package src.unigate;

import org.springframework.security.core.AuthenticationException;

public class UnigateAuthenticationException extends AuthenticationException {
    private static final String ERROR_REDIRECT = "Не разрешен переход по запрошенному URL адресу %s";
    private final UnigateAuthenticationErrorType errorType;

    public UnigateAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        errorType = null;
    }

    public UnigateAuthenticationException(String msg) {
        super(msg);
        errorType = null;
    }

    public UnigateAuthenticationException(String msg, UnigateAuthenticationErrorType errorType) {
        super(msg);
        this.errorType = errorType;
    }

    public UnigateAuthenticationErrorType getErrorType() {
        return errorType;
    }

    public enum UnigateAuthenticationErrorType {
        DELETED, RESERVED, BLOCK, LOCK, SESSION, TWOFACT, ESIA, REDIRECT, LOGIN
    }

    public static String getErrorRedirect(String redirect) {
        return String.format(
                UnigateAuthenticationException.ERROR_REDIRECT,
                redirect
        );
    }
}

