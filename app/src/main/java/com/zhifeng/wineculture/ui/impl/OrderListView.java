package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderListDto;

public interface OrderListView extends BaseView {
    void getOrderList();

    void getOrderListSuccess(OrderListDto orderList);

    void cancelOrderOrConfirmToReceiveSuccess(GeneralDto generalDto);
}
