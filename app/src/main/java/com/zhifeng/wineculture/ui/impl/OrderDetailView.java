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

    /**
     * 取消订单
     */
    void cancelOrderOrConfirmToReceive(int status);

    void cancelOrderOrConfirmToReceiveSuccess(GeneralDto generalDto);

    void cancelOrderOrConfirmToReceiveFail(int code, String msg);

    /**
     * 立即支付
     */
    void pay();

    void paySuccess(GeneralDto generalDto);

    void payFail(int code, String msg);

    /**
     * 退货
     */
    void refund();

    /**
     * 查看物流
     */
    void lookUpLogistics();

    /**
     * 评价
     */
    void comment();
}