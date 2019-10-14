package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.post.ForgetPwdPost;
import com.zhifeng.wineculture.modules.post.SetPayPwdPost;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.ForgetPwdView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @ClassName: 忘记支付密码
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/17 10:56
 * @Version: 1.0
 */

public class ForgetPwdAction extends BaseAction<ForgetPwdView> {
    public ForgetPwdAction(RxAppCompatActivity _rxAppCompatActivity, ForgetPwdView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取验证码
     * @param phone
     */
    public void getCode(String phone){
        post(WebUrlUtil.POST_SEND_VERIFY_CODE,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("phone",phone), WebUrlUtil.POST_SEND_VERIFY_CODE)));
    }

    /**
     * 忘记支付密码
     * @param forgetPwdPost
     */
    public void forgetPwd(ForgetPwdPost forgetPwdPost){
        post(WebUrlUtil.POST_RESET_PAY_PWD,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"type",2,
                        "phone",forgetPwdPost.getPhone(),"verify_code",forgetPwdPost.getVerify_code(),
                        "new_password",forgetPwdPost.getUser_password(),"confirm_password",forgetPwdPost.getConfirm_password()), WebUrlUtil.POST_RESET_PAY_PWD)));
    }

    /**
     * 设置支付密码
     * @param setPayPwdPost
     */
    public void setPayPwd(SetPayPwdPost setPayPwdPost){
        post(WebUrlUtil.POST_SET_PAY_PWD,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),
                        "phone",setPayPwdPost.getPhone(),"verify_code",setPayPwdPost.getVerify_code(),
                        "password",setPayPwdPost.getPassword(),"repaypwd",setPayPwdPost.getPassword()), WebUrlUtil.POST_SET_PAY_PWD)));

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
                    case WebUrlUtil.POST_SEND_VERIFY_CODE:
//                        //todo 获取验证码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 获取验证码成功
                                view.getCodeSuccess(generalDto.getData());
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_RESET_PAY_PWD:
                        //todo 重置支付密码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 1){
                                //todo 重置支付密码成功
                                view.forgetPwdSuccess(generalDto);
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_SET_PAY_PWD:
                        //todo 设置支付密码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 1){
                                //todo 设置支付密码成功
                                view.setPayPwdSuccess(generalDto);
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
