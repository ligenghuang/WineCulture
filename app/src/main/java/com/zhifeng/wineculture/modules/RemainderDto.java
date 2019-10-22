package com.zhifeng.wineculture.modules;

public class RemainderDto {

    /**
     * status : 200
     * msg : success
     * data : {"pwd":1,"remainder_money":"0.00","alipay":null,"alipay_name":"","rate":0.05,"max":999999999}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pwd : 1
         * remainder_money : 0.00
         * alipay : null
         * alipay_name :
         * rate : 0.05
         * max : 999999999
         */

        private int pwd;
        private String remainder_money;
        private String alipay;
        private String alipay_name;
        private double rate;
        private double max;

        public int getPwd() {
            return pwd;
        }

        public void setPwd(int pwd) {
            this.pwd = pwd;
        }

        public String getRemainder_money() {
            return remainder_money == null ? "" : remainder_money;
        }

        public void setRemainder_money(String remainder_money) {
            this.remainder_money = remainder_money == null ? "" : remainder_money;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getAlipay_name() {
            return alipay_name == null ? "" : alipay_name;
        }

        public void setAlipay_name(String alipay_name) {
            this.alipay_name = alipay_name == null ? "" : alipay_name;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }
    }
}
