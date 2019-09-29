package com.zhifeng.wineculture.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

/**
  *
  * @ClassName:     商品详情页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 15:39
  * @Version:        1.0
 */

public class GoodsDetailActivity extends UserBaseActivity {

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }
}
