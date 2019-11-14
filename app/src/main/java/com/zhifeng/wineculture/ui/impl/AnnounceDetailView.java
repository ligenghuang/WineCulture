package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.AnnounceDetailDto;

/**
  *
  * @ClassName:     公告详情
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/11/14 16:49
  * @Version:        1.0
 */

public interface AnnounceDetailView extends BaseView {

    void getAnnounceDetail();
    void getAnnounceDetailSuccess(AnnounceDetailDto detailDto);
}
