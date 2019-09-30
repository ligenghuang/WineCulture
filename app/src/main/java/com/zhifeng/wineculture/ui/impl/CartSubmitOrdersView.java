package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
  *
  * @ClassName:     购物车提交订单
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/30 10:19
  * @Version:        1.0
 */

public interface CartSubmitOrdersView extends BaseView {

    /**
     * 获取订单信息
     */
    void getCartOrder();
    void getCartOrderSuccess();

    /**
     * 提交订单
     */
    void submitOrders();
    void submitOrdersSuccess();
}
