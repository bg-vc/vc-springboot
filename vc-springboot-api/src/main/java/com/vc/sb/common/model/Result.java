package com.vc.sb.common.model;

import com.vc.sb.common.enums.ResultCode;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 22:01
 * Description:
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Result<T> {

    private int code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public Result setResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        return this;
    }

    public Result setResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
        return this;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public Result setMsg(String message) {
        this.msg = message;
        return this;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.code == ResultCode.success.getCode();
    }


}
