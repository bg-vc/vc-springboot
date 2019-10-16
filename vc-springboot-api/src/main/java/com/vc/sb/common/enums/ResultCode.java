package com.vc.sb.common.enums;


/**
 * Author:       VinceChen
 * Date:         2019-10-16 21:45
 * Description:
 */
public enum ResultCode {

    success(0, "ok"),
    internal_server_error(1, "internal server error"),
    service_not_found(2, "service not found"),
    auth_not_allow(3, "auth not allow"),
    token_invalid(4, "token was invalid"),
    err_param(5, "request param error"),
    ;

    private int code;

    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
