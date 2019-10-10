package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class ClassifyDto {


    /**
     * status : 200
     * msg : 获取成功
     * data : [{"cat_id":27,"cat_name":"多功能商品","pid":0,"level":1,"img":"","is_show":1,"desc":"","sort":1,"goods":[{"goods_id":101,"goods_name":"百年九阳丹","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190720156361736228498.png","price":"188.00","original_price":"328.00","attr_name":["精选"],"comment":0},{"goods_id":99,"goods_name":"YY-JDJ-A(超声波）","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328288666545.png","price":"680.00","original_price":"4280.00","attr_name":["精选"],"comment":1},{"goods_id":98,"goods_name":"全自动一体热水壶","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328278877846.png","price":"298.00","original_price":"558.00","attr_name":["热卖"],"comment":0},{"goods_id":97,"goods_name":"全自动一体抽水壶","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328271196140.png","price":"188.00","original_price":"288.00","attr_name":["热卖"],"comment":0},{"goods_id":96,"goods_name":"手持挂烫机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328263531950.png","price":"128.00","original_price":"398.00","attr_name":["精选"],"comment":0},{"goods_id":95,"goods_name":"高端烘干消毒机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/2019071615632825502490.png","price":"228.00","original_price":"530.00","attr_name":["精选"],"comment":0},{"goods_id":94,"goods_name":"推杆式真空吸尘器","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328247418541.png","price":"330.00","original_price":"980.00","attr_name":["精选"],"comment":0},{"goods_id":93,"goods_name":"多功能电烤箱","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/201907161563282401631.png","price":"358.00","original_price":"799.00","attr_name":["精选"],"comment":0},{"goods_id":92,"goods_name":"加湿冷风机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328232261363.png","price":"780.00","original_price":"2980.00","attr_name":["精选"],"comment":0},{"goods_id":91,"goods_name":"果蔬解毒净食机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/2019071615632802851842.png","price":"390.00","original_price":"780.00","attr_name":["热卖"],"comment":0},{"goods_id":90,"goods_name":"智能隔水炖盅","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328018637091.png","price":"178.00","original_price":"398.00","attr_name":["精选"],"comment":0},{"goods_id":88,"goods_name":"智能豪华家用落地扇","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327999280519.png","price":"388.00","original_price":"880.00","attr_name":["精选"],"comment":0},{"goods_id":87,"goods_name":"多功能电子秤","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327983064562.png","price":"128.00","original_price":"298.00","attr_name":["精选"],"comment":0},{"goods_id":86,"goods_name":"豪华智能电蚊拍","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327973397372.png","price":"36.00","original_price":"99.00","attr_name":[],"comment":0},{"goods_id":85,"goods_name":"多功能电饭煲","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327962843629.png","price":"280.00","original_price":"580.00","attr_name":["精选"],"comment":0},{"goods_id":84,"goods_name":"智能电饭煲","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327951726432.png","price":"280.00","original_price":"680.00","attr_name":["精选"],"comment":0},{"goods_id":82,"goods_name":"电动果汁杯","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327924151076.png","price":"268.00","original_price":"580.00","attr_name":["精选"],"comment":0},{"goods_id":81,"goods_name":"多功能蒸汽熨衣机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327885859811.png","price":"358.00","original_price":"780.00","attr_name":["精选"],"comment":0},{"goods_id":79,"goods_name":"多功能无叶风扇","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327855092286.png","price":"818.00","original_price":"1980.00","attr_name":["热卖"],"comment":0},{"goods_id":77,"goods_name":"多功能刷烤一体锅","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327432961302.png","price":"338.00","original_price":"680.00","attr_name":["热卖"],"comment":0},{"goods_id":76,"goods_name":"多功能绞肉机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327417841492.png","price":"338.00","original_price":"880.00","attr_name":["精选"],"comment":0},{"goods_id":75,"goods_name":"智能光催化灭蚊器","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/2019071615632698057452.png","price":"110.00","original_price":"298.00","attr_name":["热卖"],"comment":0}]},{"cat_id":25,"cat_name":"活动专区","pid":0,"level":1,"img":"","is_show":1,"desc":"","sort":1,"goods":[{"goods_id":74,"goods_name":"长白山人参","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156326939839222.png","price":"500.00","original_price":"980.00","attr_name":[],"comment":10},{"goods_id":72,"goods_name":"商务保温杯","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190717156329284277081.png","price":"500.00","original_price":"890.00","attr_name":[],"comment":21}]}]
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
         * cat_id : 27
         * cat_name : 多功能商品
         * pid : 0
         * level : 1
         * img :
         * is_show : 1
         * desc :
         * sort : 1
         * goods : [{"goods_id":101,"goods_name":"百年九阳丹","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190720156361736228498.png","price":"188.00","original_price":"328.00","attr_name":["精选"],"comment":0},{"goods_id":99,"goods_name":"YY-JDJ-A(超声波）","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328288666545.png","price":"680.00","original_price":"4280.00","attr_name":["精选"],"comment":1},{"goods_id":98,"goods_name":"全自动一体热水壶","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328278877846.png","price":"298.00","original_price":"558.00","attr_name":["热卖"],"comment":0},{"goods_id":97,"goods_name":"全自动一体抽水壶","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328271196140.png","price":"188.00","original_price":"288.00","attr_name":["热卖"],"comment":0},{"goods_id":96,"goods_name":"手持挂烫机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328263531950.png","price":"128.00","original_price":"398.00","attr_name":["精选"],"comment":0},{"goods_id":95,"goods_name":"高端烘干消毒机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/2019071615632825502490.png","price":"228.00","original_price":"530.00","attr_name":["精选"],"comment":0},{"goods_id":94,"goods_name":"推杆式真空吸尘器","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328247418541.png","price":"330.00","original_price":"980.00","attr_name":["精选"],"comment":0},{"goods_id":93,"goods_name":"多功能电烤箱","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/201907161563282401631.png","price":"358.00","original_price":"799.00","attr_name":["精选"],"comment":0},{"goods_id":92,"goods_name":"加湿冷风机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328232261363.png","price":"780.00","original_price":"2980.00","attr_name":["精选"],"comment":0},{"goods_id":91,"goods_name":"果蔬解毒净食机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/2019071615632802851842.png","price":"390.00","original_price":"780.00","attr_name":["热卖"],"comment":0},{"goods_id":90,"goods_name":"智能隔水炖盅","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156328018637091.png","price":"178.00","original_price":"398.00","attr_name":["精选"],"comment":0},{"goods_id":88,"goods_name":"智能豪华家用落地扇","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327999280519.png","price":"388.00","original_price":"880.00","attr_name":["精选"],"comment":0},{"goods_id":87,"goods_name":"多功能电子秤","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327983064562.png","price":"128.00","original_price":"298.00","attr_name":["精选"],"comment":0},{"goods_id":86,"goods_name":"豪华智能电蚊拍","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327973397372.png","price":"36.00","original_price":"99.00","attr_name":[],"comment":0},{"goods_id":85,"goods_name":"多功能电饭煲","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327962843629.png","price":"280.00","original_price":"580.00","attr_name":["精选"],"comment":0},{"goods_id":84,"goods_name":"智能电饭煲","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327951726432.png","price":"280.00","original_price":"680.00","attr_name":["精选"],"comment":0},{"goods_id":82,"goods_name":"电动果汁杯","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327924151076.png","price":"268.00","original_price":"580.00","attr_name":["精选"],"comment":0},{"goods_id":81,"goods_name":"多功能蒸汽熨衣机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327885859811.png","price":"358.00","original_price":"780.00","attr_name":["精选"],"comment":0},{"goods_id":79,"goods_name":"多功能无叶风扇","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327855092286.png","price":"818.00","original_price":"1980.00","attr_name":["热卖"],"comment":0},{"goods_id":77,"goods_name":"多功能刷烤一体锅","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327432961302.png","price":"338.00","original_price":"680.00","attr_name":["热卖"],"comment":0},{"goods_id":76,"goods_name":"多功能绞肉机","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190716156327417841492.png","price":"338.00","original_price":"880.00","attr_name":["精选"],"comment":0},{"goods_id":75,"goods_name":"智能光催化灭蚊器","img":"http://jiuwenhua.zhifengwangluo.com/upload/images/goods/2019071615632698057452.png","price":"110.00","original_price":"298.00","attr_name":["热卖"],"comment":0}]
         */

        private int cat_id;
        private String cat_name;
        private int pid;
        private int level;
        private String img;
        private int is_show;
        private String desc;
        private int sort;
        private List<GoodsBean> goods;
        boolean isClick;

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public int getCat_id() {
            return cat_id;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public String getCat_name() {
            return cat_name == null ? "" : cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name == null ? "" : cat_name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img == null ? "" : img;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc == null ? "" : desc;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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
             * goods_id : 101
             * goods_name : 百年九阳丹
             * img : http://jiuwenhua.zhifengwangluo.com/upload/images/goods/20190720156361736228498.png
             * price : 188.00
             * original_price : 328.00
             * attr_name : ["精选"]
             * comment : 0
             */

            private int goods_id;
            private String goods_name;
            private String img;
            private String price;
            private String original_price;
            private int comment;
            private List<String> attr_name;

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

            public List<String> getAttr_name() {
                if (attr_name == null) {
                    return new ArrayList<>();
                }
                return attr_name;
            }

            public void setAttr_name(List<String> attr_name) {
                this.attr_name = attr_name;
            }
        }
    }
}
