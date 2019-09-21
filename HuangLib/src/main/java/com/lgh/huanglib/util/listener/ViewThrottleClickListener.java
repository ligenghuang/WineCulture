package com.lgh.huanglib.util.listener;

import android.view.View;

import java.util.Calendar;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2018/1/10 17:02
 *     desc   :   防止快速点击的ClickListener
 *     version: 1.0
 * </pre>
 */
public abstract class ViewThrottleClickListener implements View.OnClickListener {
    private static final int THROTTLE_TIME_DEFAULT = 1000; // 1s
    private long mLastClickTime = 0;

    public long getThrottleTime() {
        return THROTTLE_TIME_DEFAULT;
    }

    public void discardClick() {
    }

    public abstract void throttleClick(View view);

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - mLastClickTime > getThrottleTime()) {
            mLastClickTime = currentTime;
            throttleClick(v);
        } else {
            discardClick();
        }
    }
}
