package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.OrderListDto;

public interface ServiceView extends BaseView {
    void getOrderList();
    void loadMoreOrderList();

    void getOrderListSuccess(OrderListDto orderList);
}
