package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class MyTeamDto {

    /**
     * status : 200
     * msg : success
     * data : {"list":[{"id":27864,"realname":"默认昵称","mobile":"17875592622"},{"id":27868,"realname":"默认昵称","mobile":"13286971460"},{"id":27869,"realname":"默认昵称","mobile":"13719364739"}],"team_count":0}
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
         * list : [{"id":27864,"realname":"默认昵称","mobile":"17875592622"},{"id":27868,"realname":"默认昵称","mobile":"13286971460"},{"id":27869,"realname":"默认昵称","mobile":"13719364739"}]
         * team_count : 0
         */

        private int team_count;
        private List<ListBean> list;

        public int getTeam_count() {
            return team_count;
        }

        public void setTeam_count(int team_count) {
            this.team_count = team_count;
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
             * id : 27864
             * realname : 默认昵称
             * mobile : 17875592622
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
