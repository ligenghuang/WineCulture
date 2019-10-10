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
     * 发送验证码
     */
    public static final String POST_SEND_VERIFY_CODE = "user/sendVerifyCode";

    /**
     * 注册
     */
    public static final String POST_REGISTER = "user/register";

    /**
     * 首页
     */
    public static final String POST_INDEX_INDEX = "index/index";

    /**
     * 分类
     */
    public static final String POST_GOODS_CATEGORYLIST = "goods/categorylist";

    /**
     * 商品详情
     */
    public static final String POST_GOODS_DETAIL = "goods/goodsDetail";

    /**
     * 取消关注
     */
    public static final String POST_DELETE_COLLECTION = "Collection/collection";

    /**
     * 加入购物车
     */
    public static final String POST_ADDCART = "Cart/addCart";

    /**
     * 我的
     */
    public static final String POST_USER_INFO = "user/user_info";
}
