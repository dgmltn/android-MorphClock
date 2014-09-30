package com.dgmltn.morphclock.app;

import java.util.GregorianCalendar;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.e175.klaus.solarpositioning.AzimuthZenithAngle;
import net.e175.klaus.solarpositioning.SPA;

/**
 * Created by dmelton on 8/10/14.
 */
public class SkyView extends View implements SystemClockManager.SystemClockListener {

	private static final String TAG = SkyView.class.getSimpleName();

	private SkyLayerDrawable mBackground;
	private SystemClockManager mSystemClockManager;

	GregorianCalendar mCalendar;

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
		mBackground = new SkyLayerDrawable();
		setBackground(mBackground);
		mSystemClockManager = new SystemClockManager(this, 1000);
		mCalendar = new GregorianCalendar();
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
		mCalendar.setTimeInMillis(System.currentTimeMillis() + (int)(DateUtils.HOUR_IN_MILLIS));
		AzimuthZenithAngle aza = SPA.calculateSolarPosition(mCalendar, 33, -117, 0, 67, 0, 0);
		float zenithAngle = (float)aza.getZenithAngle();
		float angle = mCalendar.getTime().getHours() < 12 ? 180 - zenithAngle : 180 + zenithAngle;
			Log.e(TAG, "DOUG: angle = " + angle);
		mBackground.setSunAngle(angle);
	}
}
