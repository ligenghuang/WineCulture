package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.AliPayAccountDto;
import com.zhifeng.wineculture.modules.BankCardDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.RemainderDto;

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

    void getBalanceSuccess(RemainderDto remainderDto);

    /**
     * 获取支付宝
     */
    void getAliPayAccount();

    void getAliPayAccountSuccess(AliPayAccountDto aliPayAccountDto);

    void getAliPayAccountFail(int code, String msg);

    /**
     * 获取银行卡
     */
    void getBankCard();

    void getBankCardSuccess(BankCardDto bankCardDto);

    void getBankCardFail(int code, String msg);

    /**
     * 提交提现
     */
    void withdrawal();

    void withdrawalSuccess(GeneralDto generalDto);

    void withdrawalFail(int code, String msg);
}
