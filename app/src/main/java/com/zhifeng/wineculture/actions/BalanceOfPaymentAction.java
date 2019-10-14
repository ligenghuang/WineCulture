package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.BalanceOfPaymentDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.BalanceOfPaymentView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

/**
  *
  * @ClassName:     收支明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 9:38
  * @Version:        1.0
 */

public class BalanceOfPaymentAction extends BaseAction<BalanceOfPaymentView> {
    public BalanceOfPaymentAction(RxAppCompatActivity _rxAppCompatActivity,BalanceOfPaymentView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 我的收支
     * @param log_type
     */
    public void getBalanceOfPayment(int log_type) {
        post(WebUrlUtil.POST_USER_REMAINDER_LIST, false, service -> manager.runHttp(service.PostData(
                CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "log_type", log_type==0?1:0), WebUrlUtil.POST_USER_REMAINDER_LIST)));
    }

    /**
     * sticky:表明优先接收最高级  threadMode = ThreadMode.MAIN：表明在主线程
     *
     * @param action
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MessageEvent(final Action action) {
        L.e("xx", "action   接收到数据更新....." + action.getIdentifying() + " action.getErrorType() : " + action.getErrorType());
        final String msg = action.getMsg(action);
        Observable.just(action.getErrorType())
                .all(integer -> (integer == 200)).subscribe(aBoolean -> {
            // 输出返回结果
            L.e("xx", "输出返回结果 " + aBoolean);
            switch (action.getIdentifying()) {
                case WebUrlUtil.POST_USER_REMAINDER_LIST:
                    //todo 获取我的收支
                    if (aBoolean) {
                        L.e("xx", "输出返回结果 " + action.getUserData().toString());
                        BalanceOfPaymentDto balanceOfPaymentDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<BalanceOfPaymentDto>() {
                        }.getType());
                        if (balanceOfPaymentDto.getStatus() == 200) {
                            //todo 获取我的收支 成功
                            view.getBalanceOfPaymentSuccess(balanceOfPaymentDto);
                            return;
                        }
                        view.onError(balanceOfPaymentDto.getMsg(), action.getErrorType());
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
