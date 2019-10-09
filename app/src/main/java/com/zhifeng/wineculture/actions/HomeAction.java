package com.zhifeng.wineculture.actions;

import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.HomeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @ClassName: 首页fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 17:25
 * @Version: 1.0
 */

public class HomeAction extends BaseAction<HomeView> {
    public HomeAction(RxAppCompatActivity _rxAppCompatActivity, HomeView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取首页数据
     */
    public void getHome(){
        post(WebUrlUtil.POST_INDEX_INDEX,false,service -> manager.runHttp(service.PostData(WebUrlUtil.POST_INDEX_INDEX)));
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
                    case WebUrlUtil.POST_INDEX_INDEX:
                        if (aBoolean){

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
