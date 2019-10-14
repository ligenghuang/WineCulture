package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.OrderDetailDto;

public interface OrderDetailView extends BaseView {
    void getOrderDetail();

    void getOrderDetailSuccess(OrderDetailDto orderDetailDto);
}
