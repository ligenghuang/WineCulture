package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.LogisticsDto;

/**
  *
  * @ClassName:     查看物流
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/17 11:01
  * @Version:        1.0
 */

public interface LogisticsView extends BaseView {
    void getLogostocs();
    void getLogostocsSuccess(LogisticsDto logostocsDto);
    void getLogisticsError(String msg);
}
