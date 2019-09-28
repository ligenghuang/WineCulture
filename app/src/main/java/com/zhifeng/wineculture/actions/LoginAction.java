package com.zhifeng.wineculture.actions;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.ui.impl.LoginView;

public class LoginAction extends BaseAction<LoginView> {
    public LoginAction(RxAppCompatActivity _rxAppCompatActivity, LoginView loginView) {
        super(_rxAppCompatActivity);
        attachView(loginView);
    }

    public void toRegister(){
        toRegister();
    }

    public void toUnRegister(){
        toUnRegister();
    }
}
