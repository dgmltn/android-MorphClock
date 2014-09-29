package com.dgmltn.morphclock.app;

import java.util.TimeZone;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.luckycatlabs.sunrisesunset.calculator.SolarEventCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

/**
 * Created by dmelton on 8/10/14.
 */
public class SkyView extends View implements SystemClockManager.SystemClockListener {

	private static final String TAG = SkyView.class.getSimpleName();

	private SkyLayerDrawable mBackground;
	private SystemClockManager mSystemClockManager;

	// Used for calculating the sun's angle
	private SolarEventCalculator mCalculator;

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
		mSystemClockManager = new SystemClockManager(this, 1);

		Location location = new Location(33, -117);
		TimeZone timeZone = TimeZone.getDefault();
		mCalculator = new SolarEventCalculator(location, timeZone);
		Log.e(TAG, "DOUG: location = " + location + ", timezone = " + timeZone);
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
		long period = DateUtils.DAY_IN_MILLIS;
		long millis = Util.millisSinceMidnight(time);
		float angle = 360f * (millis % period) / period;
		mBackground.setSunAngle(angle);
	}
}
