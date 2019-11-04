package com.zhifeng.wineculture.ui.classify;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.lgh.huanglib.util.CheckNetwork;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ClassifyAction;
import com.zhifeng.wineculture.adapters.CategoryAdapter;
import com.zhifeng.wineculture.adapters.CategoryListAdapter;
import com.zhifeng.wineculture.modules.ClassifyDto;
import com.zhifeng.wineculture.ui.home.SearchGoodsActivity;
import com.zhifeng.wineculture.ui.impl.ClassifyView;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.data.MySp;
import com.zhifeng.wineculture.utils.diffUtils.ClissifyDiffCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: 分类fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 14:40
 * @Version: 1.0
 */

public class ClassifyFragment extends UserBaseFragment<ClassifyAction> implements ClassifyView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rv_left)
    RecyclerView rvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rvRight;

    CategoryListAdapter categoryListAdapter;
    CategoryAdapter categoryAdapter;

    List<ClassifyDto.DataBean> mDatas = new ArrayList<>();
    private static final int H_CODE_UPDATE = 1;
    private List<ClassifyDto.DataBean> mNewDatas;//增加一个变量暂存newList
    int position = 0;

    @Override
    protected ClassifyAction initAction() {
        return new ClassifyAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }

    @Override
    protected void initialize() {
        init();
        loadView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classify, container, false);
        ButterKnife.bind(this, view);
        binding();
        ImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }

    @Override
    protected void init() {
        super.init();
        categoryListAdapter = new CategoryListAdapter();
        rvLeft.setLayoutManager(new LinearLayoutManager(mContext));
        rvLeft.setAdapter(categoryListAdapter);

        categoryAdapter = new CategoryAdapter(mContext);
        rvRight.setLayoutManager(new LinearLayoutManager(mContext));
        rvRight.setAdapter(categoryAdapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible){
            getClassifyData();
        }
    }

    @Override
    protected void loadView() {
        super.loadView();
        categoryListAdapter.setOnClickListener(new CategoryListAdapter.OnClickListener() {
            @Override
            public void OnListClick(int id, ClassifyDto.DataBean goodsBean,int Position) {
                position = Position;
                List<ClassifyDto.DataBean> list = categoryListAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setClick(list.get(i).getCat_id() == id);
                }
                categoryListAdapter.notifyDataSetChanged();
                List<ClassifyDto.DataBean> dataBeanList = new ArrayList<>();
                dataBeanList.add(goodsBean);
                categoryAdapter.refresh(dataBeanList);
            }
        });
    }

    /**
     * 获取分类数据
     */
    @Override
    public void getClassifyData() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getClassifyData();
        }
    }

    /**
     * 获取分类数据 成功
     */
    @Override
    public void getClassifyDataSuccess(ClassifyDto classifyDto) {
        loadDiss();
        List<ClassifyDto.DataBean> list =classifyDto.getData();
        if (list.size() != 0){
            mNewDatas = classifyDto.getData();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //放在子线程中计算DiffResult
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ClissifyDiffCallBack(mDatas, mNewDatas), true);
                    Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                    message.obj = diffResult;//obj存放DiffResult
                    message.sendToTarget();
                }
            }).start();


        }else {
            //todo 暂无数据
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case H_CODE_UPDATE:
                    //取出Result
                    DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;
                    //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
                    diffResult.dispatchUpdatesTo(categoryListAdapter);
                    //别忘了将新数据给Adapter
                    mDatas = mNewDatas;
                    mDatas.get(position).setClick(true);
                    categoryListAdapter.refresh(mDatas);
                    List<ClassifyDto.DataBean> dataBeanList = new ArrayList<>();
                    dataBeanList.add(mDatas.get(position));
                    categoryAdapter.refresh(dataBeanList);
                    break;
            }
        }
    };

    /**
     * 失败
     *
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
        showToast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @OnClick(R.id.ll_search)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.ll_search:
                //todo 搜索
                if (!MySp.iSLoginLive(mContext)) {
                    //todo 未登录
                    jumpActivityNotFinish(mContext, LoginActivity.class);
                    return;
                }
                jumpActivityNotFinish(mContext, SearchGoodsActivity.class);
                break;
        }
    }
}
