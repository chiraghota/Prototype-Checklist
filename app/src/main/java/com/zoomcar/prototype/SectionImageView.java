package com.zoomcar.prototype;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SectionImageView extends AppCompatImageView {
    private int mSectionId = -1;

    private Bitmap mGreenIcon, mRedIcon;
    private int mWidth, mHeight;
    private Rect mRect;
    private Database mDatabase;
    private int[] mQuestionIds;
    private float mDensity;
    private Paint mPaint;
    private int mOriginalDotSize;

    public SectionImageView(Context context) {
        super(context);
        init();
    }

    public SectionImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SectionImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mDatabase = Database.getInstance();
        this.mGreenIcon = getBitmapFromVectorDrawable(getContext(), R.drawable.ic_add_circle_green_28dp);
        this.mRedIcon = getBitmapFromVectorDrawable(getContext(), R.drawable.ic_add_circle_red_28dp);
        this.mWidth = mGreenIcon.getWidth();
        this.mHeight = mGreenIcon.getHeight();
        this.mRect = new Rect();
        this.mDensity = getResources().getDisplayMetrics().density;
        this.mOriginalDotSize = getResources().getDimensionPixelSize(R.dimen.section_dot_size);
        this.mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.white));
    }

    public void setSectionId(int sectionId) {
        this.mSectionId = sectionId;
        this.mQuestionIds = mDatabase.getSectionMap().get(mSectionId).questionIds;

        invalidate();
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mSectionId != -1) {
            for (int questionId : mQuestionIds) {
                float[] coordinates = mDatabase.getQuestionMap().get(questionId).coordinates;
                mRect.top = (int) (coordinates[1] * mDensity) - (mHeight - mOriginalDotSize) / 2;
                mRect.left = (int) (coordinates[0] * mDensity) - (mWidth - mOriginalDotSize) / 2;
                mRect.right = mRect.left + mWidth;
                mRect.bottom = mRect.top + mHeight;
                canvas.drawCircle(mRect.left + mWidth / 2, mRect.top + mHeight / 2, mWidth / 2, mPaint);

                if (mDatabase.getQuestionToDamageMap().containsKey(questionId)) {
                    canvas.drawBitmap(mRedIcon, null, mRect, null);
                } else {
                    canvas.drawBitmap(mGreenIcon, null, mRect, null);
                }
            }
        }
    }
}
