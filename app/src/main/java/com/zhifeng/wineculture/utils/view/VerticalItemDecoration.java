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
        if (parent.getChildAdapterPosition(view)!=0){
            outRect.top=padding;
        }
        outRect.bottom=padding;
    }
}

