package com.zhifeng.wineculture.modules;

public class SharePosterDto {

    /**
     * status : 200
     * msg : success
     * data : {"realname":"Flask","avatar":"http://orepool.zhifengwangluo.com/upload/images/tou/20190903156750168061690.png","mobile":"13625897412","id":27882,"qrcode":"http://laundry.com/public/shareposter/27882-poster.png","reg_url":"http://laundry.com/register?uid=27882"}
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
         * realname : Flask
         * avatar : http://orepool.zhifengwangluo.com/upload/images/tou/20190903156750168061690.png
         * mobile : 13625897412
         * id : 27882
         * qrcode : http://laundry.com/public/shareposter/27882-poster.png
         * reg_url : http://laundry.com/register?uid=27882
         */

        private String realname;
        private String avatar;
        private String mobile;
        private int id;
        private String qrcode;
        private String reg_url;

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

        public String getQrcode() {
            return qrcode == null ? "" : qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode == null ? "" : qrcode;
        }

        public String getReg_url() {
            return reg_url == null ? "" : reg_url;
        }

        public void setReg_url(String reg_url) {
            this.reg_url = reg_url == null ? "" : reg_url;
        }
    }
}
