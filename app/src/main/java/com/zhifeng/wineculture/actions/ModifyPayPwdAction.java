package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.BaseDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.ModifyPayPwdView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @ClassName: 修改支付密码
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/17 10:19
 * @Version: 1.0
 */

public class ModifyPayPwdAction extends BaseAction<ModifyPayPwdView> {
    public ModifyPayPwdAction(RxAppCompatActivity _rxAppCompatActivity, ModifyPayPwdView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 修改支付密码
     * @param oldPwd
     * @param newPwd
     */
    public void modifyPayPwd(String oldPwd,String newPwd){
        post(WebUrlUtil.POST_EDIT_PAY_PWD,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"old_password",oldPwd,
                        "new_password",newPwd),WebUrlUtil.POST_EDIT_PAY_PWD)
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
                    case WebUrlUtil.POST_EDIT_PAY_PWD:
                        //todo 修改支付密码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            BaseDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<BaseDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 1){
                                //todo 修改支付密码成功
                                view.modifyPayPwdSuccess(generalDto);
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
