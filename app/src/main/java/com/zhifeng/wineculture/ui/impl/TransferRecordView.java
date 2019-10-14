package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.TransferRecordDto;

/**
  *
  * @ClassName:     转账记录
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 15:20
  * @Version:        1.0
 */

public interface TransferRecordView extends BaseView {

    void getTransferRecord();
    void loadMoreTransferRecord();
    void getTransferRecordSuccess(TransferRecordDto transferRecordDto);
}
