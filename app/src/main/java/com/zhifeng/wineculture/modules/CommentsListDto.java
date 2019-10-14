package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class CommentsListDto {

    /**
     * status : 1
     * msg : 获取成功
     * data : [{"mobile":"","avatar":"http://api.hmylst.cn/static/images/headimg/20190711156280864771502.png","user_id":27728,"comment_id":81,"content":"物流真快，真的很好用！","star_rating":3,"replies":null,"praise":0,"add_time":1563249074,"img":["http://127.0.0.1:8021/public/upload/images/comment/20190716156324907478060.png"],"sku_id":103,"spec":"规格:洗衣片套餐一","is_praise":0}]
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
         * mobile :
         * avatar : http://api.hmylst.cn/static/images/headimg/20190711156280864771502.png
         * user_id : 27728
         * comment_id : 81
         * content : 物流真快，真的很好用！
         * star_rating : 3
         * replies : null
         * praise : 0
         * add_time : 1563249074
         * img : ["http://127.0.0.1:8021/public/upload/images/comment/20190716156324907478060.png"]
         * sku_id : 103
         * spec : 规格:洗衣片套餐一
         * is_praise : 0
         */

        private String mobile;
        private String avatar;
        private int user_id;
        private int comment_id;
        private String content;
        private int star_rating;
        private String replies;
        private int praise;
        private int add_time;
        private int sku_id;
        private String spec;
        private int is_praise;
        private List<String> img;

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile == null ? "" : mobile;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar == null ? "" : avatar;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content == null ? "" : content;
        }

        public int getStar_rating() {
            return star_rating;
        }

        public void setStar_rating(int star_rating) {
            this.star_rating = star_rating;
        }

        public String getReplies() {
            return replies;
        }

        public void setReplies(String replies) {
            this.replies = replies;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getSku_id() {
            return sku_id;
        }

        public void setSku_id(int sku_id) {
            this.sku_id = sku_id;
        }

        public String getSpec() {
            return spec == null ? "" : spec;
        }

        public void setSpec(String spec) {
            this.spec = spec == null ? "" : spec;
        }

        public int getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }

        public List<String> getImg() {
            if (img == null) {
                return new ArrayList<>();
            }
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
