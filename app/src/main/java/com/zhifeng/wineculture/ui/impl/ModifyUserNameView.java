package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;

public interface ModifyUserNameView extends BaseView {
    void modifyRealName();

    void modifyRealNameSuccess(GeneralDto generalDto);
}
