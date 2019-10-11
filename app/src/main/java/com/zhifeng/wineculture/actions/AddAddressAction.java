package com.zhifeng.wineculture.actions;

import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.AddAddressView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     新增或编辑收货地址
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 9:23
  * @Version:        1.0
 */

public class AddAddressAction extends BaseAction<AddAddressView> {
    public AddAddressAction(RxAppCompatActivity _rxAppCompatActivity,AddAddressView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取收货地址信息
     * @param id
     */
    public void getAddress(int id){
        post(WebUrlUtil.POST_ADDRESS_DETAIL,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"address_id",id),WebUrlUtil.POST_ADDRESS_DETAIL)
        ));
    }

    /**
     * 编辑收货地址
     */
    public void editAddress(){

    }

    /**
     * 新增收货地址
     */
    public void addAddress(){

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
