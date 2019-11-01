package com.zhifeng.wineculture.ui.classify;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.lgh.huanglib.util.CheckNetwork;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ClassifyAction;
import com.zhifeng.wineculture.adapters.CategoryAdapter;
import com.zhifeng.wineculture.adapters.CategoryListAdapter;
import com.zhifeng.wineculture.modules.ClassifyDto;
import com.zhifeng.wineculture.ui.home.SearchGoodsActivity;
import com.zhifeng.wineculture.ui.impl.ClassifyView;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.data.MySp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: 分类fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 14:40
 * @Version: 1.0
 */

public class ClassifyFragment extends UserBaseFragment<ClassifyAction> implements ClassifyView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rv_left)
    RecyclerView rvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rvRight;

    CategoryListAdapter categoryListAdapter;
    CategoryAdapter categoryAdapter;

    @Override
    protected ClassifyAction initAction() {
        return new ClassifyAction((RxAppCompatActivity) getActivity(), this);
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
        view = inflater.inflate(R.layout.fragment_classify, container, false);
        ButterKnife.bind(this, view);
        binding();
        ImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }

    @Override
    protected void init() {
        super.init();
        categoryListAdapter = new CategoryListAdapter();
        rvLeft.setLayoutManager(new LinearLayoutManager(mContext));
        rvLeft.setAdapter(categoryListAdapter);

        categoryAdapter = new CategoryAdapter(mContext);
        rvRight.setLayoutManager(new LinearLayoutManager(mContext));
        rvRight.setAdapter(categoryAdapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible){
            getClassifyData();
        }
    }

    @Override
    protected void loadView() {
        super.loadView();
        categoryListAdapter.setOnClickListener(new CategoryListAdapter.OnClickListener() {
            @Override
            public void OnListClick(int id, ClassifyDto.DataBean goodsBean) {
                List<ClassifyDto.DataBean> list = categoryListAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setClick(list.get(i).getCat_id() == id);
                }
                categoryListAdapter.notifyDataSetChanged();
                List<ClassifyDto.DataBean> dataBeanList = new ArrayList<>();
                dataBeanList.add(goodsBean);
                categoryAdapter.refresh(dataBeanList);
            }
        });
    }

    /**
     * 获取分类数据
     */
    @Override
    public void getClassifyData() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getClassifyData();
        }
    }

    /**
     * 获取分类数据 成功
     */
    @Override
    public void getClassifyDataSuccess(ClassifyDto classifyDto) {
        loadDiss();
        List<ClassifyDto.DataBean> list =classifyDto.getData();
        if (list.size() != 0){
            list.get(0).setClick(true);
            categoryListAdapter.refresh(list);
            List<ClassifyDto.DataBean> dataBeanList = new ArrayList<>();
            dataBeanList.add(list.get(0));
            categoryAdapter.refresh(dataBeanList);
        }else {
            //todo 暂无数据
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

    @OnClick(R.id.ll_search)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.ll_search:
                //todo 搜索
                if (!MySp.iSLoginLive(mContext)) {
                    //todo 未登录
                    jumpActivityNotFinish(mContext, LoginActivity.class);
                    return;
                }
                jumpActivityNotFinish(mContext, SearchGoodsActivity.class);
                break;
        }
    }
}
