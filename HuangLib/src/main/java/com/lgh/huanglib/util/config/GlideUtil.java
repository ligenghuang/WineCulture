package com.lgh.huanglib.util.config;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * created by lgh.
 * created date 2017/10/20 上午10:55.
 * desc   图片工具类
 * version 1.0
 */
public class GlideUtil {

    /**
     * 带圆角的图片
     *
     * @param avator
     * @param main_user_iv
     */
    public static void setImageCircle(Context context, String avator, ImageView main_user_iv, int errorPic) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                GlideApp.with(context)
                        .load(avator)
                        .placeholder(errorPic)
                        .dontAnimate()
                        .error(errorPic)
                        .centerCrop()
                        .circleCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    /**
     * 带圆角的图片
     *
     * @param avator
     * @param main_user_iv
     */
    public static void setImageCircle(Context context, String avator, ImageView main_user_iv, int errorPic, int w) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                GlideApp.with(context)
                        .load(avator)
                        .placeholder(errorPic)
                        .error(errorPic)
                        .centerCrop()
                        .dontAnimate()
                        .circleCrop()
                        .override(w)
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    /**
     * 不带圆角图片
     */
    public static void setImage(Context context, String avator, ImageView main_user_iv, int errorPic) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                GlideApp.with(context)
                        .load(avator)
                        .placeholder(errorPic)
                        .error(errorPic)
                        .dontAnimate()
                        .centerCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    /**
     * 圆角图片
     */
    public static void setRoundedImage(Context context, String avator, ImageView main_user_iv, int errorPic) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(10);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
//                        .override(300, 300);

                GlideApp.with(context)
                        .load(avator)
                        .placeholder(errorPic)
                        .error(errorPic)
                        .dontAnimate()
                        .centerCrop()
                        .apply(options)
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    /**
     * 圆角图片
     */
    public static void setRoundedImage(Context context, String avator, ImageView main_user_iv, int errorPic, int roundingRadius) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
//                        .override(300, 300);

                GlideApp.with(context)
                        .load(avator)
                        .placeholder(errorPic)
                        .error(errorPic)
                        .dontAnimate()
                        .centerCrop()
                        .apply(options)
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    /**
     * 不带圆角图片
     */
    public static void setImage(Context context, String avator, ImageView main_user_iv, int errorPic, int w, int h) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                GlideApp.with(context)
                        .load(avator)
                        .placeholder(errorPic)
                        .error(errorPic)
                        .dontAnimate()
                        .override(w, h)
                        .centerCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    public static void setImage(Context context, String avator, ImageView main_user_iv) {
        if (avator.length() > 0) {
            //圆角处理
            GlideApp.with(context)
                    .load(avator)
                    .transition(new DrawableTransitionOptions().crossFade(1000))
                    .centerCrop()
                    .dontAnimate()
                    .into(main_user_iv);
        }
    }

    /**
     * 停止下载
     */
    public static void pauseRequests() {
        GlideApp.with(MyApplication.getInstance()).pauseRequests();
    }

    /**
     * 恢复下载
     */
    public static void resumeRequests() {
        GlideApp.with(MyApplication.getInstance()).resumeRequests();
    }
}
