package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.LogisticsDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.LogisticsView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     查看物流
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/17 11:03
  * @Version:        1.0
 */

public class LogisticsAction extends BaseAction<LogisticsView> {
    public LogisticsAction(RxAppCompatActivity _rxAppCompatActivity,LogisticsView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 查看物流
     * @param orderId
     */
    public void getLogistics(String orderId){
        post(WebUrlUtil.POST_ORDER_EXPRESS,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"order_id",orderId),WebUrlUtil.POST_ORDER_EXPRESS)
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
                    case WebUrlUtil.POST_ORDER_EXPRESS:
                        //todo 查看物流
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                           try{
                               LogisticsDto logisticsDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<LogisticsDto>() {
                               }.getType());
                               if (logisticsDto.getStatus() == 200){
                                   //todo 查看物流 成功
                                   view.getLogostocsSuccess(logisticsDto);
                                   return;
                               }
                               view.onError(logisticsDto.getMsg(),logisticsDto.getStatus());
                               return;
                           }catch (JsonSyntaxException e){
                              view.getLogisticsError("查询物流信息失败");
                           }
                        }
                        view.onError(msg,action.getErrorType());
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
