package com.zhifeng.wineculture.modules;

public class AnnounceDetailDto {


    /**
     * status : 200
     * msg : success
     * data : {"id":4,"title":"普瑞廷新零售商城产品上线公告","brief":"根深蒂固发送到","urllink":"","desc":"<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;普瑞廷新零售商城产品上线公告<\/p><p>普瑞廷的家人们，您们好，在此宣布一个好消息，商城上线需要全国有特色的产品，所以商城的家人们有好的产品都可以提供给平台上架，我相信家人们的产品会在我们的商城大卖特卖，而且商城前期不收取商家的产品上架费，完全免费开放，只要产品证件齐全，质量有保障，不是过期和变质的产品，都可以申请上架商城，产品被购买以后由商家发货，消费者确认收款了以后，由平台跟商家进行结算。这对家人们是非常利好的消息和福利政策，因为以后平台会有大量的家人在商城进行购物，所以产品想不好卖都不行，所以在全国有好产品的家人们，把产品通通提供给我们商城吧，在此提前预祝家人们在商城的产品大卖特卖，财富滚滚来！<\/p><p>&nbsp;<\/p><p>&nbsp;<\/p><p>&nbsp;<\/p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 普瑞廷新零售商城运营部<\/p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 2019年7月17日星期三<\/p><p><br/><\/p>","create_time":1563415873,"status":1}
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
         * id : 4
         * title : 普瑞廷新零售商城产品上线公告
         * brief : 根深蒂固发送到
         * urllink :
         * desc : <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;普瑞廷新零售商城产品上线公告</p><p>普瑞廷的家人们，您们好，在此宣布一个好消息，商城上线需要全国有特色的产品，所以商城的家人们有好的产品都可以提供给平台上架，我相信家人们的产品会在我们的商城大卖特卖，而且商城前期不收取商家的产品上架费，完全免费开放，只要产品证件齐全，质量有保障，不是过期和变质的产品，都可以申请上架商城，产品被购买以后由商家发货，消费者确认收款了以后，由平台跟商家进行结算。这对家人们是非常利好的消息和福利政策，因为以后平台会有大量的家人在商城进行购物，所以产品想不好卖都不行，所以在全国有好产品的家人们，把产品通通提供给我们商城吧，在此提前预祝家人们在商城的产品大卖特卖，财富滚滚来！</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 普瑞廷新零售商城运营部</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 2019年7月17日星期三</p><p><br/></p>
         * create_time : 1563415873
         * status : 1
         */

        private int id;
        private String title;
        private String brief;
        private String urllink;
        private String desc;
        private int create_time;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title == null ? "" : title;
        }

        public String getBrief() {
            return brief == null ? "" : brief;
        }

        public void setBrief(String brief) {
            this.brief = brief == null ? "" : brief;
        }

        public String getUrllink() {
            return urllink == null ? "" : urllink;
        }

        public void setUrllink(String urllink) {
            this.urllink = urllink == null ? "" : urllink;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc == null ? "" : desc;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
