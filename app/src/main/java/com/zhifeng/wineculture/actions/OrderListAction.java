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
import com.zhifeng.wineculture.modules.OrderListDto;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.post.SubmitOrderPost;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.OrderListView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

public class OrderListAction extends BaseAction<OrderListView> {
    public OrderListAction(RxAppCompatActivity _rxAppCompatActivity, OrderListView orderListView) {
        super(_rxAppCompatActivity);
        attachView(orderListView);
    }

    public void getOrderList(int page, String type) {
        post(WebUrlUtil.POST_ORDER_LIST, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "page", page, "type", type), WebUrlUtil.POST_ORDER_LIST)));
    }

    public void cancelOrderOrConfirmToReceive(int order_id, int status) {
        post(WebUrlUtil.POST_EDIT_STATUS, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "order_id", order_id, "status", status), WebUrlUtil.POST_EDIT_STATUS)));
    }

    /**
     * 支付
     * @param submitOrderPost
     */
    public void payOrder(SubmitOrderPost submitOrderPost){
        post(WebUrlUtil.POST_PAY_ORDER,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext()),
                        "order_id",submitOrderPost.getCart_id(),"pay_type",submitOrderPost.getPay_type()
                        ,"pwd",submitOrderPost.getPwd()),WebUrlUtil.POST_PAY_ORDER)
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
            switch(action.getIdentifying()){
                case WebUrlUtil.POST_ORDER_LIST:
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
                    break;
                case WebUrlUtil.POST_EDIT_STATUS:
                    if (aBoolean) {
                        GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                        }.getType());
                        if (generalDto.getStatus() == 200) {
                            view.cancelOrderOrConfirmToReceiveSuccess(generalDto);
                            return;
                        }
                        view.onError(generalDto.getMsg(), generalDto.getStatus());
                        return;
                    }
                    view.onError(msg, action.getErrorType());
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
                                view.payOrderSuccess(payOrderDto);
                                return;
                            }
                            view.onError(payOrderDto.getMsg(), action.getErrorType());
                            return;
                        }catch (JsonSyntaxException e){
                            GeneralDto generalDto =  new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            view.payOrderError(generalDto.getMsg());
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
