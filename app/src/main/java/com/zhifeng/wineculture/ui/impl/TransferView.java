package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.BalanceDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.post.TransferPost;

/**
  * 
  * @ClassName:     
  * @Description:    
  * @Author:         lgh
  * @CreateDate:     2019/10/16 14:58
  * @Version:        1.0
 */

public interface TransferView extends BaseView {

    /**
     * 获取余额
     */
    void getBalanceData();
    void getBalanceDataSuccess(BalanceDto balanceDto);

    /**
     * 转账
     */
    void transfer(TransferPost transferPost);
    void transferSuccess(GeneralDto generalDto);
}
