package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
  *
  * @ClassName:     收货地址管理
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/28 18:05
  * @Version:        1.0
 */

public interface AddressListView extends BaseView {

    /**
     * 获取收货地址
     */
    void getAddressList();
    void loadMoreAddressList();
    void getAddressListSuccess();
}
