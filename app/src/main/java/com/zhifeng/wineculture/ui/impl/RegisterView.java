package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.RegisterDto;

public interface RegisterView extends BaseView {
    void register();

    void registerSuccess(RegisterDto registerDto);
}
