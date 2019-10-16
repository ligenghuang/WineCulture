package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.CommentView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;

public class CommentAction extends BaseAction<CommentView> {
    public CommentAction(RxAppCompatActivity _rxAppCompatActivity, CommentView commentView) {
        super(_rxAppCompatActivity);
        attachView(commentView);
    }

    public void postComment(String comments) {
        post(WebUrlUtil.POST_ORDER_COMMENT, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"comments",comments), WebUrlUtil.POST_ORDER_COMMENT)));
    }

    /**
     * sticky:表明优先接收最高级  threadMode = ThreadMode.MAIN：表明在主线程
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MessageEvent(final Action action) {
        L.e("xx", "action   接收到数据更新....." + action.getIdentifying() + " action.getErrorType() : " + action.getErrorType());
        final String msg = action.getMsg(action);
        Observable.just(action.getErrorType())
                .all(integer -> (integer == 200)).subscribe(aBoolean -> {
            // 输出返回结果
            L.e("xx", "输出返回结果 " + aBoolean);
            if (WebUrlUtil.POST_ORDER_COMMENT.equals(action.getIdentifying())) {
                if (aBoolean) {
                    GeneralDto myCommentListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                    }.getType());
                    if (myCommentListDto.getStatus() == 200) {
                        view.postCommentSuccess(myCommentListDto);
                        return;
                    }
                    view.onError(myCommentListDto.getMsg(), myCommentListDto.getStatus());
                    return;
                }
                view.onError(msg, action.getErrorType());
            }
        });
    }

    public void toRegister() {
        register(this);
    }

    public void toUnregister() {
        unregister(this);
    }
}
