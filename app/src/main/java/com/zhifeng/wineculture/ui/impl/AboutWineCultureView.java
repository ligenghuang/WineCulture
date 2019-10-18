package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;

/**
  *
  * @ClassName:     关于酒文化
  * @escription:
  * @Author:         lgh
  * @CreateDate:     2019/10/18 12:00
  * @Version:        1.0
 */

public interface AboutWineCultureView extends BaseView {

    void getAbout();
    void getAboutSuccess(GeneralDto generalDto);
}
