package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.CommentDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderCommentListDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.CommentView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CommentAction extends BaseAction<CommentView> {
    public CommentAction(RxAppCompatActivity _rxAppCompatActivity, CommentView commentView) {
        super(_rxAppCompatActivity);
        attachView(commentView);
    }

    public void getOrderCommentList(String order_id) {
        post(WebUrlUtil.POST_ORDER_COMMENT_LIST, false, service -> manager.runHttp(service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()), "order_id", order_id), WebUrlUtil.POST_ORDER_COMMENT_LIST)));
    }

    public void postComment(List<CommentDto> list) {
        String json = new Gson().toJson(list);
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", MySp.getAccessToken(MyApp.getContext()))
                .addFormDataPart("comments",json);
        RequestBody body = build.build();
        post(WebUrlUtil.POST_ORDER_COMMENT, false, service -> manager.runHttp(service.PostData(
                body, WebUrlUtil.POST_ORDER_COMMENT)));
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
            switch (action.getIdentifying()) {
                case WebUrlUtil.POST_ORDER_COMMENT_LIST:
                    if (aBoolean) {
                        OrderCommentListDto orderCommentListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<OrderCommentListDto>() {
                        }.getType());
                        if (orderCommentListDto.getStatus() == 200) {
                            view.getOrderCommentListSuccess(orderCommentListDto);
                            return;
                        }
                        view.onError(orderCommentListDto.getMsg(), orderCommentListDto.getStatus());
                        return;
                    }
                    view.onError(msg, action.getErrorType());
                    break;
                case WebUrlUtil.POST_ORDER_COMMENT:
                    if (aBoolean) {
                        GeneralDto myCommentListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                        }.getType());
                        if (myCommentListDto.getStatus() == 200) {
                            view.postCommentSuccess(myCommentListDto);
                            return;
                        }
                        view.postCommentFail(myCommentListDto.getMsg(), myCommentListDto.getStatus());
                        return;
                    }
                    view.postCommentFail(msg, action.getErrorType());
                    break;
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
