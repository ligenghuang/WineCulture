package com.zhifeng.wineculture.modules;

public class BindBankCardDto {

    /**
     * status : 200
     * msg : 绑定成功！
     * data : {"bankcard":"123456785456456","bankname":"4行"}
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
         * bankcard : 123456785456456
         * bankname : 4行
         */

        private String bankcard;
        private String bankname;

        public String getBankcard() {
            return bankcard == null ? "" : bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard == null ? "" : bankcard;
        }

        public String getBankname() {
            return bankname == null ? "" : bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname == null ? "" : bankname;
        }
    }
}
