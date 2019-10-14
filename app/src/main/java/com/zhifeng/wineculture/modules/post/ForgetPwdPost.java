package com.zhifeng.wineculture.modules.post;
/**
  *
  * @ClassName:     忘记支付密码请求体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/17 10:52
  * @Version:        1.0
 */

public class ForgetPwdPost {

    private String phone;
    private String verify_code;
    private String user_password;
    private String confirm_password;

    public ForgetPwdPost(String phone, String verify_code, String user_password) {
        this.phone = phone;
        this.verify_code = verify_code;
        this.user_password = user_password;
        this.confirm_password = user_password;
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

    public String getUser_password() {
        return user_password == null ? "" : user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password == null ? "" : user_password;
    }

    public String getConfirm_password() {
        return confirm_password == null ? "" : confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password == null ? "" : confirm_password;
    }
}
