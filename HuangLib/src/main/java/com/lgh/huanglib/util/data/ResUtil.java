package com.lgh.huanglib.util.data;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.lgh.huanglib.util.config.MyApplication;


/**
 * @autor lgh
 * <p>
 * create at 2017/9/9 12:15
 */
public class ResUtil {

    public static String getString(@StringRes int strId) {
        return MyApplication.getInstance().getString(strId);
    }

    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(MyApplication.getInstance(), colorId);
    }

    public static Drawable getDrawable(@DrawableRes int drawableId) {
        return ContextCompat.getDrawable(MyApplication.getInstance(), drawableId);
    }

    public static int getDimen(@DimenRes int dimenId) {
        return MyApplication.getInstance().getResources().getDimensionPixelSize(dimenId);
    }

    public static String getFormatString(@StringRes int strId, Object... args) {
        String src = MyApplication.getInstance().getString(strId);
        return String.format(src, args);
    }

}
