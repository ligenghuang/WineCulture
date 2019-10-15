package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.AnnounceDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.AnnounceView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     公告详情
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/15 18:00
  * @Version:        1.0
 */

public class AnnounceAction extends BaseAction<AnnounceView> {
    public AnnounceAction(RxAppCompatActivity _rxAppCompatActivity,AnnounceView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取公告列表
     */
    public void getAnnounce(){
        post(WebUrlUtil.POST_USER_ANNOUNCE_LIST,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_USER_ANNOUNCE_LIST)
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
                    case WebUrlUtil.POST_USER_ANNOUNCE_LIST:
                        //todo 获取公告列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            AnnounceDto announceDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<AnnounceDto>() {
                            }.getType());
                            if (announceDto.getStatus() == 200){
                                //todo 获取公告列表
                                view.getAnnounceSuccess(announceDto);
                                return;
                            }
                            view.onError(announceDto.getMsg(),action.getErrorType());
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
