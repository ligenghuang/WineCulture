package com.zhifeng.wineculture.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.SearchGoodsAction;
import com.zhifeng.wineculture.adapters.SearchGoodsHistoryAdapter;
import com.zhifeng.wineculture.modules.SearchGoodsHistoryDto;
import com.zhifeng.wineculture.ui.impl.SearchGoodsView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.view.FlowLayoutManager;
import com.zhifeng.wineculture.utils.view.SpaceItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.iv_search_top)
    ImageView ivSearchTop;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    SearchGoodsHistoryAdapter searchGoodsHistoryAdapter;

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

        searchGoodsHistoryAdapter = new SearchGoodsHistoryAdapter();
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        //设置每一个item间距
        rvSearchHistory.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        rvSearchHistory.setLayoutManager(flowLayoutManager);
        rvSearchHistory.setAdapter(searchGoodsHistoryAdapter);

        searchGoodsHistory();
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        searchGoodsHistoryAdapter.setOnClickListener(new SearchGoodsHistoryAdapter.OnClickListener() {
            @Override
            public void onClick(String keyword) {
                //todo 搜索商品
                etSearch.setText(keyword);
            }
        });
    }

    /**
     * 获取搜索历史
     */
    @Override
    public void searchGoodsHistory() {
        if(CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.searchGoodsHistory();
        }
    }

    /**
     * 获取搜索历史 成功
     * @param goodsHistoryDto
     */
    @Override
    public void searchGoodsHistorySuccess(SearchGoodsHistoryDto goodsHistoryDto) {
        loadDiss();
        List<SearchGoodsHistoryDto.DataBean> list = new ArrayList<>();
        list.addAll(goodsHistoryDto.getData());
        list.addAll(goodsHistoryDto.getData());
        list.addAll(goodsHistoryDto.getData());
        list.addAll(goodsHistoryDto.getData());
        searchGoodsHistoryAdapter.refresh(list);

        llSearch.setVisibility(View.GONE);
        llSearchHistory.setVisibility(View.VISIBLE);
    }

    @Override
    public void searchGoods() {

    }

    @Override
    public void searchGoodsSuccess() {

    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
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
                break;
            case R.id.iv_search_delete:
                //todo 删除搜索历史
                break;
            case R.id.tv_search_synthesize:
                //todo 综合排序
                break;
            case R.id.tv_search_sales:
                //todo 销量排序
                break;
            case R.id.ll_search_sort:
                //todo 价格排序
                break;
            case R.id.ll_search_screen:
                //todo 筛选
                break;
        }
    }
}
