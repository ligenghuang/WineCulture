package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.modules.OpenMemberDto;

/**
  *
  * @ClassName:     开通会员
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/17 11:56
  * @Version:        1.0
 */

public interface OpenMemberView extends BaseView {

    void getOpenMember();
    void getOpenMemberSuccess(OpenMemberDto openMemberDto);
}
