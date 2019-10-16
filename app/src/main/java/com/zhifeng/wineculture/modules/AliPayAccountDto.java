package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class AliPayAccountDto {

    /**
     * data : [{"alipay_name":"1","alipay":"1"}]
     * msg : success
     * status : 200
     */

    private String msg;
    private int status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * alipay_name : 1
         * alipay : 1
         */

        private String alipay_name;
        private String alipay;

        public String getAlipay_name() {
            return alipay_name == null ? "" : alipay_name;
        }

        public void setAlipay_name(String alipay_name) {
            this.alipay_name = alipay_name == null ? "" : alipay_name;
        }

        public String getAlipay() {
            return alipay == null ? "" : alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay == null ? "" : alipay;
        }
    }
}
