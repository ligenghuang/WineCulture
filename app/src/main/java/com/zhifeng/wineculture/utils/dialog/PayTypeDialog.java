package com.zhifeng.wineculture.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.adapters.PayTypeAdapter;
import com.zhifeng.wineculture.modules.Temporary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: 支付方式
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/16 10:04
 * @Version: 1.0
 */

public class PayTypeDialog extends Dialog {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private PayTypeAdapter payTypeAdapter;
    Context context;
    List<Temporary.DataBean.PayTypeBean> list;

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PayTypeDialog(@NonNull Context context, int themeResId, List<Temporary.DataBean.PayTypeBean> list) {
        super(context, themeResId);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_type);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        win.setGravity(Gravity.CENTER);

        initView();
    }

    private void initView(){
        payTypeAdapter = new PayTypeAdapter(R.layout.layout_item_paytype);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setAdapter(payTypeAdapter);
        payTypeAdapter.refresh(list);
        payTypeAdapter.setOnClickListener(new PayTypeAdapter.OnClickListener() {
            @Override
            public void onClick(int type, String name) {
                onClickListener.onPayTypeClick(type,name);
                List<Temporary.DataBean.PayTypeBean> list = payTypeAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelect(list.get(i).getPay_type() == type);
                }
                payTypeAdapter.notifyDataSetChanged();
            }
        });
    }

    public interface OnClickListener{
        void onPayTypeClick(int type, String name);
    }
}
