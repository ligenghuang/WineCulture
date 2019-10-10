package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailDto {

    /**
     * status : 200
     * msg : 获取成功
     * data : {"goods_id":103,"goods_name":"洗衣片套餐","type_id":0,"desc":"洗衣片套餐","content_param":"<p>洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐<\/p>","content":"<p>洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐<\/p>","goods_attr":"1","limited_start":0,"limited_end":0,"add_time":1567656062,"goods_spec":"[{\"key\":\"规格\",\"value\":\"洗衣片套餐一\"}]","price":"200.00","original_price":"300.00","cost_price":"100.00","cat_id1":25,"cat_id2":0,"stock":55952,"stock1":0,"less_stock_type":2,"shipping_setting":1,"shipping_price":"10.00","delivery_id":0,"is_hdfk":1,"is_distribution":1,"most_buy_number":0,"gift_points":"","number_sales":0,"single_number":0,"distributor_level":0,"is_full_return":0,"is_arrange_all":0,"shopping_all_return":0,"is_show":1,"dividend_agent_level":0,"is_del":0,"is_puls":0,"province_proportion":0,"tow_proportion":0,"infinite_proportion":0,"puls_discount":0,"share_discount":0,"is_gift":0,"attr_name":["精选"],"spec":{"spec_attr":[{"spec_id":1,"spec_name":"规格","res":[{"attr_id":34606,"attr_name":"洗衣片套餐一"}]}],"goods_sku":[{"sku_id":103,"goods_id":103,"sku_attr":"{\"1\":34606}","price":"200.00","groupon_price":"0.00","img":"","inventory":55952,"frozen_stock":9549,"sales":0,"virtual_sales":329,"sku_attr1":"34606","attr_id":34606,"attr_name":"洗衣片套餐一"}]},"groupon_price":"0.00","img":[{"picture":"http://laundry.com/public/upload/images/goods/20190909156803602332467.png"}],"collection":0,"comment_count":41,"coupon":[],"productAttr":[{"product_id":103,"attr_name":"规格","spec_id":1,"res":[{"attr_id":34606,"attr_name":"洗衣片套餐一"}]}],"productSku":[{"sku_id":103,"goods_id":103,"sku_attr":"{\"1\":34606}","price":"200.00","groupon_price":"0.00","img":"","inventory":55952,"frozen_stock":9549,"sales":0,"virtual_sales":329,"sku_attr1":"34606","attr_id":34606,"attr_name":"洗衣片套餐一"}],"address":"giiyiiihrffdf"}
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
         * goods_id : 103
         * goods_name : 洗衣片套餐
         * type_id : 0
         * desc : 洗衣片套餐
         * content_param : <p>洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐</p>
         * content : <p>洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐洗衣片套餐</p>
         * goods_attr : 1
         * limited_start : 0
         * limited_end : 0
         * add_time : 1567656062
         * goods_spec : [{"key":"规格","value":"洗衣片套餐一"}]
         * price : 200.00
         * original_price : 300.00
         * cost_price : 100.00
         * cat_id1 : 25
         * cat_id2 : 0
         * stock : 55952
         * stock1 : 0
         * less_stock_type : 2
         * shipping_setting : 1
         * shipping_price : 10.00
         * delivery_id : 0
         * is_hdfk : 1
         * is_distribution : 1
         * most_buy_number : 0
         * gift_points :
         * number_sales : 0
         * single_number : 0
         * distributor_level : 0
         * is_full_return : 0
         * is_arrange_all : 0
         * shopping_all_return : 0
         * is_show : 1
         * dividend_agent_level : 0
         * is_del : 0
         * is_puls : 0
         * province_proportion : 0
         * tow_proportion : 0
         * infinite_proportion : 0
         * puls_discount : 0
         * share_discount : 0
         * is_gift : 0
         * attr_name : ["精选"]
         * spec : {"spec_attr":[{"spec_id":1,"spec_name":"规格","res":[{"attr_id":34606,"attr_name":"洗衣片套餐一"}]}],"goods_sku":[{"sku_id":103,"goods_id":103,"sku_attr":"{\"1\":34606}","price":"200.00","groupon_price":"0.00","img":"","inventory":55952,"frozen_stock":9549,"sales":0,"virtual_sales":329,"sku_attr1":"34606","attr_id":34606,"attr_name":"洗衣片套餐一"}]}
         * groupon_price : 0.00
         * img : [{"picture":"http://laundry.com/public/upload/images/goods/20190909156803602332467.png"}]
         * collection : 0
         * comment_count : 41
         * coupon : []
         * productAttr : [{"product_id":103,"attr_name":"规格","spec_id":1,"res":[{"attr_id":34606,"attr_name":"洗衣片套餐一"}]}]
         * productSku : [{"sku_id":103,"goods_id":103,"sku_attr":"{\"1\":34606}","price":"200.00","groupon_price":"0.00","img":"","inventory":55952,"frozen_stock":9549,"sales":0,"virtual_sales":329,"sku_attr1":"34606","attr_id":34606,"attr_name":"洗衣片套餐一"}]
         * address : giiyiiihrffdf
         */

        private int goods_id;
        private String goods_name;
        private int type_id;
        private String desc;
        private String content_param;
        private String content;
        private String goods_attr;
        private int limited_start;
        private int limited_end;
        private int add_time;
        private String goods_spec;
        private String price;
        private String original_price;
        private String cost_price;
        private int cat_id1;
        private int cat_id2;
        private int stock;
        private int stock1;
        private int less_stock_type;
        private int shipping_setting;
        private String shipping_price;
        private int delivery_id;
        private int is_hdfk;
        private int is_distribution;
        private int most_buy_number;
        private String gift_points;
        private int number_sales;
        private int single_number;
        private int distributor_level;
        private int is_full_return;
        private int is_arrange_all;
        private int shopping_all_return;
        private int is_show;
        private int dividend_agent_level;
        private int is_del;
        private int is_puls;
        private int province_proportion;
        private int tow_proportion;
        private int infinite_proportion;
        private int puls_discount;
        private int share_discount;
        private int is_gift;
        private SpecBean spec;
        private String groupon_price;
        private int collection;
        private int comment_count;
        private String address;
        private List<String> attr_name;
        private List<ImgBean> img;
        private List<?> coupon;
        private List<ProductAttrBean> productAttr;
        private List<ProductSkuBean> productSku;

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

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc == null ? "" : desc;
        }

        public String getContent_param() {
            return content_param == null ? "" : content_param;
        }

        public void setContent_param(String content_param) {
            this.content_param = content_param == null ? "" : content_param;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content == null ? "" : content;
        }

        public String getGoods_attr() {
            return goods_attr == null ? "" : goods_attr;
        }

        public void setGoods_attr(String goods_attr) {
            this.goods_attr = goods_attr == null ? "" : goods_attr;
        }

        public int getLimited_start() {
            return limited_start;
        }

        public void setLimited_start(int limited_start) {
            this.limited_start = limited_start;
        }

        public int getLimited_end() {
            return limited_end;
        }

        public void setLimited_end(int limited_end) {
            this.limited_end = limited_end;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getGoods_spec() {
            return goods_spec == null ? "" : goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec == null ? "" : goods_spec;
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

        public String getCost_price() {
            return cost_price == null ? "" : cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price == null ? "" : cost_price;
        }

        public int getCat_id1() {
            return cat_id1;
        }

        public void setCat_id1(int cat_id1) {
            this.cat_id1 = cat_id1;
        }

        public int getCat_id2() {
            return cat_id2;
        }

        public void setCat_id2(int cat_id2) {
            this.cat_id2 = cat_id2;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getStock1() {
            return stock1;
        }

        public void setStock1(int stock1) {
            this.stock1 = stock1;
        }

        public int getLess_stock_type() {
            return less_stock_type;
        }

        public void setLess_stock_type(int less_stock_type) {
            this.less_stock_type = less_stock_type;
        }

        public int getShipping_setting() {
            return shipping_setting;
        }

        public void setShipping_setting(int shipping_setting) {
            this.shipping_setting = shipping_setting;
        }

        public String getShipping_price() {
            return shipping_price == null ? "" : shipping_price;
        }

        public void setShipping_price(String shipping_price) {
            this.shipping_price = shipping_price == null ? "" : shipping_price;
        }

        public int getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(int delivery_id) {
            this.delivery_id = delivery_id;
        }

        public int getIs_hdfk() {
            return is_hdfk;
        }

        public void setIs_hdfk(int is_hdfk) {
            this.is_hdfk = is_hdfk;
        }

        public int getIs_distribution() {
            return is_distribution;
        }

        public void setIs_distribution(int is_distribution) {
            this.is_distribution = is_distribution;
        }

        public int getMost_buy_number() {
            return most_buy_number;
        }

        public void setMost_buy_number(int most_buy_number) {
            this.most_buy_number = most_buy_number;
        }

        public String getGift_points() {
            return gift_points == null ? "" : gift_points;
        }

        public void setGift_points(String gift_points) {
            this.gift_points = gift_points == null ? "" : gift_points;
        }

        public int getNumber_sales() {
            return number_sales;
        }

        public void setNumber_sales(int number_sales) {
            this.number_sales = number_sales;
        }

        public int getSingle_number() {
            return single_number;
        }

        public void setSingle_number(int single_number) {
            this.single_number = single_number;
        }

        public int getDistributor_level() {
            return distributor_level;
        }

        public void setDistributor_level(int distributor_level) {
            this.distributor_level = distributor_level;
        }

        public int getIs_full_return() {
            return is_full_return;
        }

        public void setIs_full_return(int is_full_return) {
            this.is_full_return = is_full_return;
        }

        public int getIs_arrange_all() {
            return is_arrange_all;
        }

        public void setIs_arrange_all(int is_arrange_all) {
            this.is_arrange_all = is_arrange_all;
        }

        public int getShopping_all_return() {
            return shopping_all_return;
        }

        public void setShopping_all_return(int shopping_all_return) {
            this.shopping_all_return = shopping_all_return;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getDividend_agent_level() {
            return dividend_agent_level;
        }

        public void setDividend_agent_level(int dividend_agent_level) {
            this.dividend_agent_level = dividend_agent_level;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getIs_puls() {
            return is_puls;
        }

        public void setIs_puls(int is_puls) {
            this.is_puls = is_puls;
        }

        public int getProvince_proportion() {
            return province_proportion;
        }

        public void setProvince_proportion(int province_proportion) {
            this.province_proportion = province_proportion;
        }

        public int getTow_proportion() {
            return tow_proportion;
        }

        public void setTow_proportion(int tow_proportion) {
            this.tow_proportion = tow_proportion;
        }

        public int getInfinite_proportion() {
            return infinite_proportion;
        }

        public void setInfinite_proportion(int infinite_proportion) {
            this.infinite_proportion = infinite_proportion;
        }

        public int getPuls_discount() {
            return puls_discount;
        }

        public void setPuls_discount(int puls_discount) {
            this.puls_discount = puls_discount;
        }

        public int getShare_discount() {
            return share_discount;
        }

        public void setShare_discount(int share_discount) {
            this.share_discount = share_discount;
        }

        public int getIs_gift() {
            return is_gift;
        }

        public void setIs_gift(int is_gift) {
            this.is_gift = is_gift;
        }

        public SpecBean getSpec() {
            return spec;
        }

        public void setSpec(SpecBean spec) {
            this.spec = spec;
        }

        public String getGroupon_price() {
            return groupon_price == null ? "" : groupon_price;
        }

        public void setGroupon_price(String groupon_price) {
            this.groupon_price = groupon_price == null ? "" : groupon_price;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public List<String> getAttr_name() {
            if (attr_name == null) {
                return new ArrayList<>();
            }
            return attr_name;
        }

        public void setAttr_name(List<String> attr_name) {
            this.attr_name = attr_name;
        }

        public List<ImgBean> getImg() {
            if (img == null) {
                return new ArrayList<>();
            }
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<?> getCoupon() {
            if (coupon == null) {
                return new ArrayList<>();
            }
            return coupon;
        }

        public void setCoupon(List<?> coupon) {
            this.coupon = coupon;
        }

        public List<ProductAttrBean> getProductAttr() {
            if (productAttr == null) {
                return new ArrayList<>();
            }
            return productAttr;
        }

        public void setProductAttr(List<ProductAttrBean> productAttr) {
            this.productAttr = productAttr;
        }

        public List<ProductSkuBean> getProductSku() {
            if (productSku == null) {
                return new ArrayList<>();
            }
            return productSku;
        }

        public void setProductSku(List<ProductSkuBean> productSku) {
            this.productSku = productSku;
        }

        public static class SpecBean {
            private List<SpecAttrBean> spec_attr;
            private List<GoodsSkuBean> goods_sku;

            public List<SpecAttrBean> getSpec_attr() {
                if (spec_attr == null) {
                    return new ArrayList<>();
                }
                return spec_attr;
            }

            public void setSpec_attr(List<SpecAttrBean> spec_attr) {
                this.spec_attr = spec_attr;
            }

            public List<GoodsSkuBean> getGoods_sku() {
                if (goods_sku == null) {
                    return new ArrayList<>();
                }
                return goods_sku;
            }

            public void setGoods_sku(List<GoodsSkuBean> goods_sku) {
                this.goods_sku = goods_sku;
            }

            public static class SpecAttrBean {
                /**
                 * spec_id : 1
                 * spec_name : 规格
                 * res : [{"attr_id":34606,"attr_name":"洗衣片套餐一"}]
                 */

                private int spec_id;
                private String spec_name;
                private List<ResBean> res;

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getSpec_name() {
                    return spec_name == null ? "" : spec_name;
                }

                public void setSpec_name(String spec_name) {
                    this.spec_name = spec_name == null ? "" : spec_name;
                }

                public List<ResBean> getRes() {
                    if (res == null) {
                        return new ArrayList<>();
                    }
                    return res;
                }

                public void setRes(List<ResBean> res) {
                    this.res = res;
                }

                public static class ResBean {
                    /**
                     * attr_id : 34606
                     * attr_name : 洗衣片套餐一
                     */

                    private int attr_id;
                    private String attr_name;

                    public int getAttr_id() {
                        return attr_id;
                    }

                    public void setAttr_id(int attr_id) {
                        this.attr_id = attr_id;
                    }

                    public String getAttr_name() {
                        return attr_name == null ? "" : attr_name;
                    }

                    public void setAttr_name(String attr_name) {
                        this.attr_name = attr_name == null ? "" : attr_name;
                    }
                }
            }

            public static class GoodsSkuBean {
                /**
                 * sku_id : 103
                 * goods_id : 103
                 * sku_attr : {"1":34606}
                 * price : 200.00
                 * groupon_price : 0.00
                 * img :
                 * inventory : 55952
                 * frozen_stock : 9549
                 * sales : 0
                 * virtual_sales : 329
                 * sku_attr1 : 34606
                 * attr_id : 34606
                 * attr_name : 洗衣片套餐一
                 */

                private int sku_id;
                private int goods_id;
                private String sku_attr;
                private String price;
                private String groupon_price;
                private String img;
                private int inventory;
                private int frozen_stock;
                private int sales;
                private int virtual_sales;
                private String sku_attr1;
                private int attr_id;
                private String attr_name;

                public int getSku_id() {
                    return sku_id;
                }

                public void setSku_id(int sku_id) {
                    this.sku_id = sku_id;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public String getSku_attr() {
                    return sku_attr == null ? "" : sku_attr;
                }

                public void setSku_attr(String sku_attr) {
                    this.sku_attr = sku_attr == null ? "" : sku_attr;
                }

                public String getPrice() {
                    return price == null ? "" : price;
                }

                public void setPrice(String price) {
                    this.price = price == null ? "" : price;
                }

                public String getGroupon_price() {
                    return groupon_price == null ? "" : groupon_price;
                }

                public void setGroupon_price(String groupon_price) {
                    this.groupon_price = groupon_price == null ? "" : groupon_price;
                }

                public String getImg() {
                    return img == null ? "" : img;
                }

                public void setImg(String img) {
                    this.img = img == null ? "" : img;
                }

                public int getInventory() {
                    return inventory;
                }

                public void setInventory(int inventory) {
                    this.inventory = inventory;
                }

                public int getFrozen_stock() {
                    return frozen_stock;
                }

                public void setFrozen_stock(int frozen_stock) {
                    this.frozen_stock = frozen_stock;
                }

                public int getSales() {
                    return sales;
                }

                public void setSales(int sales) {
                    this.sales = sales;
                }

                public int getVirtual_sales() {
                    return virtual_sales;
                }

                public void setVirtual_sales(int virtual_sales) {
                    this.virtual_sales = virtual_sales;
                }

                public String getSku_attr1() {
                    return sku_attr1 == null ? "" : sku_attr1;
                }

                public void setSku_attr1(String sku_attr1) {
                    this.sku_attr1 = sku_attr1 == null ? "" : sku_attr1;
                }

                public int getAttr_id() {
                    return attr_id;
                }

                public void setAttr_id(int attr_id) {
                    this.attr_id = attr_id;
                }

                public String getAttr_name() {
                    return attr_name == null ? "" : attr_name;
                }

                public void setAttr_name(String attr_name) {
                    this.attr_name = attr_name == null ? "" : attr_name;
                }
            }
        }

        public static class ImgBean {
            /**
             * picture : http://laundry.com/public/upload/images/goods/20190909156803602332467.png
             */

            private String picture;

            public String getPicture() {
                return picture == null ? "" : picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }

        public static class ProductAttrBean {
            /**
             * product_id : 103
             * attr_name : 规格
             * spec_id : 1
             * res : [{"attr_id":34606,"attr_name":"洗衣片套餐一"}]
             */

            private int product_id;
            private String attr_name;
            private int spec_id;
            private List<ResBeanX> res;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public List<ResBeanX> getRes() {
                return res;
            }

            public void setRes(List<ResBeanX> res) {
                this.res = res;
            }

            public static class ResBeanX {
                /**
                 * attr_id : 34606
                 * attr_name : 洗衣片套餐一
                 */

                private int attr_id;
                private String attr_name;

                public int getAttr_id() {
                    return attr_id;
                }

                public void setAttr_id(int attr_id) {
                    this.attr_id = attr_id;
                }

                public String getAttr_name() {
                    return attr_name;
                }

                public void setAttr_name(String attr_name) {
                    this.attr_name = attr_name;
                }
            }
        }

        public static class ProductSkuBean {
            /**
             * sku_id : 103
             * goods_id : 103
             * sku_attr : {"1":34606}
             * price : 200.00
             * groupon_price : 0.00
             * img :
             * inventory : 55952
             * frozen_stock : 9549
             * sales : 0
             * virtual_sales : 329
             * sku_attr1 : 34606
             * attr_id : 34606
             * attr_name : 洗衣片套餐一
             */

            private int sku_id;
            private int goods_id;
            private String sku_attr;
            private String price;
            private String groupon_price;
            private String img;
            private int inventory;
            private int frozen_stock;
            private int sales;
            private int virtual_sales;
            private String sku_attr1;
            private int attr_id;
            private String attr_name;

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getSku_attr() {
                return sku_attr;
            }

            public void setSku_attr(String sku_attr) {
                this.sku_attr = sku_attr;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGroupon_price() {
                return groupon_price;
            }

            public void setGroupon_price(String groupon_price) {
                this.groupon_price = groupon_price;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getInventory() {
                return inventory;
            }

            public void setInventory(int inventory) {
                this.inventory = inventory;
            }

            public int getFrozen_stock() {
                return frozen_stock;
            }

            public void setFrozen_stock(int frozen_stock) {
                this.frozen_stock = frozen_stock;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public int getVirtual_sales() {
                return virtual_sales;
            }

            public void setVirtual_sales(int virtual_sales) {
                this.virtual_sales = virtual_sales;
            }

            public String getSku_attr1() {
                return sku_attr1;
            }

            public void setSku_attr1(String sku_attr1) {
                this.sku_attr1 = sku_attr1;
            }

            public int getAttr_id() {
                return attr_id;
            }

            public void setAttr_id(int attr_id) {
                this.attr_id = attr_id;
            }

            public String getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }
        }
    }
}
