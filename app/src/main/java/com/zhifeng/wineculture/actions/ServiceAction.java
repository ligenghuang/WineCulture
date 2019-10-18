package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.OrderListDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.ServiceView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

public class ServiceAction extends BaseAction<ServiceView> {
    public ServiceAction(RxAppCompatActivity _rxAppCompatActivity, ServiceView serviceView) {
        super(_rxAppCompatActivity);
        attachView(serviceView);
    }

    public void getOrderList(int page, String type) {
        post(WebUrlUtil.POST_ORDER_LIST, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "page", page, "type", type), WebUrlUtil.POST_ORDER_LIST)));
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
            if (WebUrlUtil.POST_ORDER_LIST.equals(action.getIdentifying())) {
                if (aBoolean) {
                    OrderListDto orderListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<OrderListDto>() {
                    }.getType());
                    if (orderListDto.getStatus() == 200) {
                        view.getOrderListSuccess(orderListDto);
                        return;
                    }
                    view.onError(orderListDto.getMsg(), orderListDto.getStatus());
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
