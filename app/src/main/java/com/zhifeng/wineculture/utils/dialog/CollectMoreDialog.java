package com.zhifeng.wineculture.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.zhifeng.wineculture.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
/**
  *
  * @ClassName:     我的收藏  操作
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/12 15:56
  * @Version:        1.0
 */

public class CollectMoreDialog extends Dialog {

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CollectMoreDialog(@NonNull Context context, int themeResId) {
        super(context,themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_collect_more);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
        win.setGravity(Gravity.BOTTOM);

    }



    @OnClick({R.id.tv_dialog_cancel_collect, R.id.tv_dialog_cancel,R.id.image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_cancel_collect:
                //todo 取消收藏
                onClickListener.onCancelCollect();
                break;
            case R.id.tv_dialog_cancel:

            case R.id.image:
                //todo 取消
                dismiss();
                break;
        }
    }

    public interface OnClickListener{
        void onCancelCollect();
    }
}
