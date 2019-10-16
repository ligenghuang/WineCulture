package com.zhifeng.wineculture.modules;
/**
  *
  * @ClassName:     会员中心
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/12 17:51
  * @Version:        1.0
 */

public class MemberCenterDto {

    /**
     * status : 200
     * msg : success
     * data : {"realname":"默认昵称","mobile":"18665679485","id":28664,"avatar":"http://jiuwenhua.cn/public/upload/images/headpic/20191011157077394663265.png","remainder_money":"0.00","distribut_money":0,"level":1,"levelname":"999会员"}
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
         * realname : 默认昵称
         * mobile : 18665679485
         * id : 28664
         * avatar : http://jiuwenhua.cn/public/upload/images/headpic/20191011157077394663265.png
         * remainder_money : 0.00
         * distribut_money : 0
         * level : 1
         * levelname : 999会员
         */

        private String realname;
        private String mobile;
        private int id;
        private String avatar;
        private String remainder_money;
        private int distribut_money;
        private int level;
        private String levelname;

        public String getRealname() {
            return realname == null ? "" : realname;
        }

        public void setRealname(String realname) {
            this.realname = realname == null ? "" : realname;
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

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar == null ? "" : avatar;
        }

        public String getRemainder_money() {
            return remainder_money == null ? "0" : remainder_money;
        }

        public void setRemainder_money(String remainder_money) {
            this.remainder_money = remainder_money == null ? "0" : remainder_money;
        }

        public int getDistribut_money() {
            return distribut_money;
        }

        public void setDistribut_money(int distribut_money) {
            this.distribut_money = distribut_money;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelname() {
            return levelname == null ? "" : levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname == null ? "" : levelname;
        }
    }
}
