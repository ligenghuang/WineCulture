package com.zhifeng.wineculture.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.TransferSearchAction;
import com.zhifeng.wineculture.adapters.TransferSearchAdapter;
import com.zhifeng.wineculture.modules.SearchPhoneDto;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.TransferSearchView;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 转账--搜索用户
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 11:38
 * @Version: 1.0
 */

public class TransferSearchActivity extends UserBaseActivity<TransferSearchAction> implements TransferSearchView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.f_right_iv)
    ImageView fRightIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_transfer_search)
    EditText etTransferSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    TransferSearchAdapter transferSearchAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_transfer_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected TransferSearchAction initAction() {
        return new TransferSearchAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        transferSearchAdapter = new TransferSearchAdapter(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(transferSearchAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showEt();
            }
        }, 100);

        loadView();

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
                .addTag("TransferSearchActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.balance_tab_4));

    }

    private void showEt(){
        etTransferSearch.setFocusable(true);
        etTransferSearch.setFocusableInTouchMode(true);
        etTransferSearch.requestFocus();

        new Thread(new Runnable() {

            public void run() {
                InputMethodManager imm = (InputMethodManager) mActicity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etTransferSearch, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }).start();
    }


    @Override
    protected void loadView() {
        super.loadView();
        etTransferSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //todo 判断是否输入手机号码
                if (TextUtils.isEmpty(etTransferSearch.getText().toString())){
                    showNormalToast(ResUtil.getString(R.string.balance_transfer_tab_1));
                    return false;
                }
                //todo 判断手机号码格式是否正确
                if (!ValidateUtils.isPhone2(etTransferSearch.getText().toString())){
                    showNormalToast(ResUtil.getString(R.string.balance_transfer_tab_3));
                    return false;
                }

                search(etTransferSearch.getText().toString());
            }
            return false;
        });

        transferSearchAdapter.setOnClickListener(new TransferSearchAdapter.OnClickListener() {
            @Override
            public void onClick(int id) {
                //todo 转账

            }
        });
    }

    /**
     * 搜索
     * @param text
     */
    @Override
    public void search(String text) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.search(text);
        }
    }

    @Override
    public void searchSuccess(SearchPhoneDto searchPhoneDto) {
        loadDiss();
        transferSearchAdapter.refresh(searchPhoneDto.getData());
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

    @OnClick(R.id.f_right_iv)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.f_right_iv:

                break;
        }
    }
}
