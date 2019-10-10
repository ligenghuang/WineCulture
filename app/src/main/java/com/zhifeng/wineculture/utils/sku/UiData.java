package com.zhifeng.wineculture.utils.sku;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zhifeng.wineculture.utils.sku.adapter.SkuAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description: sku数据管理类
 * autour: huang
 * date: 2019/9/19 21:29
 * update: 2019/9/19
 * version:
 */
public class UiData {

    BottomSheetDialog mBottomSheetDialog;

    // 保存多组adapter
    List<SkuAdapter> adapters = new ArrayList<>();

    //存放被选中的按钮对应的数据
    List<ProductModel.AttributesEntity.AttributeMembersEntity> selectedEntities = new ArrayList<>();

    //存放计算结果
    Map<String, BaseSkuModel> result;

    public List<SkuAdapter> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<SkuAdapter> adapters) {
        this.adapters = adapters;
    }

    public Map<String, BaseSkuModel> getResult() {
        return result;
    }

    public void setResult(Map<String, BaseSkuModel> result) {
        this.result = result;
    }

    public BottomSheetDialog getBottomSheetDialog() {
        return mBottomSheetDialog;
    }

    public void setBottomSheetDialog(BottomSheetDialog bottomSheetDialog) {
        mBottomSheetDialog = bottomSheetDialog;
    }

    public List<ProductModel.AttributesEntity.AttributeMembersEntity> getSelectedEntities() {
        return selectedEntities;
    }

    public void setSelectedEntities(List<ProductModel.AttributesEntity.AttributeMembersEntity> selectedEntities) {
        this.selectedEntities = selectedEntities;
    }
}
