package com.zhifeng.wineculture.modules;

public class RegisterDto {

    /**
     * status : 200
     * msg : success
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQyIsImlhdCI6MTU2NzIyMjAwNywiZXhwIjoxNTY3MjU4MDA3LCJ1c2VyX2lkIjoiMjc4ODIifQ.3rarc3dv8WHd5Pb9QNu6tnyt4zyZooewhqTar3RW_oc","mobile":"13625897412","id":"27882"}
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
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQyIsImlhdCI6MTU2NzIyMjAwNywiZXhwIjoxNTY3MjU4MDA3LCJ1c2VyX2lkIjoiMjc4ODIifQ.3rarc3dv8WHd5Pb9QNu6tnyt4zyZooewhqTar3RW_oc
         * mobile : 13625897412
         * id : 27882
         */

        private String token;
        private String mobile;
        private String id;

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token == null ? "" : token;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile == null ? "" : mobile;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id == null ? "" : id;
        }
    }
}
