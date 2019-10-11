package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

/**
  *
  * @ClassName:     地址列表实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/12 15:42
  * @Version:        1.0
 */

public class AddressListDto {


    /**
     * status : 200
     * msg : success
     * data : [{"address_id":1190,"consignee":"喜欢接电话","mobile":"15236563232","address":"123456","is_default":1,"p_cn":"湖南省","c_cn":"长沙市","d_cn":"市辖区","s_cn":null}]
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
         * address_id : 1190
         * consignee : 喜欢接电话
         * mobile : 15236563232
         * address : 123456
         * is_default : 1
         * p_cn : 湖南省
         * c_cn : 长沙市
         * d_cn : 市辖区
         * s_cn : null
         */

        private int address_id;
        private String consignee;
        private String mobile;
        private String address;
        private int is_default;
        private String p_cn;
        private String c_cn;
        private String d_cn;
        private Object s_cn;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
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

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
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
}
