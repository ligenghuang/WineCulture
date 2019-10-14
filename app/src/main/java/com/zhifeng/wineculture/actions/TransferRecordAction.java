package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.TransferRecordDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.TransferRecordView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @ClassName: 转账记录
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 15:23
 * @Version: 1.0
 */

public class TransferRecordAction extends BaseAction<TransferRecordView> {
    public TransferRecordAction(RxAppCompatActivity _rxAppCompatActivity, TransferRecordView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取转账记录
     */
    public void getTransferRecord(int page) {
        post(WebUrlUtil.POST_USER_TRANSFER_LIST, false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "page", page), WebUrlUtil.POST_USER_TRANSFER_LIST)
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
                    case WebUrlUtil.POST_USER_TRANSFER_LIST:
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            TransferRecordDto transferRecordDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<TransferRecordDto>() {
                            }.getType());
                            if (transferRecordDto.getStatus() == 200) {
                                //todo 获取转账记录 成功
                                view.getTransferRecordSuccess(transferRecordDto);
                                return;
                            }
                            view.onError(transferRecordDto.getMsg(), action.getErrorType());
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
