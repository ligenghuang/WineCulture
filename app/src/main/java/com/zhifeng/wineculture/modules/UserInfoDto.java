package com.zhifeng.wineculture.modules;

public class UserInfoDto {

    /**
     * status : 200
     * msg : success
     * data : {"realname":"默认昵称","mobile":"18228178860","id":289250,"remainder_money":"996010.00","distribut_money":"0.00","createtime":1568537320,"avatar":"http://laundry_api.zhifengwangluo.com/public/upload/images/tou/20190916156859986466014.png","is_laundry":1,"team_level":3,"is_dividend":1,"ceo_num":0,"level":0,"coe_num_1":0,"coe_num_2":0,"dividend_id":9,"equity_value":"10.00","withdraw":null}
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
         * realname : 默认昵称
         * mobile : 18228178860
         * id : 289250
         * remainder_money : 996010.00
         * distribut_money : 0.00
         * createtime : 1568537320
         * avatar : http://laundry_api.zhifengwangluo.com/public/upload/images/tou/20190916156859986466014.png
         * is_laundry : 1
         * team_level : 3
         * is_dividend : 1
         * ceo_num : 0
         * level : 0
         * coe_num_1 : 0
         * coe_num_2 : 0
         * dividend_id : 9
         * equity_value : 10.00
         * withdraw : null
         */

        private String realname;
        private String mobile;
        private int id;
        private String remainder_money;
        private String distribut_money;
        private int createtime;
        private String avatar;
        private int is_laundry;
        private int team_level;
        private int is_dividend;
        private int ceo_num;
        private int level;
        private int coe_num_1;
        private int coe_num_2;
        private int dividend_id;
        private String equity_value;
        private String withdraw;

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

        public String getRemainder_money() {
            return remainder_money == null ? "" : remainder_money;
        }

        public void setRemainder_money(String remainder_money) {
            this.remainder_money = remainder_money == null ? "" : remainder_money;
        }

        public String getDistribut_money() {
            return distribut_money == null ? "" : distribut_money;
        }

        public void setDistribut_money(String distribut_money) {
            this.distribut_money = distribut_money == null ? "" : distribut_money;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar == null ? "" : avatar;
        }

        public int getIs_laundry() {
            return is_laundry;
        }

        public void setIs_laundry(int is_laundry) {
            this.is_laundry = is_laundry;
        }

        public int getTeam_level() {
            return team_level;
        }

        public void setTeam_level(int team_level) {
            this.team_level = team_level;
        }

        public int getIs_dividend() {
            return is_dividend;
        }

        public void setIs_dividend(int is_dividend) {
            this.is_dividend = is_dividend;
        }

        public int getCeo_num() {
            return ceo_num;
        }

        public void setCeo_num(int ceo_num) {
            this.ceo_num = ceo_num;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getCoe_num_1() {
            return coe_num_1;
        }

        public void setCoe_num_1(int coe_num_1) {
            this.coe_num_1 = coe_num_1;
        }

        public int getCoe_num_2() {
            return coe_num_2;
        }

        public void setCoe_num_2(int coe_num_2) {
            this.coe_num_2 = coe_num_2;
        }

        public int getDividend_id() {
            return dividend_id;
        }

        public void setDividend_id(int dividend_id) {
            this.dividend_id = dividend_id;
        }

        public String getEquity_value() {
            return equity_value == null ? "" : equity_value;
        }

        public void setEquity_value(String equity_value) {
            this.equity_value = equity_value == null ? "" : equity_value;
        }

        public String getWithdraw() {
            return withdraw == null ? "" : withdraw;
        }

        public void setWithdraw(String withdraw) {
            this.withdraw = withdraw == null ? "" : withdraw;
        }
    }
}
