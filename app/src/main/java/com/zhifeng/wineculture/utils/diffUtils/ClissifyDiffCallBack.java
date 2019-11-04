package com.zhifeng.wineculture.utils.diffUtils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.lgh.huanglib.util.L;
import com.zhifeng.wineculture.modules.ClassifyDto;

import java.util.List;
/**
  *
  * @ClassName:     分类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/22 15:49
  * @Version:        1.0
 */

public class ClissifyDiffCallBack extends DiffUtil.Callback {

    private List<ClassifyDto.DataBean> olddatas;
    private List<ClassifyDto.DataBean> newDatas;

    public ClissifyDiffCallBack(List<ClassifyDto.DataBean> olddatas, List<ClassifyDto.DataBean> newDatas) {
        this.olddatas = olddatas;
        this.newDatas = newDatas;
    }

    public int getOldListSize() {
        return olddatas != null ? olddatas.size() : 0;
    }

    public int getNewListSize() {
        return newDatas != null ? newDatas.size() : 0;
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil用返回的信息（true/false）来检测当前item的内容是否发生了变化
     * 所以你可以根据你的UI去改变它的返回值
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * @param oldItemPosition The position of the item in the old list
     *                        旧数据的item
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     *                        新数据某个替换了旧数据的item
     * @return True if the contents of the items are the same or false if they are different.
     * true代表着2个item内容相同，否则，不同
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        L.e("lgh_diff",);
        return olddatas.get(oldItemPosition).getCat_id() == newDatas.get(newItemPosition).getCat_id();
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return ((olddatas.get(oldItemPosition).getCat_id() == newDatas.get(newItemPosition).getCat_id())
                && (olddatas.get(oldItemPosition).getGoods().size() == newDatas.get(newItemPosition).getGoods().size()));
    }

    /**
     * 当areItemsTheSame(int, int)返回true，且areContentsTheSame(int, int)返回false时，DiffUtils会回调此方法，
     * 去得到这个Item（有哪些）改变的payload。
     * 例如，如果你用RecyclerView配合DiffUtils，你可以返回  这个Item改变的那些字段，可以用那些信息去执行正确的动画
     * 默认的实现是返回null
     *
     * @param oldItemPosition The position of the item in the old list
     *                        在老数据源的postion
     * @param newItemPosition The position of the item in the new list
     *                        在新数据源的position
     * @return A payload object that represents the change between the two items.
     * 返回 一个 代表着新老item的改变内容的 payload对象，
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //实现这个方法 就能成为文艺青年中的文艺青年
        // 定向刷新中的部分更新
        // 效率最高
        //只是没有了ItemChange的白光一闪动画，（反正我也觉得不太重要）
        ClassifyDto.DataBean oldBean = olddatas.get(oldItemPosition);
        ClassifyDto.DataBean newBean = newDatas.get(newItemPosition);
        //这里就不用比较核心字段了,一定相等
        Bundle payload = new Bundle();
        boolean b = false;
        if (oldBean.getCat_id() != newBean.getCat_id()) {
            b = true;
        }

        if (oldBean.getGoods().size() != newBean.getGoods().size()) {
            b = true;
        } else {
            //todo 判断两个数组元素是否相同
            boolean flag1 = olddatas.containsAll(newDatas);
            boolean flag2 = newDatas.containsAll(olddatas);
            b = !(flag1 && flag2);
        }

        if (b) {
            payload.putInt("KEY_PERMISSION", newBean.getCat_id());
        }
//
        if (payload.size() == 0)//如果没有变化 就传空
            return null;
        return payload;//
    }
}
