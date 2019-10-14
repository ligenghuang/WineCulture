package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.FootPrintDto;

/**
  *
  * @ClassName:     我的足迹
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 11:58
  * @Version:        1.0
 */

public interface MyFootPrintView extends BaseView {
    void getFootPrint();
    void getFootPrintSuccess(FootPrintDto footPrintDto);

    /**
     * 删除足迹
     * @param id
     */
    void deleteFootPrint(String id);
    void deleteFootPrintSuccess(String msg);
}
