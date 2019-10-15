package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.AnnounceDto;

/**
  *
  * @ClassName:     公告详情
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/15 9:55
  * @Version:        1.0
 */

public interface AnnounceView extends BaseView {

    void getAnnounce();
    void getAnnounceSuccess(AnnounceDto announceDto);
}
