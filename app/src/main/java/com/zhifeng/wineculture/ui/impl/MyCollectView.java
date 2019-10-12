package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.CollectionListDto;

/**
  *
  * @ClassName:     收藏列表
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/12 16:02
  * @Version:        1.0
 */
public interface MyCollectView extends BaseView {
    /**
     * 获取关注列表
     */
    void getCollectionList();
    void getCollectionListSuccess(CollectionListDto collectionListDto);

    /**
     * 取消关注
     * @param goods_id
     */
    void deleteCollection(String goods_id);
    void deleteCollectionSuccess(String msg);
}
