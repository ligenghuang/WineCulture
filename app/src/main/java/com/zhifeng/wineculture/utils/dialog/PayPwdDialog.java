package com.zhifeng.wineculture.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.adapters.KeyBoardAdapter;
import com.zhifeng.wineculture.utils.view.OnPasswordInputFinish;
import com.zhifeng.wineculture.utils.view.VirtualKeyboardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
  *
  * @ClassName:     支付密码输入框
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 14:55
  * @Version:        1.0
 */

public class PayPwdDialog extends Dialog implements DialogInterface.OnDismissListener {
    @BindView(R.id.tv_pass1)
    TextView tvPass1;
    @BindView(R.id.img_pass1)
    ImageView imgPass1;
    @BindView(R.id.tv_pass2)
    TextView tvPass2;
    @BindView(R.id.img_pass2)
    ImageView imgPass2;
    @BindView(R.id.tv_pass3)
    TextView tvPass3;
    @BindView(R.id.img_pass3)
    ImageView imgPass3;
    @BindView(R.id.tv_pass4)
    TextView tvPass4;
    @BindView(R.id.img_pass4)
    ImageView imgPass4;
    @BindView(R.id.tv_pass5)
    TextView tvPass5;
    @BindView(R.id.img_pass5)
    ImageView imgPass5;
    @BindView(R.id.tv_pass6)
    TextView tvPass6;
    @BindView(R.id.img_pass6)
    ImageView imgPass6;
    @BindView(R.id.virtualKeyboardView)
    VirtualKeyboardView virtualKeyboardView;
    @BindView(R.id.tv_dialog_money)
    TextView tvMoney;
    @BindView(R.id.iv_pay_type)
    ImageView ivPayType;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;

    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？

    private GridView gridView;

    private ArrayList<Map<String, String>> valueList;

    private int currentIndex = -1;    //用于记录当前输入密码格位置
    private boolean isCheck;

    Context mContext;
    double money;
    String payTypeName;
    int paytype;

    public PayPwdDialog(@NonNull Context context, int themeResId, double money, String payTypeName,int paytype) {
        super(context, themeResId);
        mContext = context;
        this.money = money;
        this.payTypeName = payTypeName;
        this.paytype = paytype;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_pwd);
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
        win.setWindowAnimations(R.style.CommonBottomDialogAnim);


        gridView = virtualKeyboardView.getGridView();
        initView();
        initValueList();
        setupView();
        loadView();
        setOnDismissListener(this);
    }


    // 这里，我们没有使用默认的数字键盘，因为第10个数字不显示.而是空白
    private void initValueList() {

        valueList = new ArrayList<>();

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }


    private void initView() {
        tvMoney.setText("￥" + money);
        tvPayType.setText(payTypeName);
        ivPayType.setImageResource(paytype == 1?R.drawable.icon_balance_ali:R.drawable.icon_balance_bank);

        tvList = new TextView[6];

        imgList = new ImageView[6];

        tvList[0] = tvPass1;
        tvList[1] = tvPass2;
        tvList[2] = tvPass3;
        tvList[3] = tvPass4;
        tvList[4] = tvPass5;
        tvList[5] = tvPass6;


        imgList[0] = imgPass1;
        imgList[1] = imgPass2;
        imgList[2] = imgPass3;
        imgList[3] = imgPass4;
        imgList[4] = imgPass5;
        imgList[5] = imgPass6;

    }

    private void setupView() {

        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
        gridView.setAdapter(keyBoardAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        ++currentIndex;
                        tvList[currentIndex].setText(valueList.get(position).get("name"));
                        tvList[currentIndex].setVisibility(View.INVISIBLE);
                        imgList[currentIndex].setVisibility(View.VISIBLE);
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            tvList[currentIndex].setText("");
                            tvList[currentIndex].setVisibility(View.VISIBLE);
                            imgList[currentIndex].setVisibility(View.INVISIBLE);
                            currentIndex--;
                        }
                    }
                }
            }
        });
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {


        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 1) {

                    String strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱

                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    pass.inputFinish(strPassword);    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }

    public VirtualKeyboardView getVirtualKeyboardView() {

        return virtualKeyboardView;
    }


    private void loadView() {
        /**
         *  是否需要验证
         */
        setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                onFinishInput.inputFinish(password);
//
            }
        });

    }

    @OnClick({R.id.iv_close,R.id.dialog_forget_pwd})
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                onFinishInput.close();
                break;
            case R.id.dialog_forget_pwd:
                onFinishInput.forget();
                break;
        }
    }


    /**
     * 设置默认
     */
    public void setDefaultTxt() {


        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            tvList[i].post(new Runnable() {
                @Override
                public void run() {
                    tvList[finalI].setText("");
                    tvList[finalI].setVisibility(View.VISIBLE);
                    imgList[finalI].setVisibility(View.INVISIBLE);
                }
            });


        }
        currentIndex = -1;
    }

    OnFinishInput onFinishInput;

    public void setOnFinishInput(OnFinishInput onFinishInput) {
        this.onFinishInput = onFinishInput;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    public interface OnFinishInput {
        void inputFinish(String password);
        void forget();
        void close();
    }
}
