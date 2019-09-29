package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
  *
  * @ClassName:     新增或编辑收货地址
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 9:20
  * @Version:        1.0
 */

public interface AddAddressView extends BaseView {

    /**
     * 获取收货地址信息
     */
    void getAddress();
    void getAddressSuccess();

    /**
     * 编辑收货地址
     */
    void editAddress();
    void editAddressSuccess();

    /**
     * 新增收货地址
     */
    void addAddress();
    void addAddressSuccess();
}
