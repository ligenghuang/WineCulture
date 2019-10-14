package com.zhifeng.wineculture.modules;
/**
  *
  * @ClassName:     安全中心返回实体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/16 16:39
  * @Version:        1.0
 */

public class SafeInfoDto {

    /**
     * status : 200
     * msg : success
     * data : {"realname":"默认昵称","mobile":"13202029884"}
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
         * mobile : 13202029884
         */

        private String realname;
        private String mobile;
        private int pwd;

        public int getPwd() {
            return pwd;
        }

        public void setPwd(int pwd) {
            this.pwd = pwd;
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
