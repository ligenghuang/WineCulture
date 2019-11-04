package com.zhifeng.wineculture.utils;

import java.util.List;

public class Util {
    private static long lastClickTime;

    /**
     * 点击频率控制
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 500) {       //500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
