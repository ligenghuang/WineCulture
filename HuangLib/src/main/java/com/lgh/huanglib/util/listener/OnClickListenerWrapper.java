package com.lgh.huanglib.util.listener;

import android.view.View;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2017/11/22 15:32
 *     desc   :    防止Button的频繁点击,多次执行点击事件
 *     version: 1.0
 * </pre>
 */
public abstract class OnClickListenerWrapper implements View.OnClickListener {

    private static long lastClickTime;

    protected abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        if (isFastDuplicateClick()) {
            return;
        }
        onSingleClick(v);
    }

    /**
     * 是否为重复的快速点击
     *
     * @return
     */
    protected boolean isFastDuplicateClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            lastClickTime = time;
            return true;
        }
        return false;
    }
}
