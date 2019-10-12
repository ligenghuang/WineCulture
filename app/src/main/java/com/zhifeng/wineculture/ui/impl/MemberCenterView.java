package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.MemberCenterDto;

/**
  *
  * @ClassName:     会员中心
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 10:33
  * @Version:        1.0
 */

public interface MemberCenterView extends BaseView {

    void getMemberCenterData();
    void getMemberCenterDataSuccess(MemberCenterDto memberCenterDto);
}
