package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
  *
  * @ClassName:     首页fragment
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/28 17:24
  * @Version:        1.0
 */

public interface HomeView extends BaseView {

    /**
     * 获取首页数据
     */
    void getHomeData();
    void getHomeDataSuccess();
}
