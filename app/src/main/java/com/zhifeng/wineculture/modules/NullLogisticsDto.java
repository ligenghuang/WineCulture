package com.zhifeng.wineculture.modules;

import java.util.List;

public class NullLogisticsDto {


    /**
     * status : 200
     * msg : 获取成功
     * data : {"wuliu":{"number":"123456","type":"顺丰快递"},"goods_res":[{"goods_id":98,"goods_name":"全自动一体热水壶","goods_num":1,"spec_key_name":"规格:无","goods_price":"1500.00","original_price":"558.00","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328278877846.png"}],"address":"北京市北京市东城区hhhhh"}
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
        /**
         * wuliu : {"number":"123456","type":"顺丰快递"}
         * goods_res : [{"goods_id":98,"goods_name":"全自动一体热水壶","goods_num":1,"spec_key_name":"规格:无","goods_price":"1500.00","original_price":"558.00","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328278877846.png"}]
         * address : 北京市北京市东城区hhhhh
         */

        private WuliuBean wuliu;
        private String address;
        private List<GoodsResBean> goods_res;

        public WuliuBean getWuliu() {
            return wuliu;
        }

        public void setWuliu(WuliuBean wuliu) {
            this.wuliu = wuliu;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<GoodsResBean> getGoods_res() {
            return goods_res;
        }

        public void setGoods_res(List<GoodsResBean> goods_res) {
            this.goods_res = goods_res;
        }

        public static class WuliuBean {
            /**
             * number : 123456
             * type : 顺丰快递
             */

            private String number;
            private String type;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class GoodsResBean {
            /**
             * goods_id : 98
             * goods_name : 全自动一体热水壶
             * goods_num : 1
             * spec_key_name : 规格:无
             * goods_price : 1500.00
             * original_price : 558.00
             * img : http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328278877846.png
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
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getSpec_key_name() {
                return spec_key_name;
            }

            public void setSpec_key_name(String spec_key_name) {
                this.spec_key_name = spec_key_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(String original_price) {
                this.original_price = original_price;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
