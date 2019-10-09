package com.zhifeng.wineculture.net;


import com.zhifeng.wineculture.BuildConfig;

public class WebUrlUtil {


    static {
        //配合retrofit，需要以/结尾
        if (BuildConfig.DEBUG) {
            BASE_URL = "http://jiuwenhua.zhifengwangluo.com/api/";
        } else {
            BASE_URL = "http://jiuwenhua.zhifengwangluo.com/api/";
        }
    }

    public static String BASE_URL;

    /**
     * 登录或注册
     */
    public static final String POST_LOGIN = "user/doLogin";


}
