package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.MyTeamDto;

public interface MyTeamView extends BaseView {
    void getMyTeam();

    void getMyTeamSuccess(MyTeamDto myTeamDto);
}
