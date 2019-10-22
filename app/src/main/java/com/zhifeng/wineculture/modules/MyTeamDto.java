package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class MyTeamDto {

    /**
     * status : 200
     * msg : 获取成功
     * data : {"list":[{"id":27799,"realname":"啊波（秒审）","mobile":"15967684278"},{"id":27800,"realname":"共点","mobile":"13777610748"}],"team_count":2,"putong":1,"vip":0,"vip1":1}
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
         * list : [{"id":27799,"realname":"啊波（秒审）","mobile":"15967684278"},{"id":27800,"realname":"共点","mobile":"13777610748"}]
         * team_count : 2
         * putong : 1
         * vip : 0
         * vip1 : 1
         */

        private int team_count;
        private int putong;
        private int vip;
        private int vip1;
        private List<ListBean> list;

        public int getTeam_count() {
            return team_count;
        }

        public void setTeam_count(int team_count) {
            this.team_count = team_count;
        }

        public int getPutong() {
            return putong;
        }

        public void setPutong(int putong) {
            this.putong = putong;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public int getVip1() {
            return vip1;
        }

        public void setVip1(int vip1) {
            this.vip1 = vip1;
        }

        public List<ListBean> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 27799
             * realname : 啊波（秒审）
             * mobile : 15967684278
             */

            private int id;
            private String realname;
            private String mobile;

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

            public String getMobile() {
                return mobile == null ? "" : mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile == null ? "" : mobile;
            }
        }
    }
}
