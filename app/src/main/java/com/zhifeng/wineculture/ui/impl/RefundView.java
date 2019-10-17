package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;

public interface RefundView extends BaseView {
    void refund();

    void refundSuccess(GeneralDto generalDto);
}
