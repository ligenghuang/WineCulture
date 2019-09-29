package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
  *
  * @ClassName:     转账--搜索用户
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 12:02
  * @Version:        1.0
 */

public interface TransferSearchView extends BaseView {

    void search(String text);
    void searchSuccess();
}
