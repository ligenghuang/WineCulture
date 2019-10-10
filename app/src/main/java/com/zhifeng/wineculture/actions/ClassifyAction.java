package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.ClassifyDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.ClassifyView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     分类fragment
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/28 17:42
  * @Version:        1.0
 */
public class ClassifyAction extends BaseAction<ClassifyView> {
    public ClassifyAction(RxAppCompatActivity _rxAppCompatActivity,ClassifyView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取分类数据
     */
    public void getClassifyData(){
        post(WebUrlUtil.POST_GOODS_CATEGORYLIST,false,service -> manager.runHttp(service.PostData(WebUrlUtil.POST_GOODS_CATEGORYLIST)));
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
                    case WebUrlUtil.POST_GOODS_CATEGORYLIST:
                        if (aBoolean){
                            try{
                                ClassifyDto classifyDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<ClassifyDto>() {
                                }.getType());
                                if (classifyDto.getStatus() == 200) {
                                    view.getClassifyDataSuccess(classifyDto);
                                    return;
                                }
                                view.onError(classifyDto.getMsg(), classifyDto.getStatus());
                            }catch (JsonSyntaxException e){
                                GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                                }.getType());

                                view.onError(generalDto.getMsg(),generalDto.getStatus());
                                return;
                            }
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
