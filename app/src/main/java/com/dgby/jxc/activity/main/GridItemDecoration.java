package com.dgby.jxc.activity.main;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpacing;

    public GridItemDecoration(int spacing) {
        mSpacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int column = position % spanCount;

        outRect.left = mSpacing - column * mSpacing / spanCount;
        outRect.right = (column + 1) * mSpacing / spanCount;

        if (position < spanCount) {
            outRect.top = mSpacing;
        }
        outRect.bottom = mSpacing;
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            return gridLayoutManager.getSpanCount();
        }
        return 0;
    }
}
