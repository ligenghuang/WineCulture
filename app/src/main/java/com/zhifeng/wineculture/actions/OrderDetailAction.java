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
import com.zhifeng.wineculture.modules.OrderDetailDto;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.PayTypeDto;
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

    /**
     * 获取订单详情
     * @param order_id
     */
    public void getOrderDetail(String order_id) {
        post(WebUrlUtil.POST_ORDER_DETAIL, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "order_id", order_id), WebUrlUtil.POST_ORDER_DETAIL)));
    }

    /**
     * 获取支付方式
     */
    public void getPayType(){
        post(WebUrlUtil.POST_PAY_TYPE,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_PAY_TYPE)
        ));
    }

    /**
     * 修改订单状态
     * @param order_id
     * @param status
     */
    public void cancelOrderOrConfirmToReceive(String order_id, int status) {
        post(WebUrlUtil.POST_EDIT_STATUS, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "order_id", order_id, "status", status), WebUrlUtil.POST_EDIT_STATUS)));
    }

    /**
     * 支付
     * @param order_id
     * @param pay_type
     * @param pwd
     */
    public void pay(String order_id, int pay_type, String pwd) {
        post(WebUrlUtil.POST_PAY_ORDER, false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),
                        "order_id", order_id, "pay_type", pay_type, "pwd", pwd), WebUrlUtil.POST_PAY_ORDER)
        ));
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
                case WebUrlUtil.POST_PAY_TYPE:
                    //todo 获取支付方式
                    if (aBoolean) {
                        L.e("xx", "输出返回结果 " + action.getUserData().toString());
                        PayTypeDto payTypeDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<PayTypeDto>() {
                        }.getType());
                        if (payTypeDto.getStatus() == 200){
                            //todo 获取支付方式成功
                            view.getPayTypeSuccess(payTypeDto);
                            return;
                        }
                        view.onError(payTypeDto.getMsg(),action.getErrorType());
                        return;
                    }
                    view.onError(msg,action.getErrorType());
                    break;
                case WebUrlUtil.POST_PAY_ORDER:
                    //todo 订单支付
                    if (aBoolean) {
                        L.e("xx", "输出返回结果 " + action.getUserData().toString());
                        try{
                            PayOrderDto payOrderDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<PayOrderDto>() {
                            }.getType());
                            if (payOrderDto.getStatus() == 200) {
                                //todo 订单支付成功
                                view.paySuccess(payOrderDto);
                                return;
                            }
                            view.onError(payOrderDto.getMsg(), action.getErrorType());
                            return;
                        }catch (JsonSyntaxException e){
                            GeneralDto generalDto =  new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            view.payFail(generalDto.getMsg());
                            return;
                        }
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
