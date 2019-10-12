package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.AddressDetailDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.RegionDto;
import com.zhifeng.wineculture.modules.post.AddOrEditAddressPost;

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
     * 获取收货地址详情
     * @param id
     */
    void getAddress(int id);
    void getAddressSuccess(AddressDetailDto addressDetailDto);

    /**
     * 编辑添加地址
     * @param post
     */
    void addOrEditAddress(AddOrEditAddressPost post);
    void addOrEditAddressSuccess(GeneralDto generalDto);

    void getRegion(String code);
    void getRegionSuccess(RegionDto regionDto);
    void getRegionError();
}
