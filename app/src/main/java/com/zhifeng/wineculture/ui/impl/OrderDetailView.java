package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderDetailDto;

/**
 * @ClassName:
 * @Description: 订单详情
 * @Author: Administrator
 * @Date: 2019/10/15 15:56
 */
public interface OrderDetailView extends BaseView {
    void getOrderDetail();

    void getOrderDetailSuccess(OrderDetailDto orderDetailDto);

    void cancelOrder();

    void cancelOrderSuccess(GeneralDto generalDto);

    void cancelOrderFail(int code, String msg);

    void pay();

    void paySuccess(GeneralDto generalDto);

    void payFail(int code, String msg);
}
