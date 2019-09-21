package com.lgh.huanglib.event;

import com.lgh.huanglib.actions.Action;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2017/12/22 11:24
 *     desc   :     事件
 *     version: 1.0
 * </pre>
 */
public class StoreEvent {
    public static String getMsg(Action action) {
        Object obj = action.getData().get(Action.KEY_MSG);
        return obj == null ? "" : (String) obj;
    }

    public int code;
    public int resultCode;
    public String msg = "";

    /**
     * 统一错误返回
     */
    public static final String ACTION_KEY_ERROR = "KEY_ERROR";

    public static String ACTION_KEY_SUCCESS = "ACTION_KEY_SUCCESS_BASE";

}
