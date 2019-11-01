package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MyFootPrintAction;
import com.zhifeng.wineculture.adapters.MyFootPrintAdapter;
import com.zhifeng.wineculture.modules.FootPrintDto;
import com.zhifeng.wineculture.ui.impl.MyFootPrintView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName:
 * @Description: 我的足迹
 * @Author: Administrator
 * @Date: 2019/9/28 18:06
 */
public class MyFootPrintActivity extends UserBaseActivity<MyFootPrintAction> implements MyFootPrintView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.f_right_tv)
    TextView fRightTv;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_foot_print_delete_all)
    TextView tvFootPrintDeleteAll;
    @BindView(R.id.tv_foot_print_delete)
    TextView tvFootPrintDelete;
    @BindView(R.id.ll_foot_print_delete)
    LinearLayout llFootPrintDelete;

    MyFootPrintAdapter myFootPrintAdapter;

    boolean isManagement = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_my_foot_print;
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        refreshLayout.setEnableLoadMore(false);

        myFootPrintAdapter = new MyFootPrintAdapter(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myFootPrintAdapter);

        loadView();
        refreshLayout.autoRefresh();

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
                .addTag("MyFootPrintActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_myfootprint);
    }

    @Override
    protected MyFootPrintAction initAction() {
        return new MyFootPrintAction(this, this);
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getFootPrint();
            }
        });

        myFootPrintAdapter.setOnClickListener(new MyFootPrintAdapter.OnClickListener() {
            @Override
            public void onClick(int id) {
                List<FootPrintDto.DataBean> list = myFootPrintAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getGoods_id() == id) {
                        list.get(i).setClick(true);
                    }
                }
                myFootPrintAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 获取我的足迹
     */
    @Override
    public void getFootPrint() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getMyFootPrion();
        } else {
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 获取我的足迹成功
     *
     * @param footPrintDto
     */
    @Override
    public void getFootPrintSuccess(FootPrintDto footPrintDto) {
        refreshLayout.finishRefresh();
        myFootPrintAdapter.refresh(footPrintDto.getData());
        isManagement = false;
        setManagement();
    }

    /**
     * 删除足迹
     * @param id
     */
    @Override
    public void deleteFootPrint(String id) {
        if (TextUtils.isEmpty(id)){
            showNormalToast(R.string.my_foot_print_tab_5);
            return;
        }
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.deleteFootPrint(id);
        }
    }

    /**
     * 删除足迹 成功
     * @param msg
     */
    @Override
    public void deleteFootPrintSuccess(String msg) {
        loadDiss();
        showNormalToast(msg);
        getFootPrint();
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
        showNormalToast(message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @OnClick({R.id.f_right_tv, R.id.tv_foot_print_delete_all, R.id.tv_foot_print_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.f_right_tv:
                //todo 编辑
                isManagement = !isManagement;
                setManagement();
                break;
            case R.id.tv_foot_print_delete_all:
                //todo 清空
                deleteAll();
                break;
            case R.id.tv_foot_print_delete:
                //todo 删除
                delete();
                break;
        }
    }

    /**
     * 清空
     */
    private void deleteAll() {
        List<FootPrintDto.DataBean> list = myFootPrintAdapter.getAllData();
        String id = "";
        for (int i = 0; i < list.size(); i++) {
            //todo 拼接id
            if (i == 0) {
                id = list.get(i).getGoods_id() + "";
            } else {
                id = id + "," + list.get(i).getGoods_id();
            }

        }
        deleteFootPrint(id);
    }

    /**
     * 删除
     */
    private void delete(){
        List<FootPrintDto.DataBean> list = myFootPrintAdapter.getAllData();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            FootPrintDto.DataBean dataBean = list.get(i);
            if (dataBean.isClick()){
               //todo 拼接id
               if (id.length() > 0) {
                   id.append(",");
               }
               id.append(dataBean.getGoods_id());
           }
        }
        deleteFootPrint(id.toString());
    }

    private void setManagement() {
        fRightTv.setText(ResUtil.getString(isManagement ? R.string.my_foot_print_tab_3 : R.string.my_foot_print_tab_4));
        myFootPrintAdapter.setManagement(isManagement);
        myFootPrintAdapter.notifyDataSetChanged();
        llFootPrintDelete.setVisibility(isManagement ? View.VISIBLE : View.GONE);
    }
}
