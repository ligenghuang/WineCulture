package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.OrderDetailDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.OrderDetailView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

/**
 * @ClassName:
 * @Description: 订单详情
 * @Author: Administrator
 * @Date: 2019/10/15 15:56
 */
public class OrderDetailAction extends BaseAction<OrderDetailView> {
    public OrderDetailAction(RxAppCompatActivity _rxAppCompatActivity, OrderDetailView orderDetailView) {
        super(_rxAppCompatActivity);
        attachView(orderDetailView);
    }

    public void getOrderDetail(String order_id) {
        post(WebUrlUtil.POST_ORDER_DETAIL, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "order_id", order_id), WebUrlUtil.POST_ORDER_DETAIL)));
    }

    public void cancel() {

    }

    public void pay() {

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
            switch (action.getIdentifying()){
                case WebUrlUtil.POST_ORDER_DETAIL:
                    if (aBoolean) {
                        OrderDetailDto orderDetailDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<OrderDetailDto>() {
                        }.getType());
                        if (orderDetailDto.getStatus() == 200) {
                            view.getOrderDetailSuccess(orderDetailDto);
                            return;
                        }
                        view.onError(orderDetailDto.getMsg(), orderDetailDto.getStatus());
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
