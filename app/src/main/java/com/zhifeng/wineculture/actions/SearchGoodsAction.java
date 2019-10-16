package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.FootPrintDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.SearchGoodsDto;
import com.zhifeng.wineculture.modules.SearchGoodsHistoryDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.SearchGoodsView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     搜索页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/15 16:24
  * @Version:        1.0
 */

public class SearchGoodsAction extends BaseAction<SearchGoodsView> {
    public SearchGoodsAction(RxAppCompatActivity _rxAppCompatActivity,SearchGoodsView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 搜索历史
     */
    public void searchGoodsHistory(){
        post(WebUrlUtil.POST_SEARCH_HISTORY,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"type",2),WebUrlUtil.POST_SEARCH_HISTORY)
        ));
    }

    /**
     * 搜索商品
     */
    public void searchGoods(String keyword,int page,int number_sales,int price,String sort){
        post(WebUrlUtil.POST_SEARCH_GOODS,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"keyword",keyword,"page",page,
                        "number_sales",number_sales,"price",price,"sort",sort
                ),WebUrlUtil.POST_SEARCH_GOODS)
        ));
    }

    /**
     * 删除搜索历史
     */
    public void delHistory(){
        post(WebUrlUtil.POST_DEL_SEARCH_HISTORY,false,service -> manager.runHttp(service.PostData(
                CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext()),"del","all"),WebUrlUtil.POST_DEL_SEARCH_HISTORY
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
                    case WebUrlUtil.POST_SEARCH_HISTORY:
                        //todo 搜索历史
                        if (aBoolean){
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            SearchGoodsHistoryDto searchGoodsHistoryDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<SearchGoodsHistoryDto>() {
                            }.getType());
                            if (searchGoodsHistoryDto.getStatus() == 1) {
                                //todo 搜索历史 成功
                                view.searchGoodsHistorySuccess(searchGoodsHistoryDto);
                                return;
                            }
                            view.onError(searchGoodsHistoryDto.getMsg(), action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_SEARCH_GOODS:
                        //todo 搜索商品
                        if (aBoolean){
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            SearchGoodsDto searchGoodsDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<SearchGoodsDto>() {
                            }.getType());
                            if (searchGoodsDto.getStatus() == 200) {
                                //todo 搜索商品 成功
                                view.searchGoodsSuccess(searchGoodsDto);
                                return;
                            }
                            view.onError(searchGoodsDto.getMsg(), action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_DEL_SEARCH_HISTORY:
                        //todo 删除搜索历史
                        if (aBoolean){
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200) {
                                //todo 删除搜索历史 成功
                                view.deleteHistorySuccess(generalDto);
                                return;
                            }
                            view.onError(generalDto.getMsg(), action.getErrorType());
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
