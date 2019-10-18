package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;

/**
  *
  * @ClassName:     我要预约领商品
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/17 14:07
  * @Version:        1.0
 */

public interface BookingGoodsView extends BaseView {

    void bookingGoods(String contact,String mobile,String address);
    void bookingGoodsSuccess(GeneralDto generalDto);
}
