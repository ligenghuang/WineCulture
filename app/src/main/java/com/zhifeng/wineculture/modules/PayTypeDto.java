package com.zhifeng.wineculture.modules;

import java.util.List;

public class PayTypeDto {


    /**
     * status : 200
     * msg : 成功！
     * data : [{"pay_type":2,"pay_name":"微信支付"},{"pay_type":1,"pay_name":"余额支付"},{"pay_type":3,"pay_name":"支付宝支付"}]
     */

    private int status;
    private String msg;
    private List<Temporary.DataBean.PayTypeBean> data;

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

    public List<Temporary.DataBean.PayTypeBean> getData() {
        return data;
    }

    public void setData(List<Temporary.DataBean.PayTypeBean> data) {
        this.data = data;
    }

}
