package com.lgh.huanglib.retrofitlib.utils;

import android.content.Context;

import com.lgh.huanglib.retrofitlib.RxRetrofitApp;
import com.lgh.huanglib.retrofitlib.http.cookie.CookieResulte;
import com.lgh.huanglib.retrofitlib.http.cookie.CookieResulteDao;
import com.lgh.huanglib.util.config.MyApplication;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * 数据缓存
 * 数据库工具类-geendao运用
 * Created by WZG on 2016/10/25.
 */

public class CookieDbUtil {
    private static CookieDbUtil db;
    public final static String dbName = "tests_db";
    private Context context;


    public CookieDbUtil() {
        context= RxRetrofitApp.getApplication();
    }


    /**
     * 获取单例
     * @return
     */
    public synchronized static CookieDbUtil getInstance() {
        if (db == null) {
            synchronized (CookieDbUtil.class) {
                if (db == null) {
                    db = new CookieDbUtil();
                }
            }
        }
        return db;
    }




    public void saveCookie(CookieResulte info){
        CookieResulteDao downInfoDao = MyApplication.getInstance().getDaoSession().getCookieResulteDao();
        downInfoDao.insert(info);
    }

    public void updateCookie(CookieResulte info){
        CookieResulteDao downInfoDao = MyApplication.getInstance().getDaoSession().getCookieResulteDao();
        downInfoDao.update(info);
    }

    public void deleteCookie(CookieResulte info){
        CookieResulteDao downInfoDao = MyApplication.getInstance().getDaoSession().getCookieResulteDao();
        downInfoDao.delete(info);
    }


    public CookieResulte queryCookieBy(String  url) {
        CookieResulteDao downInfoDao = MyApplication.getInstance().getDaoSession().getCookieResulteDao();
        QueryBuilder<CookieResulte> qb = downInfoDao.queryBuilder();
        qb.where(CookieResulteDao.Properties.Url.eq(url));
        List<CookieResulte> list = qb.list();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    public List<CookieResulte> queryCookieAll() {
        CookieResulteDao downInfoDao = MyApplication.getInstance().getDaoSession().getCookieResulteDao();
        QueryBuilder<CookieResulte> qb = downInfoDao.queryBuilder();
        return qb.list();
    }
}
