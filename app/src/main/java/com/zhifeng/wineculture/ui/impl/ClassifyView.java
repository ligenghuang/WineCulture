package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.ClassifyDto;

/**
  *
  * @ClassName:     分类fragment
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/28 17:41
  * @Version:        1.0
 */

public interface ClassifyView extends BaseView {

    /**
     * 获取分类数据
     */
    void getClassifyData();
    void getClassifyDataSuccess(ClassifyDto classifyDto);
}
