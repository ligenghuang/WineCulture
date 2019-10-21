package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.AddCartDto;
import com.zhifeng.wineculture.modules.CartListDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.CartView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     购物车
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/30 14:55
  * @Version:        1.0
 */

public class CartAction extends BaseAction<CartView> {
    public CartAction(RxAppCompatActivity _rxAppCompatActivity,CartView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取购物车列表
     */
    public void getCartList(){
        post(WebUrlUtil.POST_CART_LIST,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())), WebUrlUtil.POST_CART_LIST)));

    }

    /**
     * 删除购物车商品
     * @param id
     */
    public void delCart(String id){
        post(WebUrlUtil.POST_DELETE_CART_LIST,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"cart_id",id), WebUrlUtil.POST_DELETE_CART_LIST)));

    }


    /***
     * 购物车商品数量修改
     * @param id
     * @param num
     */
    public void editCart(String id,String num){
        post(WebUrlUtil.POST_ADDCART,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"sku_id",id,"cart_number",num,"edit",1), WebUrlUtil.POST_ADDCART)));

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
                    case WebUrlUtil.POST_CART_LIST:
                        //todo 获取购物车列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            CartListDto cartListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<CartListDto>() {
                            }.getType());
                            if (cartListDto.getStatus() == 200){
                                //todo 获取购物车列表成功
                                view.getCartListSuccess(cartListDto);
                                return;
                            }
                            view.onError(cartListDto.getMsg(),cartListDto.getStatus());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_DELETE_CART_LIST:
                        //todo 获取购物车 删除
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 1||generalDto.getStatus() == 200){
                                //todo 获取购物车 删除 成功
                                view.delCartSuccess();
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_ADDCART:
                        //todo 获取购物车 数量修改
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            AddCartDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<AddCartDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 获取购物车 数量修改 成功
                                view.editCartSuccess();
                                return;
                            }
                            view.editCartError(generalDto.getMsg());
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
