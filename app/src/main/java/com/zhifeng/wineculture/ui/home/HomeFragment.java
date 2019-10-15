package com.zhifeng.wineculture.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.HomeAction;
import com.zhifeng.wineculture.adapters.AnnounceAdapter;
import com.zhifeng.wineculture.adapters.BannerHome;
import com.zhifeng.wineculture.adapters.HotGoodsAdapter;
import com.zhifeng.wineculture.adapters.LikeGoodsAdapter;
import com.zhifeng.wineculture.adapters.RecommendedAdapter;
import com.zhifeng.wineculture.modules.HomeDto;
import com.zhifeng.wineculture.ui.impl.HomeView;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.view.TextBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.ll_like)
    LinearLayout llLike;

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

    RecommendedAdapter recommendedAdapter;
    HotGoodsAdapter hotGoodsAdapter;
    LikeGoodsAdapter likeGoodsAdapter;


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

        recommendedAdapter = new RecommendedAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvAdvertising.setLayoutManager(linearLayoutManager);
        rvAdvertising.setAdapter(recommendedAdapter);

        hotGoodsAdapter = new HotGoodsAdapter(mContext);
        rvHotGoods.setLayoutManager(new LinearLayoutManager(mContext));
        rvHotGoods.setAdapter(hotGoodsAdapter);

        likeGoodsAdapter = new LikeGoodsAdapter(mContext);
        rvLikeGoods.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvLikeGoods.setAdapter(likeGoodsAdapter);

        loadDialog();
        getHomeData();
    }


    @Override
    protected void loadView() {
        super.loadView();
        //todo 公告列表点击事件
        tvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                L.e("lgh_item", "position   = " + position);
                jumpActivityNotFinish(mContext, AnnounceActivity.class);
//                Intent intent = new Intent(mContext, NoticeDetailActivity.class);
//                intent.putExtra("id",announceBeans.get(position).getId()+"");
//                startActivity(intent);
                tvBanner.stopViewAnimator();
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getHomeData();
            }
        });
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
        HomeDto.DataBean dataBean = homeDto.getData();
        setBanner(dataBean.getBanners());//轮播图
        setAnnounceList(dataBean.getAnnounce());//公告
        recommendedAdapter.refresh(dataBean.getTuijian());//推荐
        llHot.setVisibility(dataBean.getHot_goods().size() == 0 ? View.GONE : View.VISIBLE);
        hotGoodsAdapter.refresh(dataBean.getHot_goods());//热销商品
        llLike.setVisibility(dataBean.getLike().size() == 0 ? View.GONE : View.VISIBLE);
        likeGoodsAdapter.refresh(dataBean.getLike());//猜你喜欢

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

    private void setBanner(List<HomeDto.DataBean.BannersBean> banners) {
        //设置轮播图
        if (banners.size() != 0) {
            bannerHome.setVisibility(View.VISIBLE);
            imgs = new ArrayList<>();
            tips = new ArrayList<>();
            url = new ArrayList<>();
            titles = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                HomeDto.DataBean.BannersBean bannersBean = banners.get(i);
                imgs.add(bannersBean.getPicture());
                tips.add("");
                url.add(bannersBean.getUrl());
                titles.add(bannersBean.getTitle());
            }
            bannerHome.setAutoPlayAble(true);
            bannerHome.setData(imgs, tips);
            bannerHome.startAutoPlay();
        }
    }

    /**
     * 获取公告列表
     */
    private void setAnnounceList(List<HomeDto.DataBean.AnnounceBean> list) {
        if (list.size() != 0) {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                strings.add(list.get(i).getTitle());
            }
            tvBanner.setDatas(strings);
            tvBanner.startViewAnimator();
        }

    }

    @OnClick(R.id.ll_search)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.ll_search:
                //todo 搜索
                jumpActivityNotFinish(mContext,SearchGoodsActivity.class);
                break;
        }
    }
}
