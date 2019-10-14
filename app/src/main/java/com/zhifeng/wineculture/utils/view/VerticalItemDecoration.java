package com.zhifeng.wineculture.utils.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {
    private int padding;

    public VerticalItemDecoration(int padding){
        this.padding = padding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left=padding/4;
        if (parent.getChildAdapterPosition(view)!=0){
            outRect.top=padding;
        }
        outRect.right=padding/4;
        outRect.bottom=padding;
    }
}

