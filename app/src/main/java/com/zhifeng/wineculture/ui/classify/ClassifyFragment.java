package com.zhifeng.wineculture.ui.classify;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ClassifyAction;
import com.zhifeng.wineculture.ui.impl.ClassifyView;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        mImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
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
    public void getClassifyDataSuccess() {
        loadDiss();
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
}
