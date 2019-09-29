package com.zhifeng.wineculture.ui.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lgh.huanglib.util.base.ActivityStack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.WithdrawalRecordAction;
import com.zhifeng.wineculture.ui.impl.WithdrawalRecordView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
  *
  * @ClassName:     提现记录
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 15:27
  * @Version:        1.0
 */

public class WithdrawalRecordActivity extends UserBaseActivity<WithdrawalRecordAction> implements WithdrawalRecordView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    @Override
    public int intiLayout() {
        return R.layout.activity_withdrawal_record;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected WithdrawalRecordAction initAction() {
        return new WithdrawalRecordAction(this,this);
    }

    @Override
    public void getWithdrawalRecord() {

    }

    @Override
    public void getWithdrawalRecordSuccess() {

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
}
