package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.LoginDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.LoginView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

/**
 * description: 登录
 * autour: huang
 * date: 2019/10/6 21:05
 * update: 2019/10/6
 * version:
 */
public class LoginAction extends BaseAction<LoginView> {
    public LoginAction(RxAppCompatActivity _rxAppCompatActivity, LoginView loginView) {
        super(_rxAppCompatActivity);
        attachView(loginView);
    }

    /**
     * 登录
     * @param phone
     * @param pwd
     */
    public void login(String phone,String pwd){
        post(WebUrlUtil.POST_LOGIN,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("phone",phone,"user_password",pwd), WebUrlUtil.POST_LOGIN)));
    }

    /**
     * sticky:表明优先接收最高级  threadMode = ThreadMode.MAIN：表明在主线程
     *
     * @param action
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MessageEvent(final Action action) {
        L.e("xx", "action   接收到数据更新....." + action.getIdentifying() + " action.getErrorType() : " + action.getErrorType());
        final String msg = action.getMsg(action);
        Observable.just(action.getErrorType())
                .all(integer -> (integer == 200)).subscribe(aBoolean -> {
                    // 输出返回结果
                    L.e("xx", "输出返回结果 " + aBoolean);
            if (WebUrlUtil.POST_LOGIN.equals(action.getIdentifying())) {//todo 登录
                if (aBoolean) {
                    try {
                        LoginDto loginDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<LoginDto>() {
                        }.getType());
                        if (loginDto.getStatus() == 200) {
                            view.loginSuccess(loginDto);
                            return;
                        }
                        view.onError(loginDto.getMsg(), loginDto.getStatus());
                        return;
                    } catch (JsonSyntaxException e) {
                        GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                        }.getType());
                        view.onError(generalDto.getMsg(), generalDto.getStatus());
                        return;
                    }
                }
                view.onError(msg, action.getErrorType());
            }
                });
    }

    public void toRegister() {
        register(this);
    }

    public void toUnregister() {
        unregister(this);
    }
}
