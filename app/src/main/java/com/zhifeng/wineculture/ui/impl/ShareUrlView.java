package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.ShareUrlDto;

public interface ShareUrlView extends BaseView {
    void getShareUrl();

    void getShareUrlSuccess(ShareUrlDto shareUrlDto);
}
