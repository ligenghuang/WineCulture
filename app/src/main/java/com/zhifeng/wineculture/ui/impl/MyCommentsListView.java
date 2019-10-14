package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.MyCommentListDto;

public interface MyCommentsListView extends BaseView {
    void getCommentList();

    void getCommentListSuccess(MyCommentListDto myCommentListDto);
}
