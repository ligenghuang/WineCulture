package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDto {

    /**
     * status : 200
     * msg : 获取成功
     * data : {"order_id":300,"order_sn":"20190902183721996356","order_status":1,"pay_status":0,"shipping_status":0,"pay_type":{"pay_type":1,"pay_name":"余额支付"},"consignee":"颜小杰","mobile":"18958658952","address":"浙江省台州市温岭市滨海镇东楼村128号","coupon_price":"0.00","order_amount":"2500.00","total_amount":"2500.00","add_time":1567420641,"shipping_name":"","shipping_price":"0.00","user_note":"备注","pay_time":0,"user_money":"0.00","comment":0,"is_refund":0,"status":1,"order_refund":{"count_num":5},"goods_res":[{"goods_id":74,"goods_name":"长白山人参","goods_num":5,"spec_key_name":"规格:无","goods_price":"500.00","original_price":"980.00","img":"http://127.0.0.1:8021/upload/images/goods/20190716156326939839222.png"}]}
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
         * order_id : 300
         * order_sn : 20190902183721996356
         * order_status : 1
         * pay_status : 0
         * shipping_status : 0
         * pay_type : {"pay_type":1,"pay_name":"余额支付"}
         * consignee : 颜小杰
         * mobile : 18958658952
         * address : 浙江省台州市温岭市滨海镇东楼村128号
         * coupon_price : 0.00
         * order_amount : 2500.00
         * total_amount : 2500.00
         * add_time : 1567420641
         * shipping_name :
         * shipping_price : 0.00
         * user_note : 备注
         * pay_time : 0
         * user_money : 0.00
         * comment : 0
         * is_refund : 0
         * status : 1
         * order_refund : {"count_num":5}
         * goods_res : [{"goods_id":74,"goods_name":"长白山人参","goods_num":5,"spec_key_name":"规格:无","goods_price":"500.00","original_price":"980.00","img":"http://127.0.0.1:8021/upload/images/goods/20190716156326939839222.png"}]
         */

        private int order_id;
        private String order_sn;
        private int order_status;
        private int pay_status;
        private int shipping_status;
        private PayTypeBean pay_type;
        private String consignee;
        private String mobile;
        private String address;
        private String coupon_price;
        private String order_amount;
        private String total_amount;
        private int add_time;
        private String shipping_name;
        private String shipping_price;
        private String user_note;
        private int pay_time;
        private String user_money;
        private int comment;
        private int is_refund;
        private int status;
        private OrderRefundBean order_refund;
        private List<GoodsResBean> goods_res;
        private int is_pwd;

        public int getIs_pwd() {
            return is_pwd;
        }

        public void setIs_pwd(int is_pwd) {
            this.is_pwd = is_pwd;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn == null ? "" : order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn == null ? "" : order_sn;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(int shipping_status) {
            this.shipping_status = shipping_status;
        }

        public PayTypeBean getPay_type() {
            return pay_type;
        }

        public void setPay_type(PayTypeBean pay_type) {
            this.pay_type = pay_type;
        }

        public String getConsignee() {
            return consignee == null ? "" : consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee == null ? "" : consignee;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile == null ? "" : mobile;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public String getCoupon_price() {
            return coupon_price == null ? "" : coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price == null ? "" : coupon_price;
        }

        public String getOrder_amount() {
            return order_amount == null ? "" : order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount == null ? "" : order_amount;
        }

        public String getTotal_amount() {
            return total_amount == null ? "0" : total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount == null ? "0" : total_amount;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getShipping_name() {
            return shipping_name == null ? "" : shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name == null ? "" : shipping_name;
        }

        public String getShipping_price() {
            return shipping_price == null ? "" : shipping_price;
        }

        public void setShipping_price(String shipping_price) {
            this.shipping_price = shipping_price == null ? "" : shipping_price;
        }

        public String getUser_note() {
            return user_note == null ? "" : user_note;
        }

        public void setUser_note(String user_note) {
            this.user_note = user_note == null ? "" : user_note;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public String getUser_money() {
            return user_money == null ? "" : user_money;
        }

        public void setUser_money(String user_money) {
            this.user_money = user_money == null ? "" : user_money;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getIs_refund() {
            return is_refund;
        }

        public void setIs_refund(int is_refund) {
            this.is_refund = is_refund;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public OrderRefundBean getOrder_refund() {
            return order_refund;
        }

        public void setOrder_refund(OrderRefundBean order_refund) {
            this.order_refund = order_refund;
        }

        public List<GoodsResBean> getGoods_res() {
            if (goods_res == null) {
                return new ArrayList<>();
            }
            return goods_res;
        }

        public void setGoods_res(List<GoodsResBean> goods_res) {
            this.goods_res = goods_res;
        }

        public static class PayTypeBean {
            /**
             * pay_type : 1
             * pay_name : 余额支付
             */

            private int pay_type;
            private String pay_name;

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public String getPay_name() {
                return pay_name == null ? "" : pay_name;
            }

            public void setPay_name(String pay_name) {
                this.pay_name = pay_name == null ? "" : pay_name;
            }
        }

        public static class OrderRefundBean {
            /**
             * count_num : 5
             */

            private int count_num;

            public int getCount_num() {
                return count_num;
            }

            public void setCount_num(int count_num) {
                this.count_num = count_num;
            }
        }

        public static class GoodsResBean {
            /**
             * goods_id : 74
             * goods_name : 长白山人参
             * goods_num : 5
             * spec_key_name : 规格:无
             * goods_price : 500.00
             * original_price : 980.00
             * img : http://127.0.0.1:8021/upload/images/goods/20190716156326939839222.png
             */

            private int goods_id;
            private String goods_name;
            private int goods_num;
            private String spec_key_name;
            private String goods_price;
            private String original_price;
            private String img;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name == null ? "" : goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name == null ? "" : goods_name;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getSpec_key_name() {
                return spec_key_name == null ? "" : spec_key_name;
            }

            public void setSpec_key_name(String spec_key_name) {
                this.spec_key_name = spec_key_name == null ? "" : spec_key_name;
            }

            public String getGoods_price() {
                return goods_price == null ? "" : goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price == null ? "" : goods_price;
            }

            public String getOriginal_price() {
                return original_price == null ? "" : original_price;
            }

            public void setOriginal_price(String original_price) {
                this.original_price = original_price == null ? "" : original_price;
            }

            public String getImg() {
                return img == null ? "" : img;
            }

            public void setImg(String img) {
                this.img = img == null ? "" : img;
            }
        }
    }
}
