package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;

import butterknife.ButterKnife;

/**
  *
  * @ClassName:     收支fragment
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 10:59
  * @Version:        1.0
 */

public class BalanceOfPaymentFragment extends UserBaseFragment {
    View view;

    @Override
    protected BaseAction initAction() {
        return null;
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
        view = inflater.inflate(R.layout.fragment_balance_of_payment, container, false);
        ButterKnife.bind(this, view);
        binding();
        return view;
    }
}
