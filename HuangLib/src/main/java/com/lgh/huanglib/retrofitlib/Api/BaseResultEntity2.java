package com.lgh.huanglib.retrofitlib.Api;

/**
 * 回调信息统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseResultEntity2<T> {

    /**
     * code : -2
     * data : 400
     * msg : 请登录
     * url : null
     * wait : 0
     * Accessid : 0
     * data2 : null
     */

    private int code;
    private T data;
    private String msg;
    private Object url;
    private int wait;
    private int Accessid;
    private Object data2;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public int getAccessid() {
        return Accessid;
    }

    public void setAccessid(int Accessid) {
        this.Accessid = Accessid;
    }

    public Object getData2() {
        return data2;
    }

    public void setData2(Object data2) {
        this.data2 = data2;
    }

    @Override
    public String toString() {
        return "BaseResultEntity2{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", url=" + url +
                ", wait=" + wait +
                ", Accessid=" + Accessid +
                ", data2=" + data2 +
                '}';
    }
}
