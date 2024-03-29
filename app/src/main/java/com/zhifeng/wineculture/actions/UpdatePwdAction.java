package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.SendVerifyCodeDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.UpdatePwdView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

public class UpdatePwdAction extends BaseAction<UpdatePwdView> {
    public UpdatePwdAction(RxAppCompatActivity _rxAppCompatActivity, UpdatePwdView updatePwdView) {
        super(_rxAppCompatActivity);
        attachView(updatePwdView);
    }

    public void getVerifyCode(String phone) {
        post(WebUrlUtil.POST_SEND_VERIFY_CODE, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("phone", phone), WebUrlUtil.POST_SEND_VERIFY_CODE)));
    }

    public void updatePwd(String type, String phone, String verify_code, String user_password, String confirm_password) {
        post(WebUrlUtil.POST_RESET_PASSWORD, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("type", type, "phone", phone, "verify_code", verify_code, "user_password", user_password, "confirm_password", confirm_password), WebUrlUtil.POST_RESET_PASSWORD)));
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
            switch (action.getIdentifying()) {
                case WebUrlUtil.POST_SEND_VERIFY_CODE:
                    if (aBoolean) {
                        SendVerifyCodeDto sendVerifyCodeDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<SendVerifyCodeDto>() {
                        }.getType());
                        if (sendVerifyCodeDto.getStatus() == 200) {
                            view.sendVerifyCodeSuccess(sendVerifyCodeDto);
                            return;
                        }
                        view.sendVerifyCodeFail(sendVerifyCodeDto.getMsg(), sendVerifyCodeDto.getStatus());
                        return;
                    }
                    view.sendVerifyCodeFail(msg, action.getErrorType());
                    break;
                case WebUrlUtil.POST_RESET_PASSWORD:
                    if (aBoolean) {
                        GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                        }.getType());
                        if (generalDto.getStatus() == 200) {
                            view.updatePwdSuccess(generalDto);
                            return;
                        }
                        view.onError(generalDto.getMsg(), generalDto.getStatus());
                        return;
                    }
                    view.onError(msg, action.getErrorType());
                    break;
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
