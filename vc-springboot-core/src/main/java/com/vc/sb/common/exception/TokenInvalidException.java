package com.vc.sb.common.exception;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:07
 * Description:
 */
public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException() {
    }

    public TokenInvalidException(String message) {
        super(message);
    }

    public TokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
