package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.WithdrawalRecordDto;

/**
  *
  * @ClassName:     提现记录
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 15:34
  * @Version:        1.0
 */

public interface WithdrawalRecordView extends BaseView {
    void getWithdrawalRecord();
    void getWithdrawalRecordSuccess(WithdrawalRecordDto withdrawalRecordDto);
}
