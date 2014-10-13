package com.dgmltn.morphclock.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by dmelton on 10/13/14.
 * http://stackoverflow.com/questions/5875877/setting-a-maximum-width-on-a-viewgroup
 */
public class BoundedFrameLayout extends FrameLayout {

	private int mBoundedWidth;
	private int mBoundedHeight;

	public BoundedFrameLayout(Context context) {
		super(context);
		init(context, null);
	}

	public BoundedFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public BoundedFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedView);
		mBoundedWidth = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_width, 0);
		mBoundedHeight = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_height, 0);
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Adjust width as necessary
		int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		if(mBoundedWidth > 0 && mBoundedWidth < measuredWidth) {
			int measureMode = MeasureSpec.getMode(widthMeasureSpec);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(mBoundedWidth, measureMode);
		}
		// Adjust height as necessary
		int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
		if(mBoundedHeight > 0 && mBoundedHeight < measuredHeight) {
			int measureMode = MeasureSpec.getMode(heightMeasureSpec);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(mBoundedHeight, measureMode);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}