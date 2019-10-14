package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class BalanceOfPaymentDto {


    /**
     * status : 200
     * msg : success
     * data : {"list":[{"note":"平级奖","balance":0.48,"source_id":"","create_time":"2019-08-26 10:52:40","old_balance":null,"log_type":1},{"note":"级差奖","balance":12,"source_id":"","create_time":"2019-08-26 10:52:40","old_balance":null,"log_type":1}]}
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
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * note : 平级奖
             * balance : 0.48
             * source_id :
             * create_time : 2019-08-26 10:52:40
             * old_balance : null
             * log_type : 1
             */

            private String note;
            private double balance;
            private String source_id;
            private String create_time;
            private Object old_balance;
            private int log_type;

            public String getNote() {
                return note == null ? "" : note;
            }

            public void setNote(String note) {
                this.note = note == null ? "" : note;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getSource_id() {
                return source_id == null ? "" : source_id;
            }

            public void setSource_id(String source_id) {
                this.source_id = source_id == null ? "" : source_id;
            }

            public String getCreate_time() {
                return create_time == null ? "" : create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time == null ? "" : create_time;
            }

            public Object getOld_balance() {
                return old_balance;
            }

            public void setOld_balance(Object old_balance) {
                this.old_balance = old_balance;
            }

            public int getLog_type() {
                return log_type;
            }

            public void setLog_type(int log_type) {
                this.log_type = log_type;
            }
        }
    }
}
