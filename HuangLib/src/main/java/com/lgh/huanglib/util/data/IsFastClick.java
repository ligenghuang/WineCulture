package com.lgh.huanglib.util.data;

import com.lgh.huanglib.util.L;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2018/9/19 上午10:37
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class IsFastClick {

    public static long lastClickTime = 0;//上次点击的时间

    private static int spaceTime = 1000;//时间间隔

    public static boolean isFastClick() {

        long currentTime = System.currentTimeMillis();//当前系统时间

        boolean isAllowClick;//是否允许点击
        L.e("xx", "time " + (currentTime - lastClickTime));
        if (currentTime - lastClickTime > spaceTime) {

            isAllowClick = true;

        } else {

            isAllowClick = false;

        }
        L.e("lgh_click", "IsFastClick.lastClickTime = " + lastClickTime);
        L.e("lgh_click", "IsFastClick.currentTime  = " + currentTime);
        lastClickTime = currentTime;
        L.e("lgh_click", "IsFastClick.isFastClick  = " + isAllowClick);
        return isAllowClick;

    }
}
