package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.SharePosterDto;

public interface ShareUrlView extends BaseView {
    void getShareUrl();

    void getShareUrlSuccess(SharePosterDto sharePosterDto);
}
