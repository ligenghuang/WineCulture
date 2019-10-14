package com.zhifeng.wineculture.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.zhifeng.wineculture.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description:相机和相册选择弹窗
 * autour: lgh
 * date: 2019/5/14 11:40
 * update: 2019/5/14
 * version:
 */
public class PicturesDialog extends Dialog {

    @BindView(R.id.btn_cancel)
    TextView cancel;
    @BindView(R.id.camera_tv)
    TextView camera_tv;
    @BindView(R.id.photo_tv)
    TextView photo_tv;

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PicturesDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_dialog_head);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        win.setGravity(Gravity.BOTTOM);

    }

    @OnClick({R.id.btn_cancel,R.id.camera_tv,R.id.photo_tv})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_cancel:
                //todo 取消
                break;
            case R.id.camera_tv:
                //todo 相机
                onClickListener.onCamera();
                break;
            case R.id.photo_tv:
                //todo 相册
                onClickListener.onPhoto();
                break;
        }
        dismiss();
    }


    public interface OnClickListener{
        void onCamera();
        void onPhoto();
    }
}
