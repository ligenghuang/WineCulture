package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class BankCardDto {

    /**
     * status : 200
     * msg : 获取成功！
     * data : [{"bank_card":"1111111111111111111","bank_name":"1"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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
         * bank_card : 1111111111111111111
         * bank_name : 1
         */

        private String bank_card;
        private String bank_name;

        public String getBank_card() {
            return bank_card == null ? "" : bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card == null ? "" : bank_card;
        }

        public String getBank_name() {
            return bank_name == null ? "" : bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name == null ? "" : bank_name;
        }
    }
}
