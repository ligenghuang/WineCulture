package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.FansOrderDto;

public interface FansOrderView extends BaseView {
    void getFansOrder();

    void getFansOrderSuccess(FansOrderDto fansOrderDto);
}
