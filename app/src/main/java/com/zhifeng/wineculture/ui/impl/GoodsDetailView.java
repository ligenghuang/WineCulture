package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GoodsDetailDto;

/**
  *
  * @ClassName:    商品详情
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/10 10:12
  * @Version:        1.0
 */

public interface GoodsDetailView extends BaseView {

    void getGoodsDetail();
    void getGoodsDetailSucces(GoodsDetailDto goodsDetailDto);

    /**
     * 取消关注或关注
     */
    void deleteOrAddCollection();
    void deleteOrAddCollection(String msg);

    /**
     * 加入购物车
     * @param sku_id
     * @param cart_number
     */
    void addCart(int sku_id,int cart_number);
    void addCartSuccess(String msg);

    /**
     * 立即购买
     * @param sku_id
     * @param cart_number
     */
    void buyNow(int sku_id,int cart_number);
    void buyNowSuccess(int cartId);
}
