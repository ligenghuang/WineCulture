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
     * 登录
     */
    public static final String POST_LOGIN = "user/login";

    /**
     * 注册
     */
    public static final String POST_REGISTER = "user/register";

    /**
     * 首页
     */
    public static final String POST_INDEX_INDEX = "index/index";
}
