package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.FootPrintDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.MyFootPrintView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     我的足迹
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 12:01
  * @Version:        1.0
 */

public class MyFootPrintAction extends BaseAction<MyFootPrintView> {
    public MyFootPrintAction(RxAppCompatActivity _rxAppCompatActivity,MyFootPrintView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取我的足迹
     */
    public void getMyFootPrion(){
        post(WebUrlUtil.POST_COLLECTION_FOOT_LIST,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_COLLECTION_FOOT_LIST)));
    }

    /**
     * 删除足迹
     * @param id
     */
    public void deleteFootPrint(String id){
        post(WebUrlUtil.POST_COLLECTION_FOOT,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext()),"goods_id",id),WebUrlUtil.POST_COLLECTION_FOOT)
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
                    case WebUrlUtil.POST_COLLECTION_FOOT_LIST:
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            FootPrintDto footPrintDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<FootPrintDto>() {
                            }.getType());
                            if (footPrintDto.getStatus() == 200) {
                                //todo 获取我的足迹 成功
                                view.getFootPrintSuccess(footPrintDto);
                                return;
                            }
                            view.onError(footPrintDto.getMsg(), action.getErrorType());
                            return;
                        }
                        view.onError(msg, action.getErrorType());
                        break;
                    case WebUrlUtil.POST_COLLECTION_FOOT:
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200) {
                                //todo 删除我的足迹 成功
                                view.deleteFootPrintSuccess(generalDto.getMsg());
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
