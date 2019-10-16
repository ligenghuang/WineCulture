package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.BindBankCardDto;

public interface BindBankCardView extends BaseView {

    void bindBankCard();

    void bindBankCardSuccess(BindBankCardDto bindBankCardDto);
}
