package com.lgh.huanglib.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.hjq.toast.ToastUtils;
import com.lgh.huanglib.R;
import com.lgh.huanglib.util.data.ResUtil;


/**
 * Created by Omesoft on 2016/11/18.
 */

public class CheckNetwork {

    /**
     * 单纯检查网络
     */
    public static boolean checkNetwork(final Context myContext) {
        // ProgressDialogUtil.show(myContext);
        boolean flag = false;
        ConnectivityManager CM = (ConnectivityManager) myContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (CM.getActiveNetworkInfo() != null)
            flag = CM.getActiveNetworkInfo().isAvailable();
        return flag;
    }

    public static boolean checkNetwork2(final Context myContext) {
        boolean flag = false;

        ConnectivityManager CM = (ConnectivityManager) myContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (CM.getActiveNetworkInfo() != null)
            flag = CM.getActiveNetworkInfo().isAvailable();
        if (flag) {

            return flag;
        } else {

            ToastUtils.getToast().cancel();
            ToastUtils.show(ResUtil.getString(R.string.main_net_error));
        }

        return flag;
    }
}
