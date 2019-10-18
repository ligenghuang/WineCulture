package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderCommentListDto;

public interface CommentView extends BaseView {
    void getOrderCommentList();

    void getOrderCommentListSuccess(OrderCommentListDto orderCommentListDto);

    void postComment();

    void postCommentSuccess(GeneralDto generalDto);

    void postCommentFail(String msg, int code);
}
