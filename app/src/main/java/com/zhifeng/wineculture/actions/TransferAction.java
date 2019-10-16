package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.BalanceDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.post.TransferPost;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.TransferView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     转账
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/16 15:01
  * @Version:        1.0
 */

public class TransferAction extends BaseAction<TransferView> {
    public TransferAction(RxAppCompatActivity _rxAppCompatActivity,TransferView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }
    /**
     * 余额
     */
    public void getBalanceData(){
        post(WebUrlUtil.POST_USER_REMAINDER,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_USER_REMAINDER)
        ));
    }

    /**
     * 转账
     * @param transferPost
     */
    public void transfer(TransferPost transferPost){
        post(WebUrlUtil.POST_TRANSFER,false,service -> manager.runHttp(service.PostData(
                CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext()),"end_user_id",transferPost.getEnd_user_id(),
                        "exchange_money",transferPost.getExchange_money(),"description",transferPost.getDescription(),"paypwd",transferPost.getPwd()),WebUrlUtil.POST_TRANSFER
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
                    case WebUrlUtil.POST_USER_REMAINDER:
                        //todo 获取我的余额
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            BalanceDto balanceDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<BalanceDto>() {
                            }.getType());
                            if (balanceDto.getStatus() == 200) {
                                //todo 获取我的余额 成功
                                view.getBalanceDataSuccess(balanceDto);
                                return;
                            }
                            view.onError(balanceDto.getMsg(), action.getErrorType());
                            return;
                        }
                        view.onError(msg, action.getErrorType());
                        break;
                    case WebUrlUtil.POST_TRANSFER:
                        //todo 转账
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 1) {
                                //todo 转账 成功
                                view.transferSuccess(generalDto);
                                return;
                            }
                            view.onError(generalDto.getMsg(), action.getErrorType());
                            return;
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
