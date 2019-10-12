package com.zhifeng.wineculture.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lgh.huanglib.util.L;
import com.zhifeng.wineculture.adapters.ProvinceAdapter;
import com.zhifeng.wineculture.modules.RegionDto;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.utils.data.PhoneU;

import java.util.ArrayList;
import java.util.List;

public class AreaPickerView extends Dialog {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivBtn;

    private AreaPickerViewCallback areaPickerViewCallback;

    /**
     * View的集合
     */
    private List<View> views;
    /**
     * tab的集合
     */
    private List<String> strings;
    /**
     * 省
     */
    private List<RegionDto.DataBean> addressBeans;
    /**
     * 市
     */
    private List<RegionDto.DataBean> cityBeans;
    /**
     * 区
     */
    private List<RegionDto.DataBean> areaBeans;

    private Context context;

    private ViewPagerAdapter viewPagerAdapter;
    private ProvinceAdapter provinceAdapter;
    private ProvinceAdapter cityAdapter;
    private ProvinceAdapter areaAdapter;

    /**
     * 选中的区域下标 默认-1
     */
    private int provinceSelected = -1;
    private int citySelected = -1;
    private int areaSelected = -1;

    /**
     * 历史选中的区域下标 默认-1
     */
    private int oldProvinceSelected = -1;
    private int oldCitySelected = -1;
    private int oldAreaSelected = -1;

    private RecyclerView provinceRecyclerView;
    private RecyclerView areaRecyclerView;
    private RecyclerView cityRecyclerView;

    private boolean isCreate;

    private String city = "请选择";
    private String Code = "";
    private String name = "";

    public AreaPickerView(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_area_pickerview);
        Window window = this.getWindow();

        isCreate = true;

        /**
         * 位于底部
         */
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = PhoneU.getScreenPix(context).widthPixels;// 宽为手机屏幕宽
        params.height = PhoneU.getScreenPix(context).heightPixels * 4 / 5;// 高为手机屏幕高的4/5
        window.setAttributes(params);
        /**
         * 设置弹出动画
         */
        window.setWindowAnimations(R.style.PickerAnim);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        ivBtn = findViewById(R.id.iv_btn);
        ivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        strings = new ArrayList<>();
        strings.add("请选择");
        View provinceView = LayoutInflater.from(context)
                .inflate(R.layout.layout_recyclerview, null, false);
        View cityView = LayoutInflater.from(context)
                .inflate(R.layout.layout_recyclerview, null, false);
        View areaView = LayoutInflater.from(context)
                .inflate(R.layout.layout_recyclerview, null, false);
        provinceRecyclerView = provinceView.findViewById(R.id.recyclerview);
        cityRecyclerView = cityView.findViewById(R.id.recyclerview);
        areaRecyclerView = areaView.findViewById(R.id.recyclerview);

        views = new ArrayList<>();
        views.add(provinceView);
        views.add(cityView);
        views.add(areaView);

        /**
         * 配置adapter
         */
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        /**
         * 这句话设置了过后，假如又3个tab 删除第三个 刷新过后 第二个划第三个会有弹性
         * viewPager.setOffscreenPageLimit(2);
         */


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        provinceRecyclerView.scrollToPosition(oldProvinceSelected == -1 ? 0 : oldProvinceSelected);
                        break;
                    case 1:
                        cityRecyclerView.scrollToPosition(oldCitySelected == -1 ? 0 : oldCitySelected);
                        break;
                    case 2:
                        areaRecyclerView.scrollToPosition(oldAreaSelected == -1 ? 0 : oldAreaSelected);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    /**
     * 省
     *
     * @param addressBeans
     */
    public void setProvince(List<RegionDto.DataBean> addressBeans) {
        this.addressBeans = addressBeans;
        L.e("lgh_address", "address = " + addressBeans.size());
        provinceAdapter = new ProvinceAdapter(R.layout.item_address, addressBeans);
        provinceRecyclerView.setAdapter(provinceAdapter);
        LinearLayoutManager provinceManager = new LinearLayoutManager(context);
        provinceRecyclerView.setLayoutManager(provinceManager);
        provinceAdapter.setOnClickListener(new ProvinceAdapter.OnClickListener() {
            @Override
            public void onClick(String code, int position) {
                cityBeans = new ArrayList<>();
                areaBeans = new ArrayList<>();
                addressBeans.get(position).setStatus(true);
                provinceSelected = position;
                if (oldProvinceSelected != -1 && oldProvinceSelected != provinceSelected) {
                    addressBeans.get(oldProvinceSelected).setStatus(false);
                    Log.e("AreaPickerView", "清空");
                }
                areaPickerViewCallback.onProvinceSelected(code);
                oldCitySelected = -1;
                oldAreaSelected = -1;
                strings.set(0, addressBeans.get(position).getArea_name());
                Code = addressBeans.get(position).getCode();

            }
        });

    }

    /**
     * 获取市列表
     *
     * @param citys
     */
    public void setCityBeans(List<RegionDto.DataBean> citys) {
        if (citys.size() != 0) {
            cityBeans = citys;
            if (strings.size() == 1) {
                strings.add("请选择");
            } else if (strings.size() > 1) {
                strings.set(1, "请选择");
                if (strings.size() == 3) {
                    strings.remove(2);
                }
            }
            tabLayout.setupWithViewPager(viewPager);
            viewPagerAdapter.notifyDataSetChanged();
            tabLayout.getTabAt(1).select();
            oldProvinceSelected = provinceSelected;
            L.e("lgh_address", "address = " + cityBeans.size());
            cityAdapter = new ProvinceAdapter(R.layout.item_address, cityBeans);
            LinearLayoutManager cityListManager = new LinearLayoutManager(context);
            cityRecyclerView.setLayoutManager(cityListManager);
            cityRecyclerView.setAdapter(cityAdapter);
            cityAdapter.setOnClickListener(new ProvinceAdapter.OnClickListener() {
                @Override
                public void onClick(String code, int position) {
                    Code = code;
                    areaBeans = new ArrayList<>();
                    cityBeans.get(position).setStatus(true);
                    strings.set(1, cityBeans.get(position).getArea_name());
                    citySelected = position;
                    if (oldCitySelected != -1 && oldCitySelected != citySelected) {
                        cityBeans.get(oldCitySelected).setStatus(false);
                    }
                    if (position != oldCitySelected) {
                        areaPickerViewCallback.onCitySelected(code);
                        oldAreaSelected = -1;
                    }


                }
            });
        } else {
            if (strings.size() ==2) {
                strings.remove(1);
            }else if(strings.size() == 3){
                strings.remove(1);
                strings.remove(1);
            }
            oldCitySelected = -1;
            provinceAdapter.notifyDataSetChanged();
//            strings.set(1, city);
            tabLayout.setupWithViewPager(viewPager);
            viewPagerAdapter.notifyDataSetChanged();
            dismiss();
            name = "";
            for (int i = 0; i < strings.size(); i++) {
                name = name + strings.get(i);
            }
            areaPickerViewCallback.callback(Code, name);
        }

    }

    /**
     * 获取区列表
     *
     * @param areas
     */
    public void setAreas(List<RegionDto.DataBean> areas) {
        if (areas.size() != 0) {
            areaBeans = areas;
            cityAdapter.notifyDataSetChanged();
            if (strings.size() == 2) {
                strings.add("请选择");
            } else if (strings.size() == 3) {
                strings.set(2, "请选择");
            }
            tabLayout.setupWithViewPager(viewPager);
            viewPagerAdapter.notifyDataSetChanged();
            tabLayout.getTabAt(2).select();


            areaAdapter = new ProvinceAdapter(R.layout.item_address, areaBeans);
            LinearLayoutManager areaListManager = new LinearLayoutManager(context);
            areaRecyclerView.setLayoutManager(areaListManager);
            areaRecyclerView.setAdapter(areaAdapter);
            areaAdapter.setOnClickListener(new ProvinceAdapter.OnClickListener() {
                @Override
                public void onClick(String code, int position) {
                    strings.set(2, areaBeans.get(position).getArea_name());
                    Code = code;
                    tabLayout.setupWithViewPager(viewPager);
                    viewPagerAdapter.notifyDataSetChanged();
                    areaBeans.get(position).setStatus(true);
                    areaSelected = position;
                    if (oldAreaSelected != -1 && oldAreaSelected != position) {
                        areaBeans.get(oldAreaSelected).setStatus(false);
                    }
                    oldAreaSelected = areaSelected;
                    areaAdapter.notifyDataSetChanged();
                    dismiss();
                    name = "";
                    for (int i = 0; i < strings.size(); i++) {
                        name = name + strings.get(i);
                    }
                    areaPickerViewCallback.callback(Code, name);
                }
            });

        } else {
            if (strings.size() >3) {
                strings.remove(2);
            }
            oldAreaSelected = -1;
            cityAdapter.notifyDataSetChanged();
//            strings.set(1, city);
            tabLayout.setupWithViewPager(viewPager);
            viewPagerAdapter.notifyDataSetChanged();
            dismiss();
            name = "";
            for (int i = 0; i < strings.size(); i++) {
                name = name + strings.get(i);
            }
            areaPickerViewCallback.callback(Code, name);
        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(views.get(position));
            Log.e("AreaPickView", "------------instantiateItem");
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
            Log.e("AreaPickView", "------------destroyItem");
        }

    }

    public interface AreaPickerViewCallback {
        void onProvinceSelected(String code);

        void onCitySelected(String code);

        void callback(String code, String name);
    }

    public void setAreaPickerViewCallback(AreaPickerViewCallback areaPickerViewCallback) {
        this.areaPickerViewCallback = areaPickerViewCallback;
    }


}
