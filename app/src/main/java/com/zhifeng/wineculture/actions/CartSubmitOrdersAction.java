package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.SubmitOrderDto;
import com.zhifeng.wineculture.modules.Temporary;
import com.zhifeng.wineculture.modules.post.SubmitOrderPost;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.CartSubmitOrdersView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
  *
  * @ClassName:     购物车提交订单
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/30 10:20
  * @Version:        1.0
 */

public class CartSubmitOrdersAction extends BaseAction<CartSubmitOrdersView> {
    public CartSubmitOrdersAction(RxAppCompatActivity _rxAppCompatActivity,CartSubmitOrdersView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取订单信息
     */
    public void getCartOrder(String cart_id){
        post(WebUrlUtil.POST_TEMPORARY, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "cart_id", cart_id), WebUrlUtil.POST_TEMPORARY)));

    }

    /**
     * 提交订单
     */
    public void submitOrders(SubmitOrderPost submitOrderPost){
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", MySp.getAccessToken(MyApp.getContext()))
                .addFormDataPart("cart_id",submitOrderPost.getCart_id())
                .addFormDataPart("address_id",submitOrderPost.getAddress_id())
                .addFormDataPart("pay_type",submitOrderPost.getPay_type())
                .addFormDataPart("user_note[]",submitOrderPost.getUser_note().toString());
        RequestBody body = build.build();
        post(WebUrlUtil.POST_SUBMITORDER,false,service -> manager.runHttp(
                service.PostData(body,WebUrlUtil.POST_SUBMITORDER)
        ));
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
     *
     * @param action
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MessageEvent(final Action action) {
        L.e("xx", "action   接收到数据更新....." + action.getIdentifying() + " action.getErrorType() : " + action.getErrorType());

        final String msg = action.getMsg(action);
        Observable.just(action.getErrorType())
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return (integer == 200);
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                // 输出返回结果
                L.e("xx", "输出返回结果 " + aBoolean);

                switch (action.getIdentifying()) {
                    case WebUrlUtil.POST_TEMPORARY:
                        //todo 获取购物车提交订单
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            Temporary temporary = new Gson().fromJson(action.getUserData().toString(), new TypeToken<Temporary>() {
                            }.getType());
                            if (temporary.getStatus() == 200) {
                                //todo 获取购物车提交订单成功
                                view.getCartOrderSuccess(temporary);
                                return;
                            }
                            view.onError(temporary.getMsg(), action.getErrorType());
                            return;
                        }
                        view.onError(msg, action.getErrorType());
                        break;
                    case WebUrlUtil.POST_SUBMITORDER:
                        //todo 提交订单
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            SubmitOrderDto submitOrderDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<SubmitOrderDto>() {
                            }.getType());
                            if (submitOrderDto.getStatus() == 200) {
                                //todo 提交订单成功
                                view.submitOrdersSuccess(submitOrderDto);
                                return;
                            }
                            view.onError(submitOrderDto.getMsg(), action.getErrorType());
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
