package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.UserInfoDto;

public interface MyView extends BaseView {
    void getUserInfo();

    void getUserInfoSuccess(UserInfoDto userInfoDto);

    void noLogin();
}
