package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.SendVerifyCodeDto;

public interface UpdatePwdView extends BaseView {
    void getCode();

    void sendVerifyCodeSuccess(SendVerifyCodeDto sendVerifyCodeDto);

    void sendVerifyCodeFail(String msg, int errorType);

    void updatePwd();

    void updatePwdSuccess(GeneralDto generalDto);


}
