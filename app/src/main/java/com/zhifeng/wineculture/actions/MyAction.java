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
import com.zhifeng.wineculture.modules.UserInfoDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.MyView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MyAction extends BaseAction<MyView> {
    public MyAction(RxAppCompatActivity _rxAppCompatActivity, MyView myView) {
        super(_rxAppCompatActivity);
        attachView(myView);
    }

    public void getUserInfo() {
        post(WebUrlUtil.POST_USER_INFO, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())), WebUrlUtil.POST_USER_INFO)));
    }

    /**
     * 修改头像
     * @param path
     */
    public void updataAvatar(String path){
        post(WebUrlUtil.POST_UPDATEAVATAR,false,service -> manager.runHttp(service.PostData(
                CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext()),"image",path),WebUrlUtil.POST_UPDATEAVATAR
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
                .all(integer -> (integer == 200)).subscribe(aBoolean -> {
            // 输出返回结果
            L.e("xx", "输出返回结果 " + aBoolean);
            if (WebUrlUtil.POST_USER_INFO.equals(action.getIdentifying())) {
                if (aBoolean) {
                    try {
                        UserInfoDto userInfoDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<UserInfoDto>() {
                        }.getType());
                        if (userInfoDto.getStatus() == 200) {
                            view.getUserInfoSuccess(userInfoDto);
                            return;
                        }
                        view.onError(userInfoDto.getMsg(), userInfoDto.getStatus());
                        return;
                    } catch (JsonSyntaxException e) {
                        view.noLogin();
                        return;
                    }
                }
                view.onError(msg, action.getErrorType());
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
                    case WebUrlUtil.POST_USER_INFO:
                        //todo 获取用户信息
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            try {
                                UserInfoDto userInfoDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<UserInfoDto>() {
                                }.getType());
                                if (userInfoDto.getStatus() == 200){
                                    //todo 获取用户信息成功
                                    view.getUserInfoSuccess(userInfoDto);
                                    return;
                                }
                                view.onError(userInfoDto.getMsg(),action.getErrorType());
                            }catch (JsonSyntaxException e){
                                view.noLogin();
                            }

                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_UPDATEAVATAR:
                        //todo 修改头像
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new  Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 修改头像 成功
                                view.updataAvatarSuccess(generalDto.getData());
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
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
