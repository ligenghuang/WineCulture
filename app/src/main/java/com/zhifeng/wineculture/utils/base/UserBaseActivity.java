package com.zhifeng.wineculture.utils.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.base.BaseActivity;
import com.lgh.huanglib.util.cusview.Wave;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.actions.BaseAction;

import butterknife.ButterKnife;


/**
 * @author lgh
 * created at 2019/5/13 0013 14:30
 */
public abstract class UserBaseActivity<P extends BaseAction> extends BaseActivity {

    protected P baseAction;

    protected abstract P initAction();


    protected RecyclerView baseRecyclerView;
    protected LinearLayout baseLlNodata;
    protected Wave baseWaveLoading;
    protected ImageView baseIvPlaceholderImage;
    protected TextView baseTvPlaceholderTip;
    protected SmartRefreshLayout baseSmartRefreshLayout;
    View[] viewLis = new View[5];

    protected void binding() {
        setContentView(intiLayout());
        ButterKnife.bind(this);
        baseAction = initAction();
        initTitlebar();
        init();
        initView();
    }



    @Override
    public void finish() {
        super.finish();
        hideInput();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseAction != null) {
            baseAction.unregister();
            baseAction.dettachView();
        }

    }


    /**
     * 如果想使用 加载状态的控件 先初始化这个方法
     */
    protected void initStateView(RecyclerView recyclerView,
                                 LinearLayout llNodata,
                                 Wave waveLoading,
                                 ImageView ivPlaceholderImage,
                                 TextView tvPlaceholderTip) {
        this.baseRecyclerView = recyclerView;
        this.baseLlNodata = llNodata;
        this.baseWaveLoading = waveLoading;
        this.baseIvPlaceholderImage = ivPlaceholderImage;
        this.baseTvPlaceholderTip = tvPlaceholderTip;
        viewLis[0] = baseRecyclerView;
        viewLis[1] = baseLlNodata;
        viewLis[2] = baseWaveLoading;
        viewLis[3] = baseIvPlaceholderImage;
        viewLis[4] = baseTvPlaceholderTip;
    }


    /**
     * 如果想使用 加载状态的控件 先初始化这个方法
     */
    protected void initStateView(SmartRefreshLayout smartRefreshLayout,
                                 LinearLayout llNodata,
                                 Wave waveLoading,
                                 ImageView ivPlaceholderImage,
                                 TextView tvPlaceholderTip) {
        this.baseSmartRefreshLayout = smartRefreshLayout;
        this.baseLlNodata = llNodata;
        this.baseWaveLoading = waveLoading;
        this.baseIvPlaceholderImage = ivPlaceholderImage;
        this.baseTvPlaceholderTip = tvPlaceholderTip;
        viewLis[0] = baseSmartRefreshLayout;
        viewLis[1] = baseLlNodata;
        viewLis[2] = baseWaveLoading;
        viewLis[3] = baseIvPlaceholderImage;
        viewLis[4] = baseTvPlaceholderTip;
    }


}
