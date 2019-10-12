package com.zhifeng.wineculture.modules.post;
/**
  *
  * @ClassName:     添加或修改收货地址请求体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/16 9:59
  * @Version:        1.0
 */

public class AddOrEditAddressPost {
    private int address_id;
    private String consignee;
    private String district;
    private String address;
    private String mobile;
    private int is_default;

    @Override
    public String toString() {
        return "AddOrEditAddressPost{" +
                "address_id=" + address_id +
                ", consignee='" + consignee + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", is_default=" + is_default +
                '}';
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getConsignee() {
        return consignee == null ? "" : consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? "" : consignee;
    }

    public String getDistrict() {
        return district == null ? "0" : district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? "" : district;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address == null ? "" : address;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? "" : mobile;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
