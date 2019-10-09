package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.RegisterDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.RegisterView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

public class RegisterAction extends BaseAction<RegisterView> {
    public RegisterAction(RxAppCompatActivity _rxAppCompatActivity, RegisterView registerView) {
        super(_rxAppCompatActivity);
        attachView(registerView);
    }

    public void register(String phone, String verify_code, String user_password, String confirm_password) {
        post(WebUrlUtil.POST_REGISTER, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("phone", phone, "verify_code", verify_code, "user_password", user_password, "confirm_password", confirm_password), WebUrlUtil.POST_REGISTER)));
    }

    /**
     * sticky:表明优先接收最高级  threadMode = ThreadMode.MAIN：表明在主线程
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
            if (WebUrlUtil.POST_REGISTER.equals(action.getIdentifying())) {
                RegisterDto registerDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<RegisterDto>() {
                }.getType());
                if (registerDto.getStatus() == 200) {
                    view.registerSuccess(registerDto);
                    return;
                }
                view.onError(registerDto.getMsg(), registerDto.getStatus());
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
