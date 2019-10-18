package com.zhifeng.wineculture.utils.data;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.lgh.huanglib.util.L;


/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * 屏幕像素转换工具类
 */
public class DisplayUtil {
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    // 将px值转换为dip或dp值，保证尺寸大小不变
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 将dip或dp值转换为px值，保证尺寸大小不变
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getDisplayDensity(Context context) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getDisplayDensityDPI(Context context) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int pxToDp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem().getDisplayMetrics());
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static void printDPI(Context context) {
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                // ...
                L.e("xx", "设备用这个包的东西 drawable-Ldpi...");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                // ...
                L.e("xx", "设备用这个包的东西 drawable-mdpi...");
                break;
            case DisplayMetrics.DENSITY_HIGH:
                // ...
                L.e("xx", "设备用这个包的东西 drawable-hdpi...");
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                // ...
                L.e("xx", "设备用这个包的东西 drawable-xhdpi...");
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                // ...
                L.e("xx", "设备用这个包的东西 drawable-xxhdpi...");
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                // ...
                L.e("xx", "设备用这个包的东西 drawable-xxxhdpi...");
                break;
            default:
                L.e("xx", "设备用这个包的东西 drawable-" + context.getResources().getDisplayMetrics().densityDpi + "...");
                break;
        }
    }


}
