package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.AddressListDto;
import com.zhifeng.wineculture.modules.GeneralDto;

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
     * 获取地址列表
     */
    void getAddressList();
    void getAddressListSuccess(AddressListDto addressListDto);
    void getAddressListNull();

    /**
     * 删除地址
     */
    void deteleAddress(int id);
    void deteleAddressSuccess(GeneralDto generalDto);
}
