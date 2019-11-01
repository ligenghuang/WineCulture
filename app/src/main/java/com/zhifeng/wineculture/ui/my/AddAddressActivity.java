package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
import com.zhifeng.wineculture.modules.AddressDetailDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.RegionDto;
import com.zhifeng.wineculture.modules.post.AddOrEditAddressPost;
import com.zhifeng.wineculture.ui.impl.AddAddressView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.dialog.AreaPickerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

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


    int address_id = 0;
    boolean Isdistrict = false;
    String district = "0";
    int is_default = 0;
    boolean isProvince = true;
    boolean isCity = false;
    boolean isArea = false;

    AreaPickerView areaPickerView;

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
        areaPickerView = new AreaPickerView(this, R.style.Dialog);
        address_id = getIntent().getIntExtra("address_id", 0);
        if (address_id != 0) {
            getAddress(address_id);
        }
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        areaPickerView.setAreaPickerViewCallback(new AreaPickerView.AreaPickerViewCallback() {
            @Override
            public void onProvinceSelected(String code) {
                isProvince = false;
                isCity = true;
                isArea = false;
                loadDialog();
                getRegion(code);
            }

            @Override
            public void onCitySelected(String code) {
                isProvince = false;
                isCity = false;
                isArea = true;
                loadDialog();
                getRegion(code);
            }

            @Override
            public void callback(String code,String name) {
                Isdistrict = true;
                district = code;
                tvDistrict.setText(name.replaceFirst("请选择",""));
            }
        });
    }

    /**
     * 获取收货地址详情
     *
     * @param id
     */
    @Override
    public void getAddress(int id) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getAddress(id);
        }
    }

    /**
     * 获取地址成功
     *
     * @param addressDetailDto
     */
    @Override
    public void getAddressSuccess(AddressDetailDto addressDetailDto) {
        loadDiss();
        AddressDetailDto.DataBean dataBean = addressDetailDto.getData();
        etConsignee.setText(dataBean.getConsignee());//收货人
        etConsignee.setSelection(dataBean.getConsignee().length());
        etMobile.setText(dataBean.getMobile());//联系电话
        //收货地址
        Isdistrict = true;
        district = dataBean.getDistrictcode();
        tvDistrict.setText(dataBean.getProvincename()+dataBean.getCityname()+dataBean.getDistrictname());
        etAddress.setText(dataBean.getAddress());//收货地址
        is_default = dataBean.getIs_default();
        llAddressDefault.setSelected(is_default == 1);
    }

    /**
     * 编辑添加地址
     *
     * @param post
     */
    @Override
    public void addOrEditAddress(AddOrEditAddressPost post) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.AddOrEditAddress(post);
        }
    }

    /**
     * 编辑添加地址 成功
     *
     * @param generalDto
     */
    @Override
    public void addOrEditAddressSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(generalDto.getMsg());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    public void getRegion(String code) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getRegion(code);
        }
    }

    @Override
    public void getRegionSuccess(RegionDto regionDto) {

        if (isProvince) {
            areaPickerView.show();
            areaPickerView.setProvince(regionDto.getData());
        }else if (isCity){
            areaPickerView.setCityBeans(regionDto.getData());
        }else if (isArea){
            areaPickerView.setAreas(regionDto.getData());
        }
        loadDiss();
    }

    @Override
    public void getRegionError() {
        loadDiss();
        if (isCity){
            areaPickerView.setCityBeans(new ArrayList<>());
        }else if (isArea){
            areaPickerView.setAreas(new ArrayList<>());
        }
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

    @OnClick({R.id.tv_district, R.id.ll_address_default, R.id.tv_address_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_district:
                isProvince = true;
                isCity = false;
                isArea = false;
                loadDialog();
                getRegion("0");
                break;
            case R.id.ll_address_default:
                //todo 设置为默认地址
                is_default = is_default == 1 ? 0 : 1;
                llAddressDefault.setSelected(is_default == 1);
                break;
            case R.id.tv_address_confirm:
                //todo 保存
                confirm();
                break;
        }
    }

    /**
     * 保存
     */
    private void confirm() {
        AddOrEditAddressPost post = new AddOrEditAddressPost();
        post.setAddress_id(address_id);

        //判断收货人是否为空
        if (TextUtils.isEmpty(etConsignee.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.add_address_tab_11));
            return;
        }
        post.setConsignee(etConsignee.getText().toString());

        //判断联系电话是否为空
        if (TextUtils.isEmpty(etMobile.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.add_address_tab_4));
            return;
        }

        if (!ValidateUtils.isPhone2(etMobile.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.add_address_tab_17));
            return;
        }

        post.setMobile(etMobile.getText().toString());

        //判断是否选择收货地址
        if (!Isdistrict) {
            showNormalToast(ResUtil.getString(R.string.add_address_tab_12));
            return;
        }
        post.setDistrict(district );

        //判断详细地址是否为空
        if (TextUtils.isEmpty(etAddress.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.add_address_tab_7));
            return;
        }
        post.setAddress(etAddress.getText().toString());

        post.setIs_default(is_default);
        addOrEditAddress(post);

    }
}
