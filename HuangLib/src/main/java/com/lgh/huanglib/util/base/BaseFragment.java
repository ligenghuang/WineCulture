package com.lgh.huanglib.util.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hjq.toast.ToastUtils;
import com.lgh.huanglib.R;
import com.lgh.huanglib.util.CustomToast;
import com.lgh.huanglib.util.data.IsFastClick;
import com.lgh.huanglib.util.dialog.QMUITipDialog;


/**
 * 基类
 *
 * @autor omesoft_lgc
 * <p>
 * create at 2017/2/16 15:12
 */
public abstract class BaseFragment extends RootFragment {

    private static final String TAG = "BaseFragment";

    public FragmentManager mFragmentManager;
    public FragmentTransaction mFragmentTransaction;

    public  Context mContext;
    public Activity mActivity;
    protected Handler handler;


    public QMUITipDialog tipDialog = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    protected void initTitleBar() {
    }

    protected void init() {

    }

    protected void initView() {
    }

    protected void loadView() {
    }


    protected void initHandler() {
    }

    protected void sendMsg(int whatId, Object objs) {
        Message msg = new Message();
        msg.what = whatId;
        msg.obj = objs;
        Log.v("sendMsg", "msg.what=" + msg.what);
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    protected void sendMsg(Handler handler, int whatId, Object objs) {
        Message msg = new Message();
        msg.what = whatId;
        msg.obj = objs;
        Log.v("sendMsg", "msg.what=" + msg.what);
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    protected void onActivityResult() {
        // TODO Auto-generated method stub
    }

    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
    }

    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivity(final Context context, final Class<?> classActivity) {
        startActivity(new Intent(context, classActivity));
        getActivity().finish();
    }

    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivityNotFinish(final Context context, final Class<?> classActivity) {
        if (IsFastClick.isFastClick()) {
            startActivity(new Intent(context, classActivity));
        }

    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
    }


    /**
     * 显示加载 zhong  context.getString(R.string.main_process_fail)
     *
     * @param res
     */

    public void loadDialog(String res) {
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(res)
                .create();
        tipDialog.show();
    }

    public void loadDialog() {
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
        tipDialog.show();
    }

    /**
     * 显示加载 成功  context.getString(R.string.main_process_fail)
     *
     * @param res
     */
    public void loadSuccess(String res, Context context) {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
            tipDialog = new QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                    .setTipWord(res)
                    .create();
            tipDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 2000);
        }

    }

    /**
     * 显示加载错误  context.getString(R.string.main_process_fail)
     *
     * @param res
     */
    public void loadError(String res, Context context) {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
            tipDialog = new QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord(res)
                    .create();
            tipDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 2000);
        }

    }

    public void loadDiss() {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
        }
    }

    protected void showToast(String text) {
        CustomToast.showToasts(getContext(), text);
    }

    protected void showToast(int text) {
        ToastUtils.getToast().cancel();
        ToastUtils.show(text);
    }


}

