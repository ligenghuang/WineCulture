package com.zhifeng.wineculture.modules;

/**
 * 通用实体类
 */
public class GeneralDto {


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
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
