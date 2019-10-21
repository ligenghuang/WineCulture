package com.zhifeng.wineculture.modules;

import java.util.ArrayList;

public class WithdrawalDto {

    /**
     * data : []
     * msg : 提现申请成功,工作人员加急审核中！
     * status : 200
     */

    private String msg;
    private int status;
    private Object data;

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

    public Object getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
