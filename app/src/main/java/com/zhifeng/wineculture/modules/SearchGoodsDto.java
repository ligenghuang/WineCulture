package com.zhifeng.wineculture.modules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchGoodsDto {


    /**
     * status : 200
     * msg : 获取成功
     * data : {"banners":[{"picture":"http://jiuwenhua.cn/public/\\uploads\\fixed_picture\\20191016\\e5e49456337050c2e207eaa2a288f447.jpeg","title":"搜索","url":""}],"list":{"total":2,"per_page":20,"current_page":1,"last_page":1,"data":[{"goods_id":98,"goods_name":"全自动一体热水壶","price":"298.00","number_sales":5},{"goods_id":97,"goods_name":"全自动一体抽水壶","price":"188.00","number_sales":1}],"0":{"picture":"http://jiuwenhua.cn/public/upload/images/goods/20190716156328278877846.png","praise":"100%"},"1":{"picture":"http://jiuwenhua.cn/public/upload/images/goods/20190716156328271196140.png","praise":"100%"}}}
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
         * banners : [{"picture":"http://jiuwenhua.cn/public/\\uploads\\fixed_picture\\20191016\\e5e49456337050c2e207eaa2a288f447.jpeg","title":"搜索","url":""}]
         * list : {"total":2,"per_page":20,"current_page":1,"last_page":1,"data":[{"goods_id":98,"goods_name":"全自动一体热水壶","price":"298.00","number_sales":5},{"goods_id":97,"goods_name":"全自动一体抽水壶","price":"188.00","number_sales":1}],"0":{"picture":"http://jiuwenhua.cn/public/upload/images/goods/20190716156328278877846.png","praise":"100%"},"1":{"picture":"http://jiuwenhua.cn/public/upload/images/goods/20190716156328271196140.png","praise":"100%"}}
         */

        private ListBean list;
        private List<BannersBean> banners;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public List<BannersBean> getBanners() {
            if (banners == null) {
                return new ArrayList<>();
            }
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public static class ListBean {
            /**
             * total : 2
             * per_page : 20
             * current_page : 1
             * last_page : 1
             * data : [{"goods_id":98,"goods_name":"全自动一体热水壶","price":"298.00","number_sales":5},{"goods_id":97,"goods_name":"全自动一体抽水壶","price":"188.00","number_sales":1}]
             * 0 : {"picture":"http://jiuwenhua.cn/public/upload/images/goods/20190716156328278877846.png","praise":"100%"}
             * 1 : {"picture":"http://jiuwenhua.cn/public/upload/images/goods/20190716156328271196140.png","praise":"100%"}
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
                 * goods_id : 98
                 * goods_name : 全自动一体热水壶
                 * price : 298.00
                 * number_sales : 5
                 */

                private int goods_id;
                private String goods_name;
                private String price;
                private int number_sales;
                private String picture;
                private String praise;

                public String getPraise() {
                    return praise == null ? "" : praise;
                }

                public void setPraise(String praise) {
                    this.praise = praise == null ? "" : praise;
                }

                public String getPicture() {
                    return picture == null ? "" : picture;
                }

                public void setPicture(String picture) {
                    this.picture = picture == null ? "" : picture;
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

                public int getNumber_sales() {
                    return number_sales;
                }

                public void setNumber_sales(int number_sales) {
                    this.number_sales = number_sales;
                }
            }
        }

        public static class BannersBean {
            /**
             * picture :
             * title : 搜索
             * url :
             */

            private String picture;
            private String title;
            private String url;

            public String getPicture() {
                return picture == null ? "" : picture;
            }

            public void setPicture(String picture) {
                this.picture = picture == null ? "" : picture;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title == null ? "" : title;
            }

            public String getUrl() {
                return url == null ? "" : url;
            }

            public void setUrl(String url) {
                this.url = url == null ? "" : url;
            }
        }
    }
}
