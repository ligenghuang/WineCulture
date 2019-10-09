package com.zhifeng.wineculture.modules;
/**
 * description:登录
 * autour: huang
 * date: 2019/10/9 21:08
 * update: 2019/10/9
 * version:
 */
public class LoginDto {

    /**
     * status : 200
     * msg : success
     * data : {"id":27882,"mobile":"13625897412","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQyIsImlhdCI6MTU2NzIyMzYxMSwiZXhwIjoxNTY3MjU5NjExLCJ1c2VyX2lkIjoyNzg4Mn0.RDTg80ELel0AzLg4Oz6NY9vfNNC7zt7ZuwNIoSF-BnE"}
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
         * id : 27882
         * mobile : 13625897412
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQyIsImlhdCI6MTU2NzIyMzYxMSwiZXhwIjoxNTY3MjU5NjExLCJ1c2VyX2lkIjoyNzg4Mn0.RDTg80ELel0AzLg4Oz6NY9vfNNC7zt7ZuwNIoSF-BnE
         */

        private int id;
        private String mobile;
        private String token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
