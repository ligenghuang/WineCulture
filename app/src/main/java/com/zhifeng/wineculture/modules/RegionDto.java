package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class RegionDto {


    /**
     * status : 200
     * msg : success
     * data : [{"area_id":4,"code":"110101","parent_id":"110100","area_name":"东城区","area_type":3},{"area_id":14,"code":"110102","parent_id":"110100","area_name":"西城区","area_type":3}]
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
         * area_id : 4
         * code : 110101
         * parent_id : 110100
         * area_name : 东城区
         * area_type : 3
         */

        private int area_id;
        private String code;
        private String parent_id;
        private String area_name;
        private int area_type;
        private boolean status;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code == null ? "" : code;
        }

        public String getParent_id() {
            return parent_id == null ? "" : parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id == null ? "" : parent_id;
        }

        public String getArea_name() {
            return area_name == null ? "" : area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name == null ? "" : area_name;
        }

        public int getArea_type() {
            return area_type;
        }

        public void setArea_type(int area_type) {
            this.area_type = area_type;
        }
    }
}
