package com.lgh.huanglib.retrofitlib.listener;

import com.lgh.huanglib.retrofitlib.http.ApiException;
import com.lgh.huanglib.retrofitlib.http.cookie.CookieResulte;

import rx.Observable;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public abstract class HttpOnNextListener<T> {
    /**
     * 成功后回调方法
     *
     * @param t
     */

    public abstract void onNext(T t, String mothead);



    public void onNext(CookieResulte cookieResulte, String method) {

    }

    /**
     * 成功后的ober返回，扩展链接式调用
     *
     * @param observable
     */
    public void onNext(Observable observable) {

    }

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     *
     * @param e
     */
    public void onError(ApiException e, String method) {

    }

    public void onError(int code, String error, String method) {

    }

    /**
     * 取消回調
     */
    public void onCancel() {

    }


}
