package com.vc.sb.common.exception;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:11
 * Description:
 */
public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
