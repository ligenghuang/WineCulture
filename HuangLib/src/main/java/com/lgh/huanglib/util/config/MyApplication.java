package com.lgh.huanglib.util.config;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.hjq.toast.ToastUtils;
import com.lgh.huanglib.BuildConfig;
import com.lgh.huanglib.retrofitlib.RxRetrofitApp;
import com.lgh.huanglib.retrofitlib.download.DaoMaster;
import com.lgh.huanglib.retrofitlib.download.DaoSession;
import com.lgh.huanglib.retrofitlib.utils.CookieDbUtil;
import com.lgh.huanglib.util.cusview.ClassicsHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.greendao.database.Database;

import java.util.Locale;


/**
 * @autor lgh
 * <p>
 * create at 2017/9/9 12:15
 */
public class MyApplication extends Application {

    protected static MyApplication mApp;

    private DaoSession daoSession;


    boolean isOpenChangeLanguage = false;

    @SuppressLint("StaticFieldLeak") private static Context context;

    public static Context getContext() {
        return context;
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context);
                return header;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        RxRetrofitApp.init(this, BuildConfig.DEBUG);



        ToastUtils.init(this);
        /**
         * 右滑关闭
         */
//        QMUISwipeBackActivityManager.init(this);

        createDataBase();

    }
    //切换语言
    public void changeLanguage() {
        if (!isOpenChangeLanguage) {
            return;
        }
        SharedPreferences preferences = getSharedPreferences("language", MODE_PRIVATE);
        int mode = preferences.getInt("language", 0);
        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (mode == 1) {
            configuration.locale = Locale.ENGLISH;
        } else if (mode == 0) {
            configuration.locale = Locale.CHINESE;
        }
        resources.updateConfiguration(configuration, metrics);
    }
    /**
     * 初始化 greendao
     */
    private void createDataBase() {
        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(this, CookieDbUtil.dbName, null);
        /**
         * 加密操作
         */
        Database database = dbHelper.getEncryptedWritableDb("garyliang888888");
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


    public static MyApplication getInstance() {
        return mApp;
    }

}
