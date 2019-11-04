package com.zhifeng.wineculture.utils.popup;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.core.DrawerPopupView;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.adapters.ScreenAdapter;
import com.zhifeng.wineculture.modules.ScreenDto;

import java.util.ArrayList;
import java.util.List;

public class CustomDrawerPopupView extends DrawerPopupView {
    Context context;
    boolean isClick = false;
    List<ScreenDto> list = new ArrayList<>();

    OnListClickListener onListClickListener;
    ScreenAdapter screenAdapter;

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }

    public CustomDrawerPopupView(@NonNull Context context, List<ScreenDto> list, OnListClickListener onListClickListener) {
        super(context);
        this.context = context;
        this.list = list;
        this.onListClickListener = onListClickListener;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_drawer_popup;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        screenAdapter = new ScreenAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(screenAdapter);
        screenAdapter.setOnClickListener(new ScreenAdapter.OnClickListener() {
            @Override
            public void onClick() {
                isClick = true;
            }
        });
        screenAdapter.refresh(list);
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void onDismiss() {
        onListClickListener.onClick(list.get(0).isClick(),list.get(1).isClick(),list.get(2).isClick(),list.get(3).isClick(),isClick,screenAdapter.getAllData());
        super.onDismiss();

    }

    public interface OnListClickListener {
        void onClick(boolean t1,boolean t2,boolean t3,boolean t4,boolean isClick,List<ScreenDto> list);
    }

}
