package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class MyCommentListDto {

    /**
     * status : 200
     * msg : 成功！
     * data : [{"order_id":8,"goods_id":74,"sku_id":74,"goods_name":"长白山人参","goods_num":1,"goods_price":"500.00","spec_key_name":"规格:无","img":"http://jiuwenhua.cn/public/images/goods/20190716156326939839222.png","order_amount":"500.00","comment":"0"},{"order_id":9,"goods_id":74,"sku_id":74,"goods_name":"长白山人参","goods_num":1,"goods_price":"500.00","spec_key_name":"规格:无","img":"http://jiuwenhua.cn/public/images/goods/20190716156326939839222.png","order_amount":"500.00","comment":"1"}]
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
         * order_id : 8
         * goods_id : 74
         * sku_id : 74
         * goods_name : 长白山人参
         * goods_num : 1
         * goods_price : 500.00
         * spec_key_name : 规格:无
         * img : http://jiuwenhua.cn/public/images/goods/20190716156326939839222.png
         * order_amount : 500.00
         * comment : 0
         */

        private int order_id;
        private int goods_id;
        private int sku_id;
        private String goods_name;
        private int goods_num;
        private String goods_price;
        private String spec_key_name;
        private String img;
        private String order_amount;
        private int comment;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
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

        public String getGoods_price() {
            return goods_price == null ? "" : goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price == null ? "" : goods_price;
        }

        public String getSpec_key_name() {
            return spec_key_name == null ? "" : spec_key_name;
        }

        public void setSpec_key_name(String spec_key_name) {
            this.spec_key_name = spec_key_name == null ? "" : spec_key_name;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img == null ? "" : img;
        }

        public String getOrder_amount() {
            return order_amount == null ? "" : order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount == null ? "" : order_amount;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }
    }
}
