package com.zhifeng.wineculture.utils.photo;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;

import java.lang.reflect.Field;

/**
*
* @autor feijin_lgc
* create at 2017/9/11 17:59
*/
public class PixelConvertUtil {
    private static DisplayMetrics dm = new DisplayMetrics();
    public final static int Pixe240X320 = 0;
    public final static int Pixe320X480 = 1;
    public final static int Pixe480X800 = 2;
    public final static int Pixe480X854 = 3;

    private static int defaultTextSize;
    /**
     * 用于判断哪一种手机显示类型
     */
    public static int showStyle;
    public static int width, height;

    public static int dip2px(Context context, float dipValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        // Log.v("test","density:"+density);
        return (int) (dipValue * (density));
    }

    public static int px2dip(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        // Log.v("test","density:"+density);
        return (int) (pxValue / ((density)));
    }

    public static int px2sp(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().scaledDensity;
        // Log.v("test","density:"+density);
        return (int) (pxValue / ((density)) + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float density = context.getResources().getDisplayMetrics().scaledDensity;
        // Log.v("test","density:"+density);
        return (int) (spValue * (density) + 0.5f);
    }

    /**
     * 全局的字体大小，以width=480为准
     */
    public static int getConvertSize(Activity activity, int size) {
        if (activity != null) {

            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            display.getMetrics(dm);
            int width, height;
            width = dm.widthPixels;
            height = dm.heightPixels;
            if (width == 480 && height > 800) {
                defaultTextSize = PixelConvertUtil.dip2px(activity, size);
            } else if (width == 480 && height > 480 && height <= 800) {
                defaultTextSize = PixelConvertUtil.dip2px(activity, size);
            } else if (width == 320 && height > 320 && height <= 480) {
                defaultTextSize = PixelConvertUtil.dip2px(activity, size);
            } else if (width == 240 && height <= 320) {
                defaultTextSize = PixelConvertUtil.dip2px(activity, size);
            }
        }
        return defaultTextSize;
    }

    /**
     * 返回哪一种分辨率
     */
    public static int getShowStyle(Activity activity) {
        if (activity != null) {
            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            display.getMetrics(dm);

            width = dm.widthPixels;
            height = dm.heightPixels;
            if (width >= 480 && height > 800) {
                showStyle = Pixe480X854;
            } else if (width > 320 && width <= 480 && height > 480 && height <= 800) {
                showStyle = Pixe480X800;
            } else if (width > 240 && width <= 320 && height > 320 && height <= 480) {
                showStyle = Pixe320X480;
            } else if (width <= 240 && height <= 320) {
                showStyle = Pixe240X320;
            }
        }
        return showStyle;
    }

    /**
     * 屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        Display display = manager.getDefaultDisplay();
        display.getMetrics(dm);

        return dm.widthPixels;
    }

    /**
     * 表情键盘 显示表情的行数
     */
    public static int getIconCloum(Activity activity) {
        int with = PixelConvertUtil.getScreenWidth(activity);
        with = with - PixelConvertUtil.dip2px(activity, 2 * 15);
        int colum = with / PixelConvertUtil.dip2px(activity, 35 + 8);
        return colum;
    }

    /**
     * 屏幕高度
     */
    public static float getScreenHeight(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        Display display = manager.getDefaultDisplay();
        display.getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取状态栏高度
     */
    public static int getBarHeight(Activity activity) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 获取状态栏加标题栏的高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int footBarHeight = 0;
        switch (getShowStyle(activity)) {
            case 0:
                footBarHeight = 21;
                break;
            case 1:
                footBarHeight = 28;
                break;
            case 2:
                footBarHeight = 35;
                break;
            case 3:
                footBarHeight = 40;
                break;
        }
        return footBarHeight;
    }

    /**
     * 设置进度条Y坐标
     */
    public static int getProgressBarLocation(Activity activity) {
        getShowStyle(activity);
        int footBarHeight = height * 2 / 3;

        return footBarHeight;
    }

    /**
     * 设置指定宽度
     */
    public static LayoutParams getLayoutParamsByWidthDip(Context context, int widthDip) {
        return new LayoutParams(PixelConvertUtil.dip2px(context, widthDip),
                LayoutParams.FILL_PARENT);
    }

    /**
     * 设置指定宽度
     */
    public static LayoutParams getLayoutParams_w_f() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.FILL_PARENT);
    }

    /**
     * 设置高度和宽度都扩到最大
     */
    public static LayoutParams getLayoutParams_f_f() {
        return new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
    }

    /**
     * 设置高度和宽度都扩到包裸
     */
    public static LayoutParams getLayoutParams_w_w() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }
}
