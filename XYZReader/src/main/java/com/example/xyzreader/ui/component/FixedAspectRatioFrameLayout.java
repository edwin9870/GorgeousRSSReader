package com.example.xyzreader.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.xyzreader.R;

/**
 * Created by Edwin Ramirez Ventura on 6/18/2017.
 */

public class FixedAspectRatioFrameLayout extends FrameLayout {

    private int mAspectRatioWidth;
    private int mAspectRatioHeight;

    public FixedAspectRatioFrameLayout(@NonNull Context context) {
        super(context);
    }

    public FixedAspectRatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FixedAspectRatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                       @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight = originalWidth * mAspectRatioHeight / mAspectRatioWidth;

        int finalWidth, finalHeight;

        if (calculatedHeight > originalHeight) {
            finalWidth = originalHeight * mAspectRatioWidth / mAspectRatioHeight;
            finalHeight = originalHeight;
        } else {
            finalWidth = originalWidth;
            finalHeight = calculatedHeight;
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable
                .FixedAspectRatioFrameLayout);
        try {
            mAspectRatioWidth = styledAttributes.getInt(R.styleable
                    .FixedAspectRatioFrameLayout_aspectRatioWidth, 4);
            mAspectRatioHeight = styledAttributes.getInt(R.styleable
                    .FixedAspectRatioFrameLayout_aspectRatioHeight, 3);
        } finally {
            styledAttributes.recycle();
        }

    }
}
