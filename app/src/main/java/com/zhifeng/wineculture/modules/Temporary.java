package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class Temporary {

    /**
     * status : 200
     * msg : 成功
     * data : {"goods_res":[{"cart_id":3701,"selected":0,"session_id":"","user_id":27888,"groupon_id":0,"goods_id":75,"goods_sn":"","goods_name":"企业版 矿机","market_price":"0.00","goods_price":"10000.00","member_goods_price":"10000.00","subtotal_price":"40000.00","sku_id":75,"goods_num":4,"spec_key_name":"规格:西数企业1T","img":"http://zf8020.com/upload/images/goods/20190803156482339365585.png","single_number":0}],"addr_res":{"user_id":27888,"province":2,"city":3,"district":4,"twon":0,"address":"北京市北京市东城区1号","consignee":"好好","mobile":"13769445886","address_id":1186,"is_default":1,"p_cn":"北京市","c_cn":"北京市","d_cn":"东城区","s_cn":null},"pay_type":[{"pay_type":2,"pay_name":"微信支付"},{"pay_type":1,"pay_name":"余额支付"},{"pay_type":3,"pay_name":"支付宝支付"}],"shipping_price":"0.00","coupon":[],"remainder_money":"0.00000000","pwd":0}
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
         * goods_res : [{"cart_id":3701,"selected":0,"session_id":"","user_id":27888,"groupon_id":0,"goods_id":75,"goods_sn":"","goods_name":"企业版 矿机","market_price":"0.00","goods_price":"10000.00","member_goods_price":"10000.00","subtotal_price":"40000.00","sku_id":75,"goods_num":4,"spec_key_name":"规格:西数企业1T","img":"http://zf8020.com/upload/images/goods/20190803156482339365585.png","single_number":0}]
         * addr_res : {"user_id":27888,"province":2,"city":3,"district":4,"twon":0,"address":"北京市北京市东城区1号","consignee":"好好","mobile":"13769445886","address_id":1186,"is_default":1,"p_cn":"北京市","c_cn":"北京市","d_cn":"东城区","s_cn":null}
         * pay_type : [{"pay_type":2,"pay_name":"微信支付"},{"pay_type":1,"pay_name":"余额支付"},{"pay_type":3,"pay_name":"支付宝支付"}]
         * shipping_price : 0.00
         * coupon : []
         * remainder_money : 0.00000000
         * pwd : 0
         */

        private AddrResBean addr_res;
        private String shipping_price;
        private String remainder_money;
        private int pwd;
        private List<GoodsResBean> goods_res;
        private List<PayTypeBean> pay_type;
        private List<?> coupon;

        public AddrResBean getAddr_res() {
            return addr_res;
        }

        public void setAddr_res(AddrResBean addr_res) {
            this.addr_res = addr_res;
        }

        public String getShipping_price() {
            return shipping_price == null ? "" : shipping_price;
        }

        public void setShipping_price(String shipping_price) {
            this.shipping_price = shipping_price == null ? "" : shipping_price;
        }

        public String getRemainder_money() {
            return remainder_money == null ? "" : remainder_money;
        }

        public void setRemainder_money(String remainder_money) {
            this.remainder_money = remainder_money == null ? "" : remainder_money;
        }

        public int getPwd() {
            return pwd;
        }

        public void setPwd(int pwd) {
            this.pwd = pwd;
        }

        public List<GoodsResBean> getGoods_res() {
            if (goods_res == null) {
                return new ArrayList<>();
            }
            return goods_res;
        }

        public void setGoods_res(List<GoodsResBean> goods_res) {
            this.goods_res = goods_res;
        }

        public List<PayTypeBean> getPay_type() {
            if (pay_type == null) {
                return new ArrayList<>();
            }
            return pay_type;
        }

        public void setPay_type(List<PayTypeBean> pay_type) {
            this.pay_type = pay_type;
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

        public static class AddrResBean {
            /**
             * user_id : 27888
             * province : 2
             * city : 3
             * district : 4
             * twon : 0
             * address : 北京市北京市东城区1号
             * consignee : 好好
             * mobile : 13769445886
             * address_id : 1186
             * is_default : 1
             * p_cn : 北京市
             * c_cn : 北京市
             * d_cn : 东城区
             * s_cn : null
             */

            private int user_id;
            private int province;
            private int city;
            private int district;
            private int twon;
            private String address;
            private String consignee;
            private String mobile;
            private int address_id;
            private int is_default;
            private String p_cn;
            private String c_cn;
            private String d_cn;
            private Object s_cn;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getProvince() {
                return province;
            }

            public void setProvince(int province) {
                this.province = province;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public int getDistrict() {
                return district;
            }

            public void setDistrict(int district) {
                this.district = district;
            }

            public int getTwon() {
                return twon;
            }

            public void setTwon(int twon) {
                this.twon = twon;
            }

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address == null ? "" : address;
            }

            public String getConsignee() {
                return consignee == null ? "" : consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee == null ? "" : consignee;
            }

            public String getMobile() {
                return mobile == null ? "" : mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile == null ? "" : mobile;
            }

            public int getAddress_id() {
                return address_id;
            }

            public void setAddress_id(int address_id) {
                this.address_id = address_id;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public String getP_cn() {
                return p_cn == null ? "" : p_cn;
            }

            public void setP_cn(String p_cn) {
                this.p_cn = p_cn == null ? "" : p_cn;
            }

            public String getC_cn() {
                return c_cn == null ? "" : c_cn;
            }

            public void setC_cn(String c_cn) {
                this.c_cn = c_cn == null ? "" : c_cn;
            }

            public String getD_cn() {
                return d_cn == null ? "" : d_cn;
            }

            public void setD_cn(String d_cn) {
                this.d_cn = d_cn == null ? "" : d_cn;
            }

            public Object getS_cn() {
                return s_cn;
            }

            public void setS_cn(Object s_cn) {
                this.s_cn = s_cn;
            }
        }

        public static class GoodsResBean {
            /**
             * cart_id : 3701
             * selected : 0
             * session_id :
             * user_id : 27888
             * groupon_id : 0
             * goods_id : 75
             * goods_sn :
             * goods_name : 企业版 矿机
             * market_price : 0.00
             * goods_price : 10000.00
             * member_goods_price : 10000.00
             * subtotal_price : 40000.00
             * sku_id : 75
             * goods_num : 4
             * spec_key_name : 规格:西数企业1T
             * img : http://zf8020.com/upload/images/goods/20190803156482339365585.png
             * single_number : 0
             */

            private int cart_id;
            private int selected;
            private String session_id;
            private int user_id;
            private int groupon_id;
            private int goods_id;
            private String goods_sn;
            private String goods_name;
            private String market_price;
            private String goods_price;
            private String member_goods_price;
            private String subtotal_price;
            private int sku_id;
            private int goods_num;
            private String spec_key_name;
            private String img;
            private int single_number;

            public int getCart_id() {
                return cart_id;
            }

            public void setCart_id(int cart_id) {
                this.cart_id = cart_id;
            }

            public int getSelected() {
                return selected;
            }

            public void setSelected(int selected) {
                this.selected = selected;
            }

            public String getSession_id() {
                return session_id == null ? "" : session_id;
            }

            public void setSession_id(String session_id) {
                this.session_id = session_id == null ? "" : session_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getGroupon_id() {
                return groupon_id;
            }

            public void setGroupon_id(int groupon_id) {
                this.groupon_id = groupon_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_sn() {
                return goods_sn == null ? "" : goods_sn;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn == null ? "" : goods_sn;
            }

            public String getGoods_name() {
                return goods_name == null ? "" : goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name == null ? "" : goods_name;
            }

            public String getMarket_price() {
                return market_price == null ? "" : market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price == null ? "" : market_price;
            }

            public String getGoods_price() {
                return goods_price == null ? "" : goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price == null ? "" : goods_price;
            }

            public String getMember_goods_price() {
                return member_goods_price == null ? "" : member_goods_price;
            }

            public void setMember_goods_price(String member_goods_price) {
                this.member_goods_price = member_goods_price == null ? "" : member_goods_price;
            }

            public String getSubtotal_price() {
                return subtotal_price == null ? "" : subtotal_price;
            }

            public void setSubtotal_price(String subtotal_price) {
                this.subtotal_price = subtotal_price == null ? "" : subtotal_price;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getSpec_key_name() {
                return spec_key_name == null ? "" : spec_key_name;
            }

            public void setSpec_key_name(String spec_key_name) {
                this.spec_key_name = spec_key_name == null ? "" : spec_key_name;
            }

            public String getImg() {
                return img == null ? "" : img;
            }

            public void setImg(String img) {
                this.img = img == null ? "" : img;
            }

            public int getSingle_number() {
                return single_number;
            }

            public void setSingle_number(int single_number) {
                this.single_number = single_number;
            }
        }

        public static class PayTypeBean {
            /**
             * pay_type : 2
             * pay_name : 微信支付
             */

            private int pay_type;
            private String pay_name;
            private boolean isSelect;

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public String getPay_name() {
                return pay_name == null ? "" : pay_name;
            }

            public void setPay_name(String pay_name) {
                this.pay_name = pay_name == null ? "" : pay_name;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }
        }
    }
}
