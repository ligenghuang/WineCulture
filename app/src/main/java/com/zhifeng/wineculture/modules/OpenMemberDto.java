package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class OpenMemberDto {


    /**
     * status : 200
     * msg : 获取成功
     * data : [{"goods_id":106,"goods_name":"3999","img":"http://jiuwenhua.cn/upload/images/goods/20191016157120855562612.png","price":"0.00","original_price":"0.00","attr_name":[],"comment":0},{"goods_id":105,"goods_name":"999","img":"http://jiuwenhua.cn/upload/images/goods/20191015157110337666558.png","price":"0.00","original_price":"0.00","attr_name":[],"comment":0}]
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
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
         * goods_id : 106
         * goods_name : 3999
         * img : http://jiuwenhua.cn/upload/images/goods/20191016157120855562612.png
         * price : 0.00
         * original_price : 0.00
         * attr_name : []
         * comment : 0
         */

        private int goods_id;
        private String goods_name;
        private String img;
        private String price;
        private String original_price;
        private int comment;
        private List<?> attr_name;

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

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img == null ? "" : img;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price == null ? "" : price;
        }

        public String getOriginal_price() {
            return original_price == null ? "" : original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price == null ? "" : original_price;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public List<?> getAttr_name() {
            if (attr_name == null) {
                return new ArrayList<>();
            }
            return attr_name;
        }

        public void setAttr_name(List<?> attr_name) {
            this.attr_name = attr_name;
        }
    }
}
