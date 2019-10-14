package com.zhifeng.wineculture.modules.post;
/**
  *
  * @ClassName:     设置支付密码 请求体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/17 10:54
  * @Version:        1.0
 */

public class SetPayPwdPost {

    private String phone;
    private String verify_code;
    private String password;

    public SetPayPwdPost(String phone, String verify_code, String password) {
        this.phone = phone;
        this.verify_code = verify_code;
        this.password = password;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" : phone;
    }

    public String getVerify_code() {
        return verify_code == null ? "" : verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code == null ? "" : verify_code;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }
}
