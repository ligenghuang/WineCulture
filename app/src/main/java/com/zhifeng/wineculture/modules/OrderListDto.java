package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class OrderListDto {

    /**
     * status : 200
     * msg : 获取成功
     * data : [{"order_id":18,"order_sn":"20190731134727626648","comment":0,"order_status":3,"pay_status":0,"shipping_status":0,"pay_type":1,"add_time":1564552047,"goods_price":"500.00","total_amount":"500.00","order_amount":"500.00","shipping_price":"0.00","is_refund":0,"goods":[{"goods_name":"商务保温杯","goods_id":72,"sku_id":71,"img":"http://laundry.com/public/upload/images/goods/20190717156329284277081.png","spec_key_name":"规格:金色","goods_price":"500.00","goods_num":1}],"goods_num":1,"status":5},{"order_id":17,"order_sn":"20190731134710153733","comment":0,"order_status":4,"pay_status":1,"shipping_status":3,"pay_type":1,"add_time":1564552030,"goods_price":"500.00","total_amount":"500.00","order_amount":"500.00","shipping_price":"0.00","is_refund":0,"goods":[{"goods_name":"商务保温杯","goods_id":72,"sku_id":71,"img":"http://laundry.com/public/upload/images/goods/20190717156329284277081.png","spec_key_name":"规格:金色","goods_price":"500.00","goods_num":1},{"goods_name":"长白山人参","goods_id":74,"sku_id":74,"img":"http://laundry.com/public/upload/images/goods/20190905156765395068038.png","spec_key_name":"规格:无","goods_price":"500.00","goods_num":1}],"goods_num":2,"status":4}]
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
         * order_id : 18
         * order_sn : 20190731134727626648
         * comment : 0
         * order_status : 3
         * pay_status : 0
         * shipping_status : 0
         * pay_type : 1
         * add_time : 1564552047
         * goods_price : 500.00
         * total_amount : 500.00
         * order_amount : 500.00
         * shipping_price : 0.00
         * is_refund : 0
         * goods : [{"goods_name":"商务保温杯","goods_id":72,"sku_id":71,"img":"http://laundry.com/public/upload/images/goods/20190717156329284277081.png","spec_key_name":"规格:金色","goods_price":"500.00","goods_num":1}]
         * goods_num : 1
         * status : 5
         */

        private int order_id;
        private String order_sn;
        private int comment;
        private int order_status;
        private int pay_status;
        private int shipping_status;
        private int pay_type;
        private long add_time;
        private String goods_price;
        private String total_amount;
        private String order_amount;
        private String shipping_price;
        private int is_refund;
        private int goods_num;
        private int status;
        private List<GoodsBean> goods;

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

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
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

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getGoods_price() {
            return goods_price == null ? "" : goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price == null ? "" : goods_price;
        }

        public String getTotal_amount() {
            return total_amount == null ? "" : total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount == null ? "" : total_amount;
        }

        public String getOrder_amount() {
            return order_amount == null ? "" : order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount == null ? "" : order_amount;
        }

        public String getShipping_price() {
            return shipping_price == null ? "" : shipping_price;
        }

        public void setShipping_price(String shipping_price) {
            this.shipping_price = shipping_price == null ? "" : shipping_price;
        }

        public int getIs_refund() {
            return is_refund;
        }

        public void setIs_refund(int is_refund) {
            this.is_refund = is_refund;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<GoodsBean> getGoods() {
            if (goods == null) {
                return new ArrayList<>();
            }
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goods_name : 商务保温杯
             * goods_id : 72
             * sku_id : 71
             * img : http://laundry.com/public/upload/images/goods/20190717156329284277081.png
             * spec_key_name : 规格:金色
             * goods_price : 500.00
             * goods_num : 1
             */

            private String goods_name;
            private int goods_id;
            private int sku_id;
            private String img;
            private String spec_key_name;
            private String goods_price;
            private int goods_num;

            public String getGoods_name() {
                return goods_name == null ? "" : goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name == null ? "" : goods_name;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public String getImg() {
                return img == null ? "" : img;
            }

            public void setImg(String img) {
                this.img = img == null ? "" : img;
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

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }
        }
    }
}
