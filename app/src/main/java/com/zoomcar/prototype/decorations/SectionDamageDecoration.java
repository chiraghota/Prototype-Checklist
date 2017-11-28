package com.zoomcar.prototype.decorations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoomcar.prototype.R;
import com.zoomcar.prototype.adapters.SectionDamageAdapter;

/**
 * Created by arunlakra on 27/11/17.
 */

public class SectionDamageDecoration extends RecyclerView.ItemDecoration {
    private final int mSpacingOffsetInPixels;
    private final SectionDamageAdapter mAdapter;

    private View mNewHeaderView;
    private View mOldHeaderView;

    public SectionDamageDecoration(Context context, @DimenRes int spacing, SectionDamageAdapter adapter) {
        this.mSpacingOffsetInPixels = context.getResources().getDimensionPixelOffset(spacing);
        this.mAdapter = adapter;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpacingOffsetInPixels;
        outRect.right = mSpacingOffsetInPixels;
        outRect.bottom = mSpacingOffsetInPixels;

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpacingOffsetInPixels;
        }

        final int position = parent.getChildAdapterPosition(view);
        if (mAdapter.getSectionHeader(position) != null) {
            outRect.top = 120;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (mNewHeaderView == null) {
            mNewHeaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_damage_header, parent, false);
            fixLayoutSize(mNewHeaderView, parent);
        }

        if (mOldHeaderView == null) {
            mOldHeaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_existing_damage_header, parent, false);
            fixLayoutSize(mOldHeaderView, parent);
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            String title = mAdapter.getSectionHeader(i);
            if (title != null) {
                c.save();
                if (title.equals(parent.getContext().getString(R.string.new_reported_damages))) {
                    c.translate(0, child.getTop() - mNewHeaderView.getHeight());
                    mNewHeaderView.draw(c);
                } else if (title.equals(parent.getContext().getString(R.string.existing_reported_damages))) {
                    c.translate(0, child.getTop() - mOldHeaderView.getHeight());
                    mOldHeaderView.draw(c);
                }
                c.restore();
            }
        }
    }

    private void fixLayoutSize(View view, ViewGroup parent) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(),
                View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(),
                View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(),
                view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(),
                view.getLayoutParams().height);

        view.measure(childWidth, childHeight);

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

}

