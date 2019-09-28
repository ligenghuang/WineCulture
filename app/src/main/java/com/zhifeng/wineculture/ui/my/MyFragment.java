package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MyAction;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: 我的fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 14:41
 * @Version: 1.0
 */

public class MyFragment extends UserBaseFragment<MyAction> {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvId)
    TextView tvId;
    @BindView(R.id.tvMobile)
    TextView tvMobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_my, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected MyAction initAction() {
        return null;
    }

    @Override
    protected void initialize() {

    }

    @OnClick({R.id.tvMyOrder, R.id.tv_obligation, R.id.tv_toBeShipped, R.id.tv_toBeReceived, R.id.tv_toBeComment, R.id.tv_service, R.id.tv_memberCenter, R.id.tv_myteam, R.id.tv_popularize, R.id.tv_proxy, R.id.tv_myCollect, R.id.tv_myFootprint, R.id.tv_myComments, R.id.tv_myAddress, R.id.tv_aboutWineCulture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvMyOrder:
                jumpActivityNotFinish(mContext,MyOrderActivity.class);
                break;
            case R.id.tv_obligation:
                jumpActivityNotFinish(mContext,ObligationActivity.class);
                break;
            case R.id.tv_toBeShipped:
                jumpActivityNotFinish(mContext,ToBeShippedActivity.class);
                break;
            case R.id.tv_toBeReceived:
                jumpActivityNotFinish(mContext,ToBeReceivedActivity.class);
                break;
            case R.id.tv_toBeComment:
                jumpActivityNotFinish(mContext,ToBeCommentActivity.class);
                break;
            case R.id.tv_service:
                jumpActivityNotFinish(mContext,ServiceActivity.class);
                break;
            case R.id.tv_memberCenter:
                jumpActivityNotFinish(mContext,MemberCenterActivity.class);
                break;
            case R.id.tv_myteam:
                jumpActivityNotFinish(mContext,MyTeamActivity.class);
                break;
            case R.id.tv_popularize:
                jumpActivityNotFinish(mContext,PopularizeActivity.class);
                break;
            case R.id.tv_proxy:
                jumpActivityNotFinish(mContext,ProxyActivity.class);
                break;
            case R.id.tv_myCollect:
                jumpActivityNotFinish(mContext,MyCollectActivity.class);
                break;
            case R.id.tv_myFootprint:
                jumpActivityNotFinish(mContext,MyFootPrintActivity.class);
                break;
            case R.id.tv_myComments:
                jumpActivityNotFinish(mContext,MyCommentsActivity.class);
                break;
            case R.id.tv_myAddress:
                jumpActivityNotFinish(mContext,AddressListActivity.class);
                break;
            case R.id.tv_aboutWineCulture:
                jumpActivityNotFinish(mContext,AboutWineCultureActivity.class);
                break;
        }
    }
}
