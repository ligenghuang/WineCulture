package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.AddAddressAction;
import com.zhifeng.wineculture.ui.impl.AddAddressView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 新增或编辑收货地址
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 18:20
 * @Version: 1.0
 */

public class AddAddressActivity extends UserBaseActivity<AddAddressAction> implements AddAddressView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_consignee)
    EditText etConsignee;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_district)
    TextView tvDistrict;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.ll_address_default)
    LinearLayout llAddressDefault;

    int id = 0;
    boolean Isdistrict = false;
    int is_default = 0;

    @Override
    public int intiLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected AddAddressAction initAction() {
        return new AddAddressAction(this,this);
    }

    /**
     * 初始化标题栏
     */
    @Override
    protected void initTitlebar() {
        super.initTitlebar();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("AddAddressActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.address_list_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        id = getIntent().getIntExtra("address_id",0);

        if (id != 0){
            getAddress();
        }

    }


    /**
     * 获取收货地址信息
     */
    @Override
    public void getAddress() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.getAddress(id);
        }
    }

    /**
     * 获取收货地址信息 成功
     */
    @Override
    public void getAddressSuccess() {
        loadDiss();
    }

    /**
     * 编辑收货地址
     */
    @Override
    public void editAddress() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.editAddress();
        }
    }

    /**
     * 编辑收货地址 成功
     */
    @Override
    public void editAddressSuccess() {
        loadDiss();
    }

    /**
     * 新增收货地址
     */
    @Override
    public void addAddress() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.addAddress();
        }
    }

    /**
     *  新增收货地址 成功
     */
    @Override
    public void addAddressSuccess() {
        loadDiss();
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

    @OnClick({R.id.tv_address_confirm,R.id.ll_address_default,R.id.tv_district})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_address_confirm:
                //todo 保存
                confirm();
                break;
            case R.id.ll_address_default:
                //todo 设置默认地址
                is_default = is_default == 1 ? 0 : 1;
                llAddressDefault.setSelected(is_default == 1);
                break;
            case R.id.tv_district:
                //todo 选择收货地址
                break;
        }
    }

    /**
     * 保存数据
     */
    private void confirm() {
        //todo 判断收货人是否为空
        if (TextUtils.isEmpty(etConsignee.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.add_address_tab_11));
            return;
        }

        //todo 判断联系电话是否为空
        if (TextUtils.isEmpty(etMobile.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.add_address_tab_12));
            return;
        }

        //todo 判断手机号码格式是否正确
        if (ValidateUtils.isPhone2(etMobile.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.add_address_tab_13));
            return;
        }

        //todo 判断是否选择收货地址
        if (!Isdistrict){
            showNormalToast(ResUtil.getString(R.string.add_address_tab_14));
            return;
        }

        //todo 判断详细收货地址是否为空
        if (TextUtils.isEmpty(etAddress.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.add_address_tab_15));
            return;
        }


        if (id == 0){
            //新增收货地址
        }else {
            //编辑收货地址
        }
    }
}
