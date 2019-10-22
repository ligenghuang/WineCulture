package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class FansOrderDto {

    /**
     * status : 200
     * msg : success
     * data : {"total":2,"per_page":20,"current_page":1,"last_page":1,"data":[{"add_time":1567049175,"order_sn":"20190829112615859230","total_amount":"200000.00","id":"27851","realname":"默认昵称"},{"add_time":1567048862,"order_sn":"20190829112102570273","total_amount":"20000.00","id":"27855","realname":"默认昵称"}]}
     */

    private int status;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 2
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"add_time":1567049175,"order_sn":"20190829112615859230","total_amount":"200000.00","id":"27851","realname":"默认昵称"},{"add_time":1567048862,"order_sn":"20190829112102570273","total_amount":"20000.00","id":"27855","realname":"默认昵称"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
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
             * add_time : 1567049175
             * order_sn : 20190829112615859230
             * total_amount : 200000.00
             * id : 27851
             * realname : 默认昵称
             */

            private int add_time;
            private String order_sn;
            private String total_amount;
            private int id;
            private String realname;

            public int getAdd_time() {
                return add_time;
            }

            public void setAdd_time(int add_time) {
                this.add_time = add_time;
            }

            public String getOrder_sn() {
                return order_sn == null ? "" : order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn == null ? "" : order_sn;
            }

            public String getTotal_amount() {
                return total_amount == null ? "" : total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount == null ? "" : total_amount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRealname() {
                return realname == null ? "" : realname;
            }

            public void setRealname(String realname) {
                this.realname = realname == null ? "" : realname;
            }
        }
    }
}
