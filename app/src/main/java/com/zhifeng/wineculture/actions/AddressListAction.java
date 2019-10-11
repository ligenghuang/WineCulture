package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.AddressListDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.AddressListView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     收货地址管理
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/28 18:06
  * @Version:        1.0
 */

public class AddressListAction extends BaseAction<AddressListView> {
    public AddressListAction(RxAppCompatActivity _rxAppCompatActivity,AddressListView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取收货地址列表
     */
    public void getAddressList(){
        post(WebUrlUtil.POST_ADDRESS_LIST,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_ADDRESS_LIST)
        ));
    }

    /**
     * 删除地址
     * @param id
     */
    public void deteleAddress(int id){
        post(WebUrlUtil.POST_DEL_ADDRESS,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"id",id),WebUrlUtil.POST_DEL_ADDRESS)
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
                    case WebUrlUtil.POST_ADDRESS_LIST:
//                        //todo 获取地址列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            try{
                                AddressListDto addressListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<AddressListDto>() {
                                }.getType());
                                if (addressListDto.getStatus() == 200){
                                    //todo 获取地址列表成功
                                    view.getAddressListSuccess(addressListDto);
                                    return;
                                }
                                view.onError(addressListDto.getMsg(),action.getErrorType());
                            }catch (JsonSyntaxException e){
                                GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                                }.getType());
                                view.getAddressListNull();
                            }
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_DEL_ADDRESS:
//                        //todo 删除地址
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 删除地址成功
                                view.deteleAddressSuccess(generalDto);
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
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
