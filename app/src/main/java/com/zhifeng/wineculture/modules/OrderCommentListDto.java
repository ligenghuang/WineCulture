package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class OrderCommentListDto {

    /**
     * status : 200
     * msg : 成功！
     * data : [{"goods_id":18,"sku_id":2,"goods_name":"美的（Midea） 三门冰箱 风冷无霜家","goods_num":3,"spec_key_name":"规格:升级版,颜色:星空灰,尺寸:大","img":"goods/20190515155791790834093.png"}]
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
         * goods_id : 18
         * sku_id : 2
         * goods_name : 美的（Midea） 三门冰箱 风冷无霜家
         * goods_num : 3
         * spec_key_name : 规格:升级版,颜色:星空灰,尺寸:大
         * img : goods/20190515155791790834093.png
         */

        private int goods_id;
        private int sku_id;
        private String goods_name;
        private int goods_num;
        private String spec_key_name;
        private String img;
        private String note;

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

        public String getNote() {
            return note == null ? "" : note;
        }

        public void setNote(String note) {
            this.note = note == null ? "" : note;
        }
    }
}
