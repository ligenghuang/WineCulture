package com.zhifeng.wineculture.utils.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.BaseFragment;
import com.lgh.huanglib.util.cusview.Wave;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.actions.BaseAction;

;

/**
*
* @author lgh
* created at 2019/5/13 0013 14:31
*/
public abstract class UserBaseFragment<P extends BaseAction> extends BaseFragment {

    protected P baseAction;

    protected abstract P initAction();

    protected abstract void initialize();

    protected boolean isLogin = false;


    protected RecyclerView baseRecyclerView;
    protected LinearLayout baseLlNodata;
    protected Wave baseWaveLoading;
    protected ImageView baseIvPlaceholderImage;
    protected TextView baseTvPlaceholderTip;
    protected SmartRefreshLayout baseSmartRefreshLayout;
    View[] viewLis = new View[5];


    protected ImmersionBar mImmersionBar;

    protected void binding() {
        L.e("xx", "调用binding ......");
        mImmersionBar = ImmersionBar.with(this.mActivity);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(true).init();

        baseAction = initAction();
        initialize();
        isLogin = false;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (baseAction != null) {
//
//                baseAction.setNoLoginListener(this);
//            }
//        } else {
//            if (baseAction != null) {
//
//                baseAction.setNoLoginListener(null);
//            }
//        }
        L.e("xx", "基类........." + isVisibleToUser);
    }



}
