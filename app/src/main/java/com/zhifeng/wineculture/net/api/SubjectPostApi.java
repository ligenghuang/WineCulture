package com.zhifeng.wineculture.net.api;

import com.lgh.huanglib.retrofitlib.Api.BaseApi;
import com.lgh.huanglib.retrofitlib.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.net.WebUrlUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
* api 分离器 可以定义多个 只要继承了就好
* @author lgh
* created at 2019/5/13 0013 14:41
*/
public class SubjectPostApi extends BaseApi {
    //    接口需要传入的参数 可自定义不同类型
    private boolean all;
    /*任何你先要传递的参数*/
//    String xxxxx;
//    String xxxxx;
//    String xxxxx;
//    String xxxxx;


    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     *
     * @param listener
     * @param rxAppCompatActivity
     */
    public SubjectPostApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);

        setBaseUrl(WebUrlUtil.BASE_URL);
        setShowProgress(false);
        setCancel(true);
        setCache(false);
//        setMethod("AppFiftyToneGraph/videoLink");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24 * 60 * 60);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return null;
    }


    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

//    @Override
//    public Observable getObservable(Retrofit retrofit) {
//        HttpPostService service = retrofit.create(HttpPostService.class);
//        return service.getAbout();
//    }


    @Override
    public Object call(Object o) {
        return null;
    }
}
