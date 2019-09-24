package com.zhifeng.wineculture.actions;

import com.google.gson.Gson;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.actions.ActionCreator;
import com.lgh.huanglib.event.EventBusUtils;
import com.lgh.huanglib.event.StoreEvent;
import com.lgh.huanglib.retrofitlib.Api.BaseResultEntity;
import com.lgh.huanglib.retrofitlib.http.ApiException;
import com.lgh.huanglib.retrofitlib.http.ExceptionEngine;
import com.lgh.huanglib.retrofitlib.http.HttpManager;
import com.lgh.huanglib.retrofitlib.http.cookie.CookieResulte;
import com.lgh.huanglib.retrofitlib.listener.HttpOnNextListener;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.net.api.SubjectPostApi;
import com.zhifeng.wineculture.net.service.HttpPostService;

/**
* 基类
* @author lgh
* created at 2019/5/13 0013 14:31
*/
public class BaseAction<V> extends ActionCreator {
    private static final String TAG = "BaseAction";
    public V view;
    public Object mSubscriber;


    public void attachView(V view) {
        this.view = view;
    }

    public void dettachView() {
        this.view = null;
    }

    RxAppCompatActivity rxAppCompatActivity;
    protected SubjectPostApi postEntity;
    protected HttpManager manager;

    public BaseAction(RxAppCompatActivity _rxAppCompatActivity) {
        rxAppCompatActivity = _rxAppCompatActivity;
    }

    /**
     * <pre>
     *     desc   :  ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ :   desc
     *     desc   :                                                                           :   desc
     *     desc   :                                                                           :   desc
     *     desc   :  访问 方式
     *     desc   :  url 为   WebUrlUtil 的地址 作为标识  判断方法                               :   desc
     *     desc   :  ServiceListener 为访问  访问监听返回                                       :   desc
     *     desc   :   不带缓冲                                                                        :   desc
     *     desc   : ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆  :   desc
     * </pre>
     */
    public void post(String url, ServiceListener serviceListener) {
        /**
         * 初始化 Http 以及API
         */
        postEntity = new SubjectPostApi(simpleOnNextListener, rxAppCompatActivity);
        manager = HttpManager.getInstance();
        postEntity.setMethod(url);
        HttpPostService service = (HttpPostService) manager.initRetrofit(postEntity, HttpPostService.class);
        if (serviceListener != null) {
            serviceListener.callBackService(service);
        }
    }

    /**
     * 设置带缓冲的访问
     *
     * @param url
     * @param isCatch
     * @param serviceListener
     */
    public void post(String url, boolean isCatch, ServiceListener serviceListener) {
        /**
         * 初始化 Http 以及API
         */
        postEntity = new SubjectPostApi(simpleOnNextListener, rxAppCompatActivity);
        manager = HttpManager.getInstance();
        postEntity.setMethod(url);
        postEntity.setCache(isCatch);
        HttpPostService service = (HttpPostService) manager.initRetrofit(postEntity, HttpPostService.class);
        if (serviceListener != null) {
            serviceListener.callBackService(service);
        }
    }


    public interface ServiceListener {
        void callBackService(HttpPostService service);
    }

    /**
     * <pre>
     *     desc   :  ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ :   desc
     *     desc   :                                                                           :   desc
     *     desc   :                                                                           :   desc
     *     desc   :  监听 访问回调
     *     desc   :                                                                           :   desc
     *     desc   :                                                                           :   desc
     *     desc   :                                                                           :   desc
     *     desc   : ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆ ☆  :   desc
     * </pre>
     */
    HttpOnNextListener simpleOnNextListener = new HttpOnNextListener<BaseResultEntity>() {

        @Override
        public void onNext(BaseResultEntity subjects, String method) {
            L.e(TAG, method + "网络返回：\n" + subjects.toString());
            L.e(TAG,"method  = "+method);
            if (subjects.getResult() == 1) {
                Gson gson = new Gson();
                String obj2 = gson.toJson(subjects);
                try {
                    L.json(TAG, "   4 网络返回   ：\n" + obj2);
                    sendEvent(StoreEvent.ACTION_KEY_SUCCESS, 200, method,
                            Action.KEY_OBJ, obj2);
                } catch (Exception e) {
                    ApiException exception = ExceptionEngine.handleException(e);
                    L.e(TAG, "   4 缓存返回 错误信息 ：\n" + exception.toString());
                }
            } else {

                sendEvent(StoreEvent.ACTION_KEY_ERROR, -1, method,
                        Action.KEY_MSG, subjects.getMsg());
            }

        }

        /**
         *   访问数据访问
         * @param cookieResulte
         * @param method
         */
        @Override
        public void onNext(CookieResulte cookieResulte, String method) {
            super.onNext(cookieResulte, method);
            L.e(TAG, "  4 缓存返回： 方法\n" + method);
            L.e(TAG,"cookieResulte.getCode() = "+cookieResulte.getCode());
            try {

                L.json(TAG, "   4 缓存返回   ：\n" + cookieResulte.getResulte());
                sendEvent(StoreEvent.ACTION_KEY_SUCCESS, cookieResulte.getCode(), method,
                        Action.KEY_OBJ, cookieResulte.getResulte());
            } catch (Exception e) {
                ApiException exception = ExceptionEngine.handleException(e);
                L.e(TAG, "   4 缓存返回 错误信息 ：\n" + exception.toString());
            }

        }

        /**
         * 访问异常 方式 1
         * @param code
         * @param error
         * @param method
         */
        @Override
        public void onError(int code, String error, String method) {
            super.onError(code, error, method);

            L.e(TAG, "失败1：\n" + code + " error " + error);
            sendEvent(StoreEvent.ACTION_KEY_ERROR, code, method,
                    Action.KEY_MSG, error);
        }

        /**
         * 访问异常 方式2
         * @param e
         * @param method
         */
        @Override
        public void onError(ApiException e, String method) {
            super.onError(e, method);
            L.e(TAG, "失败2：\n" + e.toString());

            sendEvent(StoreEvent.ACTION_KEY_ERROR, netError, method,
                    Action.KEY_MSG, e.message);
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        @Override
        public void onCancel() {
            super.onCancel();
            L.e(TAG, "取消請求\n");
        }
    };

    /**
     * 用于注册  事件
     *
     * @param subscriber
     */
    public void register(Object subscriber) {

        mSubscriber = subscriber;
        EventBusUtils.register(subscriber);
    }

    /**
     * 用于取消注册 事件
     *
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        mSubscriber = null;
        EventBusUtils.unregister(subscriber);
    }

    public void unregister() {
        EventBusUtils.unregister(mSubscriber);
    }




}
