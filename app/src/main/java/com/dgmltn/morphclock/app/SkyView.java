package com.dgmltn.morphclock.app;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dmelton on 8/10/14.
 */
public class SkyView extends View implements SystemClockManager.SystemClockListener {

	private SkyDrawable mBackground;
	private SystemClockManager mSystemClockManager;

	public SkyView(Context context) {
		super(context);
		init();
	}

	public SkyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SkyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mBackground = new SkyDrawable();
		setBackground(mBackground);
		mSystemClockManager = new SystemClockManager(this, 1);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mSystemClockManager.start();
	}

	@Override
	protected void onDetachedFromWindow() {
		mSystemClockManager.stop();
		super.onDetachedFromWindow();
	}

	@Override
	public void onTimeChanged(long time) {
		long period = DateUtils.MINUTE_IN_MILLIS;
		long millis = Util.millisSinceMidnight(time);
		float angle = 360f * (millis % period) / period;
		mBackground.setSunAngle(angle);
	}
}
