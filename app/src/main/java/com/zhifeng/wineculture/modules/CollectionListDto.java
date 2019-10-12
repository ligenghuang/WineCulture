package com.zhifeng.wineculture.modules;

import java.util.List;
/**
  *
  * @ClassName:     收藏列表实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/12 16:04
  * @Version:        1.0
 */

public class CollectionListDto {


    /**
     * status : 200
     * msg : 成功！
     * data : [{"goods_id":97,"goods_name":"全自动一体抽水壶","price":"188.00","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328271196140.png"},{"goods_id":98,"goods_name":"全自动一体热水壶","price":"298.00","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328278877846.png"}]
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
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * goods_id : 97
         * goods_name : 全自动一体抽水壶
         * price : 188.00
         * img :
         */

        private int goods_id;
        private String goods_name;
        private String price;
        private String img;
        private String original_price;

        public String getOriginal_price() {
            return original_price == null ? "0" : original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price == null ? "0" : original_price;
        }

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

        public String getPrice() {
            return price == null ? "0" : price;
        }

        public void setPrice(String price) {
            this.price = price == null ? "0" : price;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img == null ? "" : img;
        }
    }
}
