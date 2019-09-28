package com.lgh.huanglib.util.base;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.lgh.huanglib.R;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.MyApplication;
import com.lgh.huanglib.util.data.IsFastClick;
import com.lgh.huanglib.util.dialog.QMUITipDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author :
 *     e-mail :
 *     time   : 2018/9/19 上午10:47
 *     desc   :
 *     version: 1.1
 * </pre>
 */
public abstract class BaseActivity extends RxAppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "MyActivity";


    /**
     * 需要进行检测的权限数组
     */
    public String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private static final int PERMISSION_REQUEST_CODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    public boolean isNeedCheck = true;


    protected ImmersionBar mImmersionBar;


    private Dialog dialog;

    public QMUITipDialog tipDialog = null;


    public boolean isNeedAnim = true;

    public Context mContext;
    public Activity mActicity;

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().changeLanguage();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }
    }

    /**
     * 检测权限
     */
    public void checkPermissions(String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]), PERMISSION_REQUEST_CODE);
        } else {
//            applyOrShowFloatWindow(context);
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     */
    public List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 检测是否所有的权限都已经授权
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                L.e("xx", "存在未授权的........");
                showMissingPermissionDialog();
                isNeedCheck = false;
            } else {

                L.e("xx", "已经授权完成........");
            }
        }
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ok_notifyTitle);
        builder.setMessage(R.string.ok_notifyMsg);
        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.ok_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityStack.getInstance().removeAll();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });

        builder.setPositiveButton(R.string.ok_setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        isNeedCheck = true;
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


    @Override
    protected void onResume() {
//        IsFastClick.lastClickTime = 0;
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    protected void init() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();


    }

    protected void initView() {

    }

    protected void loadView() {

    }

    protected void initTitlebar() {

    }

    protected void showToast(String text) {
        ToastUtils.getToast().cancel();
        ToastUtils.show(text);
    }

    protected void showToast(int text) {

        ToastUtils.getToast().cancel();
        ToastUtils.show(text);
    }


    protected void showNormalToast(String text) {

        ToastUtils.getToast().cancel();
        ToastUtils.show(text);
    }

    protected void showNormalToast(int text) {

        ToastUtils.getToast().cancel();
        ToastUtils.show(text);
    }


    @Override
    public void finish() {
        super.finish();
        if (isNeedAnim) {
            overridePendingTransition(R.anim.close_enter_anim, R.anim.close_exit_anim);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (!intent.hasExtra("isLead")) {

            overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
        } else {
            boolean isLead = intent.getBooleanExtra("isLead", false);
            if (isLead) {
                overridePendingTransition(R.anim.enteralpha_1, R.anim.exitalpha_1);
            } else {
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
            }
        }
    }

    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivity(final Context context, final Class<?> classActivity) {
        startActivity(new Intent(context, classActivity));
        finish();
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

    /**
     * 带参数跳转
     *
     * @param targetClass
     * @param key
     * @param value
     */
    protected void startActivity(Class<?> targetClass, String key, String value) {
        if (IsFastClick.isFastClick()) {
            Intent intent = new Intent(this, targetClass);
            intent.putExtra(key, value);
            startActivity(intent);
        }
    }

    protected void startActivity(Class<?> targetClass, Map<String, String> map) {
        if (IsFastClick.isFastClick()) {
            Intent intent = new Intent(this, targetClass);
            for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
                // 遍历MAP
                Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                // 第二步：假设方法有参数的话,设置调用方法参数
                intent.putExtra(e.getKey().toString(), e.getValue().toString());
            }
            startActivity(intent);
        }
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        if (IsFastClick.isFastClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }

    }

    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }


    /**
     * 版本号
     *
     * @return
     */
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    public String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo;
        String version;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version = "  " + packInfo.versionName;
            // version = "Version" + packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return " 1.0";
    }

    /**
     * 隱藏 輸入法
     */
    public void hideInput() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


    /**
     * 显示 輸入法
     */
    public void showInput(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示加载 zhong  context.getString(R.string.main_process_fail)
     *
     * @param res
     */

    /**
     * 显示加载 zhong  context.getString(R.string.main_process_fail)
     *
     * @param res
     */

    public void loadDialog(String res) {
        tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(res)
                .create();
        tipDialog.show();
    }

    public void loadDialog() {
        tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
        tipDialog.show();
    }

    public void loadDialog(Context context) {
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
        tipDialog.show();
    }

    public void loadDialog(String res, Context context) {
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(res)
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
                    if (tipDialog != null) {
                        tipDialog.dismiss();
                    }
                }
            }, 2000);
        }

    }

    public void loadSuccess2(String res, Context context) {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
        }
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(res)
                .create();
        tipDialog.show();
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
        }
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(res)
                .create();
        tipDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tipDialog != null) {
                    tipDialog.dismiss();
                }
            }
        }, 2000);
    }

    /**
     * 显示加载错误  context.getString(R.string.main_process_fail)
     *
     * @param res
     */
    public void loadError2(String res, Context context) {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
        }
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(res)
                .create();
        tipDialog.show();
    }

    public void loadDiss() {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
        }
    }


}

