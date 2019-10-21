package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MyTeamAction;
import com.zhifeng.wineculture.adapters.TeamAdapter;
import com.zhifeng.wineculture.modules.MyTeamDto;
import com.zhifeng.wineculture.ui.impl.MyTeamView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 我的团队
 * @Author: Administrator
 * @Date: 2019/9/28 18:06
 */
public class MyTeamActivity extends UserBaseActivity<MyTeamAction> implements MyTeamView {
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
    private TeamAdapter adapter;

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
        mActicity = this;
        mContext = this;
        adapter = new TeamAdapter();
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.autoRefresh();
        loadView();
    }

    @Override
    protected void loadView() {
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            getMyTeam();
        });
       adapter.setOnClickListener(new TeamAdapter.OnClickListener() {
           @Override
           public void onClick(int id) {
               startActivity(FansOrderActivity.class, "id", String.valueOf(id));
           }
       });
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
    protected MyTeamAction initAction() {
        return new MyTeamAction(this, this);
    }

    @Override
    public void getMyTeam() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getMyTeam(1);
        }
    }

    @Override
    public void getMyTeamSuccess(MyTeamDto myTeamDto) {
        refreshLayout.finishRefresh();
        int teamCount = myTeamDto.getData().getTeam_count();
        tvMemberNum.setText(String.valueOf(teamCount));
        List<MyTeamDto.DataBean.ListBean> beans = myTeamDto.getData().getList();
        adapter.refresh(beans);
    }

    @Override
    public void onError(String message, int code) {
        refreshLayout.finishRefresh();
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
