package com.zhifeng.wineculture.actions;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.net.WebUrlUtil;
import com.zhifeng.wineculture.ui.impl.RefundView;
import com.zhifeng.wineculture.utils.config.MyApp;
import com.zhifeng.wineculture.utils.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RefundAction extends BaseAction<RefundView> {
    public RefundAction(RxAppCompatActivity _rxAppCompatActivity, RefundView refundView) {
        super(_rxAppCompatActivity);
        attachView(refundView);
    }

    public void refund(String order_id, String refund_reason, List<String> img) {
        String imgStr = new Gson().toJson(img);
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", MySp.getAccessToken(MyApp.getContext()))
                .addFormDataPart("order_id", order_id)
                .addFormDataPart("refund_reason", refund_reason)
                .addFormDataPart("img", imgStr);
        RequestBody body = build.build();
        post(WebUrlUtil.POST_APPLY_REFUND, false, service -> manager.runHttp(service.PostData(body, WebUrlUtil.POST_APPLY_REFUND)));
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
            if (WebUrlUtil.POST_APPLY_REFUND.equals(action.getIdentifying())) {
                if (aBoolean) {
                    GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                    }.getType());
                    if (generalDto.getStatus() == 200) {
                        view.refundSuccess(generalDto);
                        return;
                    }
                    view.onError(generalDto.getMsg(), generalDto.getStatus());
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
