package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.SubmitOrderDto;
import com.zhifeng.wineculture.modules.Temporary;
import com.zhifeng.wineculture.modules.post.SubmitOrderPost;

/**
  *
  * @ClassName:     提交订单
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/21 17:46
  * @Version:        1.0
 */

public interface TemporaryView extends BaseView {
    /**
     * 获取订单信息
     */
    void getTemporary();
    void getTemporarySuccess(Temporary temporary);

    /**
     * 提交订单
     */
    void submitOrder(SubmitOrderPost submitOrderPost);
    void submitOrderSuccess(SubmitOrderDto submitOrderDto);

    /**
     * 支付
     */
    void payOrder(SubmitOrderPost submitOrderPost);
    void payOrderSuccess(PayOrderDto submitOrderDto);
    void payOrderError(String msg);
}
