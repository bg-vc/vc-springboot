package com.vc.sb.common.exception;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:10
 * Description:
 */
public class AuthNotAllowException extends RuntimeException {

    public AuthNotAllowException() {
    }
    public AuthNotAllowException(String message) {
        super(message);
    }

    public AuthNotAllowException(String message, Throwable cause) {
        super(message, cause);
    }

}
