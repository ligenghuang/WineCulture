package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.BindBankCardDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.BindBankCardView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

public class BindBankCardAction extends BaseAction<BindBankCardView> {
    public BindBankCardAction(RxAppCompatActivity _rxAppCompatActivity, BindBankCardView bindBankCardView) {
        super(_rxAppCompatActivity);
        attachView(bindBankCardView);
    }

    public void bindBankCard(String bankCard, String bankName) {
        post(WebUrlUtil.POST_BOUND_BANK, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "bankcard", bankCard, "bankname", bankName), WebUrlUtil.POST_BOUND_BANK)));
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
            if (WebUrlUtil.POST_BOUND_BANK.equals(action.getIdentifying())) {
                if (aBoolean) {
                    BindBankCardDto bindBankCardDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<BindBankCardDto>() {
                    }.getType());
                    if (bindBankCardDto.getStatus() == 200) {
                        view.bindBankCardSuccess(bindBankCardDto);
                        return;
                    }
                    view.onError(bindBankCardDto.getMsg(), bindBankCardDto.getStatus());
                    return;
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
