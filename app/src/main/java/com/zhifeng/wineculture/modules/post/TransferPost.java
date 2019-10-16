package com.zhifeng.wineculture.modules.post;
/**
  *
  * @ClassName:     转账请求体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/16 16:58
  * @Version:        1.0
 */

public class TransferPost {

    private String end_user_id;
    private String exchange_money;
    private String description;
    private String pwd;

    public String getPwd() {
        return pwd == null ? "" : pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? "" : pwd;
    }

    public String getEnd_user_id() {
        return end_user_id == null ? "" : end_user_id;
    }

    public void setEnd_user_id(String end_user_id) {
        this.end_user_id = end_user_id == null ? "" : end_user_id;
    }

    public String getExchange_money() {
        return exchange_money == null ? "" : exchange_money;
    }

    public void setExchange_money(String exchange_money) {
        this.exchange_money = exchange_money == null ? "" : exchange_money;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }
}
