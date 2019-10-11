package com.zhifeng.wineculture.modules;

public class ShareUrlDto {

    /**
     * status : 200
     * msg : success
     * data : {"realname":"测试账号","avatar":"http://api.myxls.com/uploads/tou/20190709/6f583aad0933dec9c04c78f86e126f17.jpg","mobile":"15181111111","id":76,"url":"?uid=76"}
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
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * realname : 测试账号
         * avatar : http://api.myxls.com/uploads/tou/20190709/6f583aad0933dec9c04c78f86e126f17.jpg
         * mobile : 15181111111
         * id : 76
         * url : ?uid=76
         */

        private String realname;
        private String avatar;
        private String mobile;
        private int id;
        private String url;

        public String getRealname() {
            return realname == null ? "" : realname;
        }

        public void setRealname(String realname) {
            this.realname = realname == null ? "" : realname;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar == null ? "" : avatar;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile == null ? "" : mobile;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url == null ? "" : url;
        }
    }
}
