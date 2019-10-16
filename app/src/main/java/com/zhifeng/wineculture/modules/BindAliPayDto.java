package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class BindAliPayDto {

    /**
     * data : []
     * msg : 编辑成功
     * status : 200
     */

    private String msg;
    private int status;
    private List<?> data;

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<?> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
