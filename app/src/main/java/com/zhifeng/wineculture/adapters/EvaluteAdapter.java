package com.zhifeng.wineculture.adapters;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lgh.huanglib.util.config.GlideApp;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderCommentListDto;
import com.zhifeng.wineculture.utils.view.FlowLayout;

/**
 * description:发表评论
 * autour: feijin.lgh
 * date: 2018/10/22 17:48
 * update: 2018/10/22
 * version:
 */
public class EvaluteAdapter extends BaseQuickAdapter<OrderCommentListDto.DataBean, BaseViewHolder> {
    public String text;
    FlowLayout picFlow;

    public EvaluteAdapter() {
        super(R.layout.item_activitycomment);
    }

    /**
     * 加载数据
     *
     * @param viewHolder
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder viewHolder, OrderCommentListDto.DataBean item) {
        picFlow = (FlowLayout) viewHolder.getView(R.id.pic_flow);
        ImageView imageIv = (ImageView) viewHolder.getView(R.id.ivGoods);
        TextView textSizeTv = (TextView) viewHolder.getView(R.id.tvGoodsName);
        EditText noteTv = (EditText) viewHolder.getView(R.id.etContent);
        GlideApp.with(mContext).load(item.getImg())
                .centerCrop().placeholder(R.drawable.icon_goods_img).error(R.drawable.icon_goods_img)
                .into(imageIv);
        /**
         *  保存数据
         */
        noteTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String note = noteTv.getText().toString();
                /**
                 * 失去焦点的时候 保存文本
                 */
                if (!hasFocus) {
                    item.setNote(note);
                }
            }
        });

        noteTv.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
                int length;
                if (TextUtils.isEmpty(s)) {
                    length = 0;
                } else {
                    length = s.length();
                }
//                textSizeTv.setText(FormatUtils.format(R.string.text_size, length + ""));
                /**
                 * 存储 最后一个
                 */
                if (getData().size() - 1 == viewHolder.getPosition()) {
                    item.setNote(s.toString());
                }
            }
        });

    }

    /**
     * 添加 加好
     *
     * @param person
     * @param position
     * @param lp
     * @param images
     */
//    private void add(final OrderDetailDto.DataBean.OrderBean.OrderDetailDTOSBean person, final int position, ViewGroup.MarginLayoutParams lp, List<OrderDetailDto.DataBean.OrderBean.OrderDetailDTOSBean.AvatarsDto> images) {
//        View view = (View) LinearLayout.inflate(MyApplication.getInstance(), R.layout.iv_opinion, null);
//        ImageView image = (ImageView) view.findViewById(R.id.education_iv);
//        image.setImageResource(R.drawable.common_btn_add_picture);
//        RelativeLayout stuhomework_rl = (RelativeLayout) view.findViewById(R.id.education_iv_rl);
//        if (images.size() == 6) {
//            image.setVisibility(View.GONE);
//        }
//        stuhomework_rl.setVisibility(View.GONE);
//        L.e("liao", "----->加载添加图片" + images.toString());
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                L.e("xx", " 点击  1 ");
//                statusClickListener.pictureOrder(person, position);
//            }
//        });
//        picFlow.addView(view, lp);
//    }

    /**
     * 获取res String
     */
    private String getString(int res) {
        return mContext.getString(res);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.mOnItemLongClickListener = itemLongClickListener;
    }

    public OnStatusClickListener getStatusClickListener() {
        return statusClickListener;
    }

    public void setStatusClickListener(OnStatusClickListener statusClickListener) {
        this.statusClickListener = statusClickListener;
    }

    private OnStatusClickListener statusClickListener;

    public interface OnStatusClickListener {
        void pictureOrder(OrderCommentListDto.DataBean bean, int position);
    }
}
