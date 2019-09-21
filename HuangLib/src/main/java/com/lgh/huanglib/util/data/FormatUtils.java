package com.lgh.huanglib.util.data;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2017/11/16 19:15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FormatUtils {

    public static String format(int resId, Object... text) {
        return format(ResUtil.getString(resId), text);
    }

    public static String format(String src, Object... text) {
        return String.format(src, text);
    }

}
