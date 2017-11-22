package com.zoomcar.prototype;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DamageDecoration extends RecyclerView.ItemDecoration {
    private final int mSpacingOffsetInPixels;

    public DamageDecoration(Context context, @DimenRes int spacing) {
        this.mSpacingOffsetInPixels = context.getResources().getDimensionPixelOffset(spacing);
    }

    /**
     * Only the top-most item has top margin offset, including left, right and bottom margin offsets.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpacingOffsetInPixels;
        outRect.right = mSpacingOffsetInPixels;
        outRect.bottom = mSpacingOffsetInPixels;

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpacingOffsetInPixels;
        }
    }
}
