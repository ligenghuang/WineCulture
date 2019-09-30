package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
  *
  * @ClassName:     购物车
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/30 14:54
  * @Version:        1.0
 */

public interface CartView extends BaseView {

    /**
     * 获取购物商品列表
     */
    void getCartList();
    void getCartListSuccess();

}
