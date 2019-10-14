package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.BalanceOfPaymentDto;

/**
  *
  * @ClassName:     收支明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 9:39
  * @Version:        1.0
 */

public interface BalanceOfPaymentView extends BaseView {
    void getBalanceOfPayment();

    void getBalanceOfPaymentSuccess(BalanceOfPaymentDto balanceOfPaymentDto);
}
