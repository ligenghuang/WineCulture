package com.lgh.huanglib.retrofitlib.http;

public class ApiException extends Exception {
    public int code;
    public String message;
    public int netErrorCode;

    public ApiException(Throwable throwable, int code,int netErrorCode) {
        super(throwable);
        this.code = code;
        this.netErrorCode = netErrorCode;

    }
    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", netErrorCode=" + netErrorCode +
                '}';
    }
}