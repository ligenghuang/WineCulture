package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.BalanceDto;

/**
  *
  * @ClassName:     余额
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 11:31
  * @Version:        1.0
 */

public interface BalanceView extends BaseView {

    void getBalanceData();
    void getBalanceDataSuccess(BalanceDto balanceDto);
}
