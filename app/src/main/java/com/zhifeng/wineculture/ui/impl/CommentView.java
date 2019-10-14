package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;

public interface CommentView extends BaseView {
    void postComment();

    void postCommentSuccess(GeneralDto generalDto);
}
