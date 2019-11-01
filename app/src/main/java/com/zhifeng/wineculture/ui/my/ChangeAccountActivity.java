package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.util.base.ActivityStack;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.adapters.AccountAdapter;
import com.zhifeng.wineculture.modules.AccountDto;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 切换用户
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/14 16:29
 * @Version: 1.0
 */

public class ChangeAccountActivity extends UserBaseActivity {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private AccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_change_account;
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }

    @Override
    protected void init() {
        super.init();
        mContext=this;
        mActicity=this;
        adapter=new AccountAdapter(mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        String json = MySp.getUserList(mContext);
        if (json != null) {
            List<AccountDto> list = new Gson().fromJson(json, new TypeToken<List<AccountDto>>() {
            }.getType());
            adapter.refresh(list);
        }
        loadView();
    }

    @Override
    protected void loadView() {
        adapter.setOnItemClickListener((parent, view, position, id) -> {
            AccountDto dto = (AccountDto) adapter.getItem(position);
            if (!dto.getToken().equals(MySp.getAccessToken(mContext))) {
                MySp.clearAllSP(mContext);
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra("phone", dto.getMobile());
                startActivity(intent);
                ActivityStack.getInstance().exitIsNotHaveMain(LoginActivity.class);
            }
            finish();
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
                .addTag("ChangeAccountActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @OnClick(R.id.tvChangeAccount)
    public void onViewClicked() {
        jumpActivityNotFinish(mContext, LoginActivity.class);
    }
}
