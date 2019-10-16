package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.BindAliPayDto;

public interface BindAliPayAccountView extends BaseView {
    void bindAliPayAccount();

    void bindAliPayAccountSuccess(BindAliPayDto bindAliPayDto);
}
