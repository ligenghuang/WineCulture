package com.zhifeng.wineculture.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.HomeAction;
import com.zhifeng.wineculture.adapters.BannerHome;
import com.zhifeng.wineculture.modules.HomeDto;
import com.zhifeng.wineculture.ui.impl.HomeView;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.view.TextBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @ClassName: 首页fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 14:39
 * @Version: 1.0
 */

public class HomeFragment extends UserBaseFragment<HomeAction> implements HomeView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.banner_home)
    BGABanner bannerHome;
    @BindView(R.id.tv_banner)
    TextBannerView tvBanner;
    @BindView(R.id.banner_advertising)
    BGABanner bannerAdvertising;
    @BindView(R.id.rv_advertising)
    RecyclerView rvAdvertising;
    @BindView(R.id.rv_hot_goods)
    RecyclerView rvHotGoods;
    @BindView(R.id.rv_like_goods)
    RecyclerView rvLikeGoods;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    /**
     * 轮播图所需参数
     */
    BannerHome banner;
    BannerHome beSelfnav;

    List<String> imgs = new ArrayList<>();
    List<String> tips = new ArrayList<>();
    List<String> url = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    List<String> imgsSelfnav = new ArrayList<>();
    List<String> tipsSelfnav = new ArrayList<>();
    List<String> urlSelfnav = new ArrayList<>();
    List<String> titleSelfnav = new ArrayList<>();
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.ll_like)
    LinearLayout llLike;


    @Override
    protected HomeAction initAction() {
        return new HomeAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }

    @Override
    protected void initialize() {
        init();
        loadView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        binding();
        mImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }

    @Override
    protected void init() {
        super.init();
        refreshLayout.setEnableLoadMore(false);//禁止上拉加载更多

        //轮播图
        banner = new BannerHome();
        bannerHome.setAdapter(banner);

        beSelfnav = new BannerHome();
        bannerAdvertising.setAdapter(beSelfnav);

    }

    /**
     * 获取首页数据
     */
    @Override
    public void getHomeData() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getHome();
        }
    }

    /**
     * 获取首页数据 成功
     */
    @Override
    public void getHomeDataSuccess(HomeDto homeDto) {
        loadDiss();
        refreshLayout.finishRefresh();

    }

    /**
     * 失败
     *
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
        refreshLayout.finishRefresh();
        showToast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }
}
