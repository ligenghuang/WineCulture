package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.RegisterDto;
import com.zhifeng.wineculture.modules.SendVerifyCodeDto;

public interface RegisterView extends BaseView {
    void sendVerifyCode();

    void sendVerifyCodeSuccess(SendVerifyCodeDto sendVerifyCodeDto);

    void sendVerifyCodeFail(String msg,int code);

    void register();

    void registerSuccess(RegisterDto registerDto);
}
