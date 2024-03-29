package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.CollectionListDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.MyCollectView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     我的收藏
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/12 16:05
  * @Version:        1.0
 */

public class MyCollectAction extends BaseAction<MyCollectView> {
    public MyCollectAction(RxAppCompatActivity _rxAppCompatActivity,MyCollectView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取关注列表
     */
    public void getCollectionList(){
        post(WebUrlUtil.POST_COLLECTION_LIST,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_COLLECTION_LIST)
        ));
    }

    /**
     * 删除关注商品
     * @param goods_id
     */
    public void deleteCollection(String goods_id){
        post(WebUrlUtil.POST_DELETE_COLLECTION,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"goods_id",goods_id),WebUrlUtil.POST_DELETE_COLLECTION)
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
                    case WebUrlUtil.POST_COLLECTION_LIST:
//                        //todo 获取关注列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            CollectionListDto collectionListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<CollectionListDto>() {
                            }.getType());
                            if (collectionListDto.getStatus() == 200){
                                //todo 获取关注列表成功
                                view.getCollectionListSuccess(collectionListDto);
                                return;
                            }
                            view.onError(collectionListDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_DELETE_COLLECTION:
                        //todo 获取取消关注
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 获取取消关注成功
                                view.deleteCollectionSuccess(generalDto.getMsg());
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
