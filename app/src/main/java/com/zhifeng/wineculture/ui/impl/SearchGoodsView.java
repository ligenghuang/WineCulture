package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.SearchGoodsDto;
import com.zhifeng.wineculture.modules.SearchGoodsHistoryDto;

/**
  *
  * @ClassName:     搜索页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/15 16:21
  * @Version:        1.0
 */

public interface SearchGoodsView extends BaseView {

    /**
     * 获取搜索历史
     */
    void searchGoodsHistory();
    void searchGoodsHistorySuccess(SearchGoodsHistoryDto goodsHistoryDto);

    /**
     * 删除搜索历史
     */
    void deleteHistory();
    void deleteHistorySuccess(GeneralDto generalDto);

    /**
     * 获取搜索结果
     */
    void searchGoods();
    void loadMoreGoods();
    void searchGoodsSuccess(SearchGoodsDto searchGoodsDto);

}
