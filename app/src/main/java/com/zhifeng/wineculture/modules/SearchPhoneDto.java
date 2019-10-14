package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class SearchPhoneDto {

    /**
     * status : 200
     * msg : 获取成功
     * data : [{"id":27783,"realname":"二小姐","avatar":"http://api.puruitingxls.com/upload/images/tou/20190729156438092769803.png"}]
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
         * id : 27783
         * realname : 二小姐
         * avatar : http://api.puruitingxls.com/upload/images/tou/20190729156438092769803.png
         */

        private int id;
        private String realname;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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
    }
}
