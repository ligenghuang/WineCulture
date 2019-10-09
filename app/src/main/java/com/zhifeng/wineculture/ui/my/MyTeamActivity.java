package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.base.ActivityStack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 我的团队
 * @Author: Administrator
 * @Date: 2019/9/28 18:06
 */
public class MyTeamActivity extends UserBaseActivity {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvMemberNum)
    TextView tvMemberNum;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tvTeam)
    TextView tvTeam;
    @BindView(R.id.tvNormalMember)
    TextView tvNormalMember;
    @BindView(R.id.tvVip)
    TextView tvVip;
    @BindView(R.id.tvVip1)
    TextView tvVip1;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void init() {
        super.init();
        mActicity=this;
        mContext=this;
    }

    /**
     * 初始化标题栏
     */
    @Override
    protected void initTitlebar() {
        mImmersionBar
                .statusBarView(R.id.top_view)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("MyTeamActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.myteam_myteam);
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }
}
