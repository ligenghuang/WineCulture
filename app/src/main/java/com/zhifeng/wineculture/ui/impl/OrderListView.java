package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderListDto;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.post.SubmitOrderPost;

public interface OrderListView extends BaseView {
    void getOrderList();

    void getOrderListSuccess(OrderListDto orderList);

    /**
     * 支付
     */
    void payOrder(SubmitOrderPost submitOrderPost);
    void payOrderSuccess(PayOrderDto submitOrderDto);
    void payOrderError(String msg);

    void cancelOrderOrConfirmToReceiveSuccess(GeneralDto generalDto);
}
