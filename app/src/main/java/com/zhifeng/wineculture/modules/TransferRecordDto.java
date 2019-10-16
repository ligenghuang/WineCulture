package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class TransferRecordDto {


    /**
     * status : 200
     * msg : 获取成功
     * data : {"total":1,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":9,"user_id":28665,"out_user_id":28665,"in_user_id":28675,"exchange_money":"500.00","description":"","create_time":"2019-10-16 17:15:02","type":2,"detail":"+500.0","nickname":"默认昵称","rate_desc":null}]}
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
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 1
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"id":9,"user_id":28665,"out_user_id":28665,"in_user_id":28675,"exchange_money":"500.00","description":"","create_time":"2019-10-16 17:15:02","type":2,"detail":"+500.0","nickname":"默认昵称","rate_desc":null}]
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
             * id : 9
             * user_id : 28665
             * out_user_id : 28665
             * in_user_id : 28675
             * exchange_money : 500.00
             * description :
             * create_time : 2019-10-16 17:15:02
             * type : 2
             * detail : +500.0
             * nickname : 默认昵称
             * rate_desc : null
             */

            private int id;
            private int user_id;
            private int out_user_id;
            private int in_user_id;
            private String exchange_money;
            private String description;
            private String create_time;
            private int type;
            private String detail;
            private String nickname;
            private String rate_desc;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getOut_user_id() {
                return out_user_id;
            }

            public void setOut_user_id(int out_user_id) {
                this.out_user_id = out_user_id;
            }

            public int getIn_user_id() {
                return in_user_id;
            }

            public void setIn_user_id(int in_user_id) {
                this.in_user_id = in_user_id;
            }

            public String getExchange_money() {
                return exchange_money == null ? "" : exchange_money;
            }

            public void setExchange_money(String exchange_money) {
                this.exchange_money = exchange_money == null ? "" : exchange_money;
            }

            public String getDescription() {
                return description == null ? "" : description;
            }

            public void setDescription(String description) {
                this.description = description == null ? "" : description;
            }

            public String getCreate_time() {
                return create_time == null ? "" : create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time == null ? "" : create_time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDetail() {
                return detail == null ? "" : detail;
            }

            public void setDetail(String detail) {
                this.detail = detail == null ? "" : detail;
            }

            public String getNickname() {
                return nickname == null ? "" : nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname == null ? "" : nickname;
            }

            public String getRate_desc() {
                return rate_desc == null ? "" : rate_desc;
            }

            public void setRate_desc(String rate_desc) {
                this.rate_desc = rate_desc == null ? "" : rate_desc;
            }
        }
    }
}
