package com.lgh.huanglib.retrofitlib.http;

import android.util.Log;

import com.google.gson.Gson;
import com.lgh.huanglib.retrofitlib.Api.BaseApi;
import com.lgh.huanglib.retrofitlib.Api.BaseResultEntity;
import com.lgh.huanglib.retrofitlib.RxRetrofitApp;
import com.lgh.huanglib.retrofitlib.exception.RetryWhenNetworkException;
import com.lgh.huanglib.retrofitlib.http.cookie.CookieInterceptor;
import com.lgh.huanglib.retrofitlib.http.cookie.CookieResulte;
import com.lgh.huanglib.retrofitlib.http.cookie.DataInterceptor;
import com.lgh.huanglib.retrofitlib.listener.HttpOnNextListener;
import com.lgh.huanglib.retrofitlib.subscribers.ProgressCatchSubscriber;
import com.lgh.huanglib.retrofitlib.subscribers.ProgressSubscriber;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.MyApplication;
import com.lgh.huanglib.util.data.MySharedPreferencesUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2018/11/7
 *     desc   :  http交互处理类
 *     version: 1.1
 * </pre>
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;

    //构造方法私有
    private HttpManager() {
    }

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    private Retrofit retrofit;
    private BaseApi basePar;

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public Object initRetrofit(BaseApi basePar, Class cls) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);

        if (basePar.isCache()) {

            builder.addInterceptor(new CookieInterceptor(basePar.isCache(), basePar.getUrl()));
        } else {

//            builder.addInterceptor(mOnResponseInterceptor);
            builder.addInterceptor(new DataInterceptor());
        }
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }


        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .build();
        this.retrofit = retrofit;
        this.basePar = basePar;

        return retrofit.create(cls);
    }

    /**
     * 一般访问
     *
     * @param observable
     */
    public void runHttp(Observable observable) {
        if (basePar == null || retrofit == null) {
            return;
        }

        /**
         * 持有不同的状态码， 并且带有缓存效果的 访问
         */
        if (basePar.isCache()) {

            ProgressCatchSubscriber subCatchscriber = new ProgressCatchSubscriber(basePar);


            /*失败后的retry配置*/
            observable = observable.retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                    basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                    /*生命周期管理*/
//                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                    .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))
                    /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread())
                    /*结果判断*/
                    .map(basePar)
                    .onErrorResumeNext(new HttpResponseFunc<BaseResultEntity>());


            /*链接式对象返回*/
            SoftReference<HttpOnNextListener> httpOnNextListener = basePar.getListener();
            if (httpOnNextListener != null && httpOnNextListener.get() != null) {
                httpOnNextListener.get().onNext(observable);
            }

            /*数据回调*/
            observable.subscribe(subCatchscriber);
        } else {
            /**
             * 不持有状态码  不带缓存的访问
             */
            ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
            observable
                    .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))
                    /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread())
//                    .onErrorResumeNext(new HttpResponseFunc<BaseResultEntity>())
                    .subscribe(subscriber);

        }
        /*rx处理*/


    }

    /**
     * 带有不同的状态码返回
     *
     * @param call
     */
    public void runHttp(Call call) {
        if (basePar == null || retrofit == null) {
            return;
        }
        SoftReference<HttpOnNextListener> mSubscriberOnNextListener = basePar.getListener();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                L.e("lgh", "onResponse code " + response.code());
                L.e("lgh", "onResponse message " + response.message());
                L.e("lgh", "onResponse code " + response.isSuccessful());
                /**
                 * 访问成功
                 */
                if (response.isSuccessful()) {
                    /**
                     * 判断是否是规范的实体返回  如果不是则 原数据返回
                     */
                    if (response.body() instanceof BaseResultEntity) {
                        BaseResultEntity baseResultEntity = (BaseResultEntity) response.body();
                        L.e("lgh", "onResponse baseResultEntity  " + response.toString());
                        /**
                         * 判断是否成功访问了 服务器的状态码为 1 的时候 是正常访问
                         */
                        if (baseResultEntity.getMsg() == null && baseResultEntity.getData() == null) {
                            onCallNext(response);
                        } else if (baseResultEntity.getResult() != 1) {
                            if (mSubscriberOnNextListener.get() != null) {
                                ApiException exception = new ApiException(new Throwable(baseResultEntity.getMsg()), baseResultEntity.getResult(), baseResultEntity.getResult());
                                exception.message = baseResultEntity.getMsg();
                                mSubscriberOnNextListener.get().onError(exception, basePar.getMethod());
                            }
                        } else {

                            onCallNext(response);

                        }
                    } else {
                        onCallNext(response);
                    }


                } else {
                    /**
                     * 访问异常
                     */

                    onCallError(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                onCallError(t);
            }
        });


    }

    private void onCallError(Throwable t) {
        SoftReference<HttpOnNextListener> mSubscriberOnNextListener = basePar.getListener();
        ApiException exception = ExceptionEngine.handleException(t);
        L.e("lgh", "onFailure " + t.toString());
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(exception, basePar.getMethod());
        }
    }

    private void onCallNext(retrofit2.Response response) {
        SoftReference<HttpOnNextListener> mSubscriberOnNextListener = basePar.getListener();
        Gson gson = new Gson();
        String obj2 = gson.toJson(response.body());

        L.e("call", "onResponse obj2  " + obj2);
        CookieResulte cookieResulte = new CookieResulte(basePar.getMethod(), obj2, 0, response.code(), response.message());
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(cookieResulte, basePar.getMethod());
        }
    }

    /**
     * 异常拦截处理
     *
     * @param <T>
     */
    private class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {

        @Override
        public Observable<T> call(Throwable throwable) {
            //ExceptionEngine为处理异常的驱动器
            return Observable.error(ExceptionEngine.handleException(throwable));
        }
    }

    /**
     * 请求访问 拦截 这里只做打印
     */
    private Interceptor mOnResponseInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);


            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            long time = System.currentTimeMillis();
            L.e("xx", "Response code  " + response.code());
            L.e("xx", "Response code  " + response.message());

            return response;
        }
    };

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
