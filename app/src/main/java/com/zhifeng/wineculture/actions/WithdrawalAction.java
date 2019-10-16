package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.AliPayAccountDto;
import com.zhifeng.wineculture.modules.BankCardDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.RemainderDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.WithdrawalView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

/**
 * @ClassName: 提现
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 14:30
 * @Version: 1.0
 */

public class WithdrawalAction extends BaseAction<WithdrawalView> {
    public WithdrawalAction(RxAppCompatActivity _rxAppCompatActivity, WithdrawalView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取余额
     */
    public void getBalance() {
        post(WebUrlUtil.POST_USER_REMAINDER, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())), WebUrlUtil.POST_USER_REMAINDER)));
    }

    /**
     * 获取支付宝账户
     */
    public void getAliPayAccount() {
        post(WebUrlUtil.POST_ZFB_INFO, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())), WebUrlUtil.POST_ZFB_INFO)));
    }

    /**
     * 获取绑定的银行卡
     */
    public void getBankCard() {
        post(WebUrlUtil.POST_GET_BANK_NUMBER, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())), WebUrlUtil.POST_GET_BANK_NUMBER)));
    }

    /**
     * 提交提现
     */
    public void withdrawal(float money, int withdraw_type) {
        post(WebUrlUtil.POST_WITHDRAWAL, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "money", money, "withdraw_type", withdraw_type), WebUrlUtil.POST_WITHDRAWAL)));
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
            switch (action.getIdentifying()) {
                case WebUrlUtil.POST_USER_REMAINDER:
                    if (aBoolean) {
                        RemainderDto remainderDto = new Gson().fromJson(action.getUserData().toString(), RemainderDto.class);
                        if (remainderDto.getStatus() == 200) {
                            view.getBalanceSuccess(remainderDto);
                            return;
                        }
                        view.onError(remainderDto.getMsg(), remainderDto.getStatus());
                        return;
                    }
                    view.onError(msg, action.getErrorType());
                    break;
                case WebUrlUtil.POST_ZFB_INFO:
                    if (aBoolean) {
                        AliPayAccountDto aliPayAccountDto = new Gson().fromJson(action.getUserData().toString(), AliPayAccountDto.class);
                        if (aliPayAccountDto.getStatus() == 200) {
                            view.getAliPayAccountSuccess(aliPayAccountDto);
                            return;
                        }
                        view.getAliPayAccountFail(aliPayAccountDto.getStatus(), aliPayAccountDto.getMsg());
                        return;
                    }
                    view.getAliPayAccountFail(action.getErrorType(), msg);
                    break;
                case WebUrlUtil.POST_GET_BANK_NUMBER:
                    if (aBoolean) {
                        BankCardDto bankCardDto = new Gson().fromJson(action.getUserData().toString(), BankCardDto.class);
                        if (bankCardDto.getStatus() == 200) {
                            view.getBankCardSuccess(bankCardDto);
                            return;
                        }
                        view.getBankCardFail(bankCardDto.getStatus(), bankCardDto.getMsg());
                        return;
                    }
                    view.getBankCardFail(action.getErrorType(), msg);
                    break;
                case WebUrlUtil.POST_WITHDRAWAL:
                    if (aBoolean) {
                        GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), GeneralDto.class);
                        if (generalDto.getStatus() == 200) {
                            view.withdrawalSuccess(generalDto);
                            return;
                        }
                        view.withdrawalFail(generalDto.getStatus(), generalDto.getMsg());
                        return;
                    }
                    view.withdrawalFail(action.getErrorType(), msg);
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
