package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OpenMemberDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.BookingGoodsView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     我也预约领商品
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/17 14:11
  * @Version:        1.0
 */

public class BookingGoodsAction extends BaseAction<BookingGoodsView> {
    public BookingGoodsAction(RxAppCompatActivity _rxAppCompatActivity,BookingGoodsView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 预约领商品
     * @param contact
     * @param mobile
     * @param address
     */
    public void bookingGoods(String contact,String mobile,String address){
        post(WebUrlUtil.POST_GET_GOODS,false,service -> manager.runHttp(service.PostData(
                CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"contact",contact,
                    "mobile",mobile,"address" ,address),WebUrlUtil.POST_GET_GOODS
        )));
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
                    case WebUrlUtil.POST_GET_GOODS:
                        //todo 预约领商品
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 预约领商品 成功
                                view.bookingGoodsSuccess(generalDto);
                                return;
                            }
                            view.onError(generalDto.getMsg(),generalDto.getStatus());
                            return;
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
