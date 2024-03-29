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
     * 修改、忘记密码
     */
    public static final String POST_RESET_PASSWORD = "user/resetPassword";

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

    /**
     * 我要推广
     */
    public static final String POST_SHARE_POSTER = "user/sharePoster";

    /**
     * 购物车
     */
    public static final String POST_CART_LIST = "cart/cartlist";

    /**
     * 删除购物车商品
     */
    public static final String POST_DELETE_CART_LIST = "cart/delCart";

    /**
     * 地址管理
     */
    public static final String POST_ADDRESS_LIST = "Address/addressList";

    /**
     * 删除地址
     */
    public static final String POST_DEL_ADDRESS = "Address/delAddress";

    /**
     * 获取收货地址详情
     */
    public static final String POST_ADDRESS_DETAIL = "Address/my_address";

    /**
     * 购物车提交订单接口
     */
    public static final String POST_TEMPORARY = "Order/temporary";

    /**
     * 提交订单
     */
    public static final String POST_SUBMITORDER = "Order/submitOrder";

    /**
     * 订单支付
     */
    public static final String POST_PAY_ORDER = "pay/payment";

    /**
     * 订单列表
     */
    public static final String POST_ORDER_LIST = "order/order_list";

    /**
     * 订单详情
     */
    public static final String POST_ORDER_DETAIL = "Order/order_detail";

    /**
     * 我的团队
     */
    public static final String POST_TEAM_LIST = "user/team_list";

    /**
     * 我的团队-查看
     */
    public static final String POST_FANS_ORDER = "user/fans_order";

    /**
     * 我的评价
     */
    public static final String POST_MY_COMMENT = "user/my_comment";

    /**
     * 商品评价
     */
    public static final String POST_ORDER_COMMENT = "order/order_comment";

    /**
     * 获取下级地址
     */
    public static final String POST_ADDRESS_GET_REGION = "Address/get_region";

    /**
     * 编辑添加地址
     */
    public static final String POST_ADD_ADDRESS = "Address/addAddress";

    /**
     * 关注列表
     */
    public static final String POST_COLLECTION_LIST = "Collection/collection_list";

    /**
     * 会员中心
     */
    public static final String POST_USER_PERSONAL = "user/personal";

    /**
     * 评价列表
     */
    public static final String POST_COMMENT_LIST = "Goods/comment_list";
    /**
     * 我的收支
     */
    public static final String POST_USER_REMAINDER_LIST = "user/remainder_list";

    /**
     * 转账记录
     */
    public static final String POST_USER_TRANSFER_LIST = "user/transfer";

    /**
     * 提现记录
     */
    public static final String POST_WITHDRAWAL_LIST = "user/withdrawal_list";

    /**
     * 钱包接口
     */
    public static final String POST_USER_REMAINDER ="user/user_remainder";

    /**
     * 手机号搜索转账
     */
    public static final String POST_USER_SEARCH_PHONE = "user/search_phone";

    /**
     * 我的足迹
     */
    public static final String POST_COLLECTION_FOOT_LIST = "Collection/foot_list";

    /**
     * 删除足迹
     */
    public static final String POST_COLLECTION_FOOT = "Collection/foot";

    /**
     * 修改手机号码  验证手机号
     */
    public static final String POST_SAFE_CHECK_NEW_PHONE = "user/verifyphone";

    /**
     * 换绑手机
     */
    public static final String POST_SAFE_CHANGE_PHONE = "user/resetphone";

    /**
     * 修改支付密码
     */
    public static final  String POST_EDIT_PAY_PWD = "user/edit_pay_password";

    /**
     * 忘记支付密码
     */
    public static final  String POST_RESET_PAY_PWD = "user/paypwd_reset";

    /**
     * 设置支付密码
     */
    public static final  String POST_SET_PAY_PWD = "user/set_pay_password";

    /**
     * 安全中心信息
     */
    public static final String POST_SAFE_INDEX = "user/safe";

    /**
     * 搜索历史
     */
    public static final String POST_SEARCH_HISTORY = "user/search_history";
    /**
     * 搜索商品
     */
    public static final String POST_SEARCH_GOODS = "goods/search_goods";

    /**
     * 删除搜索历史
     */
    public static final String POST_DEL_SEARCH_HISTORY = "user/del_search_history";

    /**
     * 公告列表
     */
    public static final String POST_USER_ANNOUNCE_LIST = "user/announce_list";

    /**
     * 获取支付宝
     */
    public static final String POST_ZFB_INFO = "user/zfb_info";

    /**
     * 绑定支付宝
     */
    public static final String POST_ZFB_EDIT = "user/zfb_edit";

    /**
     * 获取银行卡
     */
    public static final String POST_GET_BANK_NUMBER = "user/get_bank_number";

    /**
     * 绑定银行卡
     */
    public static final String POST_BOUND_BANK = "user/bound_bank";

    /**
     * 提现
     */
    public static final String POST_WITHDRAWAL = "user/withdrawal";

    /**
     * 修改用户名
     */
    public static final String POST_EDIT_NAME = "user/edit_name";

    /**
     * 修改头像
     */
    public static final String POST_UPDATEAVATAR = "user/updateTou";

    /**
     * 转账
     */
    public static final String POST_TRANSFER = "user/exchange_money_handle";

    /**
     * 查看物流
     */
    public static final String POST_ORDER_EXPRESS = "Order/express";

    /**
     * 开通会员
     */
    public static final String POST_OPEN_VIP = "user/open_vip";

    /**
     * 预约领商品
     */
    public static final String POST_GET_GOODS = "user/get_goods";

    /**
     * 申请退款
     */
    public static final String POST_APPLY_REFUND = "Order/apply_refund";

    /**
     * 取消订单/确认收货
     */
    public static final String POST_EDIT_STATUS = "order/edit_status";

    /**
     * 订单评论列表
     */
    public static final String POST_ORDER_COMMENT_LIST = "Order/order_comment_list";

    /**
     * 关于酒文化
     */
    public static final String POST_ABOUT = "index/about";

    /**
     * 获取支付方式
     */
    public static final String POST_PAY_TYPE = "Order/get_pay_type";

    /**
     * 公告详情
     */
    public static final String POST_ANNOUNCE_DETAIL = "user/announce_edit";
}
