package com.nimang.pupa.common.exception;

public class ApiException extends RuntimeException{
    protected String msg;
    protected int code;
    protected Object data;

    public ApiException(String message) {
        super(message);
        this.msg = message;
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public ApiException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

    public Object getData() {
        return data;
    }
}
