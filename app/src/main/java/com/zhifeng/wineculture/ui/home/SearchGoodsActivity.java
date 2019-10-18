package com.zhifeng.wineculture.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupPosition;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.SearchGoodsAction;
import com.zhifeng.wineculture.adapters.BannerHome;
import com.zhifeng.wineculture.adapters.SearchGoodsAdapter;
import com.zhifeng.wineculture.adapters.SearchGoodsHistoryAdapter;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.ScreenDto;
import com.zhifeng.wineculture.modules.SearchGoodsDto;
import com.zhifeng.wineculture.modules.SearchGoodsHistoryDto;
import com.zhifeng.wineculture.ui.impl.SearchGoodsView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.popup.CustomDrawerPopupView;
import com.zhifeng.wineculture.utils.view.FlowLayoutManager;
import com.zhifeng.wineculture.utils.view.SpaceItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

import static com.lgh.huanglib.util.data.DensityUtil.dp2px;

/**
 * @ClassName: 搜索页
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/15 15:13
 * @Version: 1.0
 */

public class SearchGoodsActivity extends UserBaseActivity<SearchGoodsAction> implements SearchGoodsView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @BindView(R.id.banner_advertising)
    BGABanner bannerAdvertising;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    SearchGoodsHistoryAdapter searchGoodsHistoryAdapter;

    BannerHome beSelfnav;
    List<String> imgsSelfnav = new ArrayList<>();
    List<String> tipsSelfnav = new ArrayList<>();
    List<String> urlSelfnav = new ArrayList<>();
    List<String> titleSelfnav = new ArrayList<>();

    String Keyword;
    int page = 1;
    int number_sales = 0;
    int price = 0;
    //升序 asc 降序desc
    String sort = "";
    int shipping_price = 0;
    int stock = 0;
    int sales = 0;
    boolean isRefresh = true;
    boolean isMore = true;

    SearchGoodsAdapter searchGoodsAdapter;
    List<ScreenDto> list = new ArrayList<>();
    @Override
    public int intiLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    /**
     * 初始化标题栏
     */
    @Override
    protected void initTitlebar() {
        super.initTitlebar();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("SearchGoodsActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());

    }

    @Override
    protected SearchGoodsAction initAction() {
        return new SearchGoodsAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mContext = this;
        mActicity = this;

        beSelfnav = new BannerHome();
        bannerAdvertising.setAdapter(beSelfnav);

        searchGoodsHistoryAdapter = new SearchGoodsHistoryAdapter();
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        //设置每一个item间距
        rvSearchHistory.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        rvSearchHistory.setLayoutManager(flowLayoutManager);
        rvSearchHistory.setAdapter(searchGoodsHistoryAdapter);

        searchGoodsAdapter = new SearchGoodsAdapter(this);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(searchGoodsAdapter);

        searchGoodsHistory();
        loadView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showEt();
            }
        }, 200);

    }

    private void showEt() {
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();

        new Thread(new Runnable() {

            public void run() {
                InputMethodManager imm = (InputMethodManager) mActicity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etSearch, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }).start();
    }

    @Override
    protected void loadView() {
        super.loadView();
        searchGoodsHistoryAdapter.setOnClickListener(new SearchGoodsHistoryAdapter.OnClickListener() {
            @Override
            public void onClick(String keyword) {
                //todo 搜索商品
                etSearch.setText(keyword);
                Keyword = keyword;
                loadDialog();
                searchGoods();
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreGoods();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                searchGoods();
            }
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
            }
            return false;
        });
    }

    /**
     * 获取搜索历史
     */
    @Override
    public void searchGoodsHistory() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.searchGoodsHistory();
        }
    }

    /**
     * 获取搜索历史 成功
     *
     * @param goodsHistoryDto
     */
    @Override
    public void searchGoodsHistorySuccess(SearchGoodsHistoryDto goodsHistoryDto) {
        loadDiss();
        searchGoodsHistoryAdapter.refresh(goodsHistoryDto.getData());

        llSearch.setVisibility(View.GONE);
        llSearchHistory.setVisibility(View.VISIBLE);

    }

    /**
     * 删除搜索历史
     */
    @Override
    public void deleteHistory() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.delHistory();
        }
    }

    /**
     * 删除搜索历史
     *
     * @param generalDto
     */
    @Override
    public void deleteHistorySuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(generalDto.getMsg());
        searchGoodsHistoryAdapter.refresh(new ArrayList<>());
    }

    /**
     * 搜索商品
     */
    @Override
    public void searchGoods() {
        if (CheckNetwork.checkNetwork2(mContext)) {

            page = 1;
            isRefresh = true;
            baseAction.searchGoods(Keyword, page, number_sales, price, sort);
        }
    }

    /**
     * 获取更多商品
     */
    @Override
    public void loadMoreGoods() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            page++;
            isRefresh = false;
            baseAction.searchGoods(Keyword, page, number_sales, price, sort);
        }
    }

    /**
     * 搜索成功
     *
     * @param searchGoodsDto
     */
    @Override
    public void searchGoodsSuccess(SearchGoodsDto searchGoodsDto) {
        loadDiss();
        hideInput();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        llSearch.setVisibility(View.VISIBLE);
        llSearchHistory.setVisibility(View.GONE);
        SearchGoodsDto.DataBeanX dataBean = searchGoodsDto.getData();
        if (isRefresh) {
            setAdBanner(dataBean.getBanners());
            list = new ArrayList<>();
            list.add(new ScreenDto("查看全部",false));
            list.add(new ScreenDto("仅看包邮",false));
            list.add(new ScreenDto("仅看有货",false));
            list.add(new ScreenDto("促销商品",false));
        }
        SearchGoodsDto.DataBeanX.ListBean listBeans = dataBean.getList();
        List<SearchGoodsDto.DataBeanX.ListBean.DataBean> beans = listBeans.getData();
        if (beans.size() > 0) {
            isMore = page < listBeans.getLast_page();
            loadSwapTab();
            if (isRefresh) {
                searchGoodsAdapter.refresh(beans);
            } else {
                searchGoodsAdapter.loadMore(beans);
            }
        } else {
            isMore = false;
            loadSwapTab();
        }

    }

    private void setAdBanner(List<SearchGoodsDto.DataBeanX.BannersBean> banners) {
        //设置轮播图
        if (banners.size() != 0) {
            bannerAdvertising.setVisibility(View.VISIBLE);
            imgsSelfnav = new ArrayList<>();
            tipsSelfnav = new ArrayList<>();
            urlSelfnav = new ArrayList<>();
            titleSelfnav = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                SearchGoodsDto.DataBeanX.BannersBean bannersBean = banners.get(i);
                imgsSelfnav.add(bannersBean.getPicture());
                tipsSelfnav.add("");
                urlSelfnav.add(bannersBean.getUrl());
                titleSelfnav.add(bannersBean.getTitle());
            }
            bannerAdvertising.setAutoPlayAble(true);
            bannerAdvertising.setData(imgsSelfnav, tipsSelfnav);
            bannerAdvertising.startAutoPlay();
        }
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
        refreshLayout.finishLoadMore();
        showNormalToast(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @OnClick({R.id.tv_search, R.id.iv_search_delete, R.id.tv_search_synthesize, R.id.tv_search_sales, R.id.ll_search_sort, R.id.ll_search_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                //todo 搜索
                search();
                break;
            case R.id.iv_search_delete:
                //todo 删除搜索历史
                deleteHistory();
                break;
            case R.id.tv_search_synthesize:
                //todo 综合排序
                number_sales = 0;
                price = 0;
                sort = "";
                refreshLayout.autoRefresh();
                break;
            case R.id.tv_search_sales:
                //todo 销量排序
                number_sales = 1;
                price = 0;
                sort = "";

                refreshLayout.autoRefresh();
                break;
            case R.id.ll_search_sort:
                //todo 价格排序
                number_sales = 0;
                price = 1;
                if (TextUtils.isEmpty(sort) || sort.equals("desc")) {
                    sort = "asc";
                } else {
                    sort = "desc";
                }
                refreshLayout.autoRefresh();
                break;
            case R.id.ll_search_screen:
                //todo 筛选
                new XPopup.Builder(mActicity)
                        .popupPosition(PopupPosition.Right)//右边
                        .hasStatusBarShadow(true) //启用状态栏阴影
                        .atView(llSearch)
                        .asCustom(new CustomDrawerPopupView(mActicity, list, new CustomDrawerPopupView.OnListClickListener() {
                            @Override
                            public void onClick(boolean t1, boolean t2, boolean t3, boolean t4) {
                                shipping_price = t1?1:0;//仅看包邮
                                stock = t1?1:0;//仅看有货
                                sales = t1?1:0;//促销商品
                                refreshLayout.autoRefresh();
                            }
                        }))
                        .show();
                break;
        }
    }

    /**
     * 搜索
     */
    private void search() {
        if (TextUtils.isEmpty(etSearch.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.search_tab_2));
            return;
        }
        number_sales = 0;
        price = 0;
        sort = "";
        Keyword = etSearch.getText().toString();
        loadDialog();
        searchGoods();

        hideInput();
    }

    /**
     * tab变换 加载更多的显示方式
     */
    private void loadSwapTab() {
        if (!isMore) {
            L.e("xx", "设置为没有加载更多....");
            refreshLayout.finishLoadMoreWithNoMoreData();
            refreshLayout.setNoMoreData(true);
        } else {
            L.e("xx", "设置为可以加载更多....");
            refreshLayout.setNoMoreData(false);
        }
    }
}
