package com.lgh.huanglib.actions;


import android.util.Log;

import com.lgh.huanglib.R;
import com.lgh.huanglib.event.EventBusUtils;
import com.lgh.huanglib.util.data.ResUtil;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2017/12/22 15:30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ActionCreator {
    public final static String NETWORK_TIPS;
    public final static String NETERROR_TIPS;

    static {
        NETWORK_TIPS = ResUtil.getString(R.string.main_service_error);
        NETERROR_TIPS = ResUtil.getString(R.string.main_service_net_error);
    }

    public static int netError = -1;

    private void post(final Object event) {
        try {
            EventBusUtils.post(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEvent(String type, int errorType, Object... data) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("StoreData must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type, errorType);
//        actionBuilder.clazz(actionClazz);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        Log.e("xx", "准备post------->");
        post(actionBuilder.build());
    }

    public void sendEvent(String type, int errorType, String identifying, Object... data) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("StoreData must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type, errorType, identifying);
//        actionBuilder.clazz(actionClazz);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        Log.e("xx", "准备post------->");
        post(actionBuilder.buildWithIdentifying());
    }


    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }


}
