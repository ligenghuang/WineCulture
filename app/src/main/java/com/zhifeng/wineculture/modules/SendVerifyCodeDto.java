package com.zhifeng.wineculture.modules;

public class SendVerifyCodeDto {

    /**
     * status : 200
     * msg : success
     * data : 发送成功！
     */

    private int status;
    private String msg;
    private String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data == null ? "" : data;
    }
}
