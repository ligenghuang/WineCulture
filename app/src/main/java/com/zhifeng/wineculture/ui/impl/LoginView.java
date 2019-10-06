package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
 * description: 登录
 * autour: huang
 * date: 2019/10/6 21:05
 * update: 2019/10/6
 * version:
 */
public interface LoginView extends BaseView {


    void login(String phone,String pwd);
    void loginSuccess();
}
