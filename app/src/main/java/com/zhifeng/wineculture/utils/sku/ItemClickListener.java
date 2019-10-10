package com.zhifeng.wineculture.utils.sku;

import android.os.Handler;
import android.os.Message;

import com.lgh.huanglib.util.L;
import com.zhifeng.wineculture.utils.sku.adapter.SkuAdapter;

import java.util.ArrayList;
import java.util.List;


public class ItemClickListener implements SkuAdapter.OnClickListener {

    private UiData mUiData;
    private SkuAdapter currentAdapter;
    private Handler handler;
    public ItemClickListener(UiData uiData, SkuAdapter currentAdapter, Handler handler) {
        this.mUiData = uiData;
        this.handler = handler;
        this.currentAdapter = currentAdapter;
    }
    // 成功返回
    public void sendSuccess(Handler handler, Object obj, int what) {
        Message msg = new Message();
        msg.what = what;// ;
        msg.obj = obj;
        handler.sendMessage(msg);
    }
    public int getSkuSize(String skuString){
        String skuStr[] = skuString.split(";");
        return skuStr.length;
    }
    @Override
    public void onItemClickListener(int position) {
        //屏蔽不可点击
        if (currentAdapter.getAttributeMembersEntities().get(position).getStatus() == 2) {
            return;
        }
        // 设置当前单选点击
        for (ProductModel.AttributesEntity.AttributeMembersEntity entity : currentAdapter.getAttributeMembersEntities()) {
            if (entity.equals(currentAdapter.getAttributeMembersEntities().get(position))) {
                if (entity.getStatus()==1){
                    entity.setStatus(0);
                    currentAdapter.setCurrentSelectedItem(entity);
                }else {
                    entity.setStatus(1);
                    //添加已经选择的对象
                    currentAdapter.setCurrentSelectedItem(entity);
                }
            } else {
                entity.setStatus(0);
            }
        }
        //存放当前被点击的按钮
        mUiData.getSelectedEntities().clear();
        for (int i = 0; i < mUiData.getAdapters().size(); i++) {
            if (mUiData.getAdapters().get(i).getCurrentSelectedItem() != null) {
                if (mUiData.getAdapters().get(i).getCurrentSelectedItem().getStatus()==1){
                      mUiData.getSelectedEntities().add(mUiData.getAdapters().get(i).getCurrentSelectedItem());
                }
            }
        }
        //处理未选中的按钮
        for (int i = 0; i < mUiData.getAdapters().size(); i++) {
            for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mUiData.getAdapters().get(i).getAttributeMembersEntities()) {
                // 处理没有数据和没有库存(检测单个)
                if (mUiData.getResult().get(entity.getSku_attr1() + "") == null || mUiData.getResult().get(entity.getSku_attr1() + "").getStock() <= 0) {
                    entity.setStatus(2);
                } else if (entity.equals(mUiData.getAdapters().get(i).getCurrentSelectedItem())) {
                    if (mUiData.getAdapters().get(i).getCurrentSelectedItem().getStatus()==1)
                    { entity.setStatus(1);

                    }else{
                        entity.setStatus(0);
                    }
                } else {
                    entity.setStatus(0);
                }
                // 冒泡排序
                List<ProductModel.AttributesEntity.AttributeMembersEntity> cacheSelected = new ArrayList<>();
                cacheSelected.add(entity);
                cacheSelected.addAll(mUiData.getSelectedEntities());
                for (int j = 0; j < cacheSelected.size() - 1; j++) {
                    for (int k = 0; k < cacheSelected.size() - 1 - j; k++) {
                        ProductModel.AttributesEntity.AttributeMembersEntity cacheEntity;
                        if (cacheSelected.get(k).getAttributeGroupId() > cacheSelected.get(k + 1).getAttributeGroupId()) {
                            //交换数据
                            cacheEntity = cacheSelected.get(k);
                            cacheSelected.set(k, cacheSelected.get(k + 1));
                            cacheSelected.set(k + 1, cacheEntity);
                        }
                    }
                }
                for (int j = 0; j < cacheSelected.size() - 1; j++) {
                    for (int k = 0; k < cacheSelected.size() - 1 - j; k++) {
                        if (cacheSelected.get(k).getAttributeGroupId() == cacheSelected.get(k + 1).getAttributeGroupId()) {
                            cacheSelected.remove(k + 1);
                        }
                    }
                }
                StringBuffer buffer = new StringBuffer();
                float selecPrice=0f;
                float unSelecPrice=0f;
                for (ProductModel.AttributesEntity.AttributeMembersEntity selectedEntity : cacheSelected) {
                    buffer.append(selectedEntity.getSku_attr1() + ";");
                    float price = Float.valueOf(selectedEntity.getPrice());
                    selecPrice += price;
                }


                //TODO 检查数据
                if (mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)) != null ) {
                    entity.setStatus(entity.getStatus() == 1 ? 1 : 0);
                    if(entity.getStatus() == 1){
                        sendSuccess(handler,selecPrice,3);
                        if(getSkuSize(buffer.substring(0, buffer.length() - 1))==mUiData.getAdapters().size()){//选完所有更新
                            L.e("lgh_sku","lgh_sku  = "+1);
                            sendSuccess(handler,mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)).getGroupon_price(),1);//原价
                            sendSuccess(handler,mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)).getStock(),2);//库存
                            sendSuccess(handler,mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)).getSku_id(),4);//
                            sendSuccess(handler,mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)).getName(),5);
                            sendSuccess(handler,mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)).getVirtual_sales(),61);//销量

                        }else {
                            sendSuccess(handler,"0",1);
                            sendSuccess(handler,"0",2);
                            sendSuccess(handler,"-1",4);
                            L.e("lgh_sku","lgh_sku  = "+2);
                        }
                        break;
                    }else {//未选完所有更新
                        float unPirce = Float.valueOf(entity.getPrice());
                        unSelecPrice = selecPrice - unPirce;
                        sendSuccess(handler,unSelecPrice,3);
                        sendSuccess(handler,"0",7);
                        sendSuccess(handler,"-1",4);
                        L.e("lgh_sku","lgh_sku  = "+3);
                    }

                } else {
                    entity.setStatus(2);
                }
            }
            mUiData.getAdapters().get(i).notifyDataSetChanged();
        }
    }
}
