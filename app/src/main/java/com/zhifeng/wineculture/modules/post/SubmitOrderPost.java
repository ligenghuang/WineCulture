package com.zhifeng.wineculture.modules.post;

import java.util.ArrayList;
import java.util.List;

/**
  *
  * @ClassName:     提交订单实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/21 17:42
  * @Version:        1.0
 */

public class SubmitOrderPost {

    private String cart_id;
    private String address_id;
    private String pay_type;
    private List<String> user_note;
    private String pwd;

    public String getCart_id() {
        return cart_id == null ? "" : cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id == null ? "" : cart_id;
    }

    public String getAddress_id() {
        return address_id == null ? "" : address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id == null ? "" : address_id;
    }

    public String getPay_type() {
        return pay_type == null ? "" : pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type == null ? "" : pay_type;
    }

    public List<String> getUser_note() {
        if (user_note == null) {
            return new ArrayList<>();
        }
        return user_note;
    }

    public void setUser_note(List<String> user_note) {
        this.user_note = user_note;
    }

    public String getPwd() {
        return pwd == null ? "" : pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? "" : pwd;
    }
}
