package com.lgh.huanglib.retrofitlib.subscribers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.gson.Gson;
import com.lgh.huanglib.retrofitlib.Api.BaseApi;
import com.lgh.huanglib.retrofitlib.http.ApiException;
import com.lgh.huanglib.retrofitlib.http.ExceptionEngine;
import com.lgh.huanglib.retrofitlib.http.cookie.CookieResulte;
import com.lgh.huanglib.retrofitlib.listener.HttpOnNextListener;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2018/9/20 下午5:06
 *     desc   :   用于在Http请求开始时，自动显示一个ProgressDialog
 *                  在Http请求结束是，关闭ProgressDialog
 *                  调用者自己对请求数据进行处理
 *     version: 1.0
 * </pre>
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    /*是否弹框*/
    private boolean showPorgress = true;
    /* 软引用回调接口*/
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    /*软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;
    /*加载框可自己定义*/
    private ProgressDialog pd;
    /*请求数据*/
    private BaseApi api;


    private static int code;
    private static String message;

    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());

        code=-1;
        message=null;

        setShowPorgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }



        this.setCallListener((_code, _message) -> {

            code = _code;
            message = _message;
            L.e("onCall", "code " + code + " message " + message);
        });
    }


    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCancel();
                        }
                        onCancelProgress();
                    }
                });
            }
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowPorgress()) {
            return;
        }
        Context context = mActivity.get();
        if (pd == null || context == null) {
            return;
        }
        if (!pd.isShowing()) {
            pd.show();
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (!isShowPorgress()) {
            return;
        }
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
        L.e("ProgressCatchSubscriber", "onStart.....");
        /*缓存并且有网*/
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        L.e("ProgressCatchSubscriber", "onCompleted.....");
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        L.e("ProgressCatchSubscriber", "onError....." + e.toString());
        dismissProgressDialog();
        ApiException exception = ExceptionEngine.handleException(e);
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(exception, api.getMethod());
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        L.e("ProgressCatchSubscriber", "onNext....." + t.toString());
        Gson gson = new Gson();
        String obj2 = gson.toJson(t);
        L.e("ProgressCatchSubscriber", "onNext obj2  " + obj2);
        CookieResulte cookieResulte = new CookieResulte(api.getMethod(), obj2, 0, code, message);
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(cookieResulte, api.getMethod());
        }
    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }

    public static CallListener callListener;

    public CallListener getCallListener() {
        return callListener;
    }

    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }


    public interface CallListener {
        void onCall(int code, String message);
    }

}