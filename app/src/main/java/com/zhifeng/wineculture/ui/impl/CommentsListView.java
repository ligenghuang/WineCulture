package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.CommentsListDto;

public interface CommentsListView extends BaseView {
    void getCommentList();

    void getCommentListSuccess(CommentsListDto commentsListDto);
}
