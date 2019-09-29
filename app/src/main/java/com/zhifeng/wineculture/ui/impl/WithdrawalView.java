package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
  *
  * @ClassName:     提现
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 14:29
  * @Version:        1.0
 */

public interface WithdrawalView extends BaseView {
    /**
     * 获取余额
     */
    void getBalance();
    void getBalanceSuccess();

    /**
     * 提交提现
     */
    void withdrawal();
    void withdrawalSuccess();
}
