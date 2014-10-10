package com.dgmltn.morphclock.app;

import android.animation.TimeAnimator;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;


public class SystemClockManager implements TimeAnimator.TimeListener {

	public interface SystemClockListener {
		void onTimeChanged(long time);
	}

	private TimeAnimator mAnimator;
	private long mResolution;

	private long mLast = 0;

	private final SystemClockListener mListener;

	public SystemClockManager(SystemClockListener listener) {
		this(listener, DateUtils.SECOND_IN_MILLIS);
	}

	public SystemClockManager(SystemClockListener listener, long resolution) {
		mListener = listener;
		mResolution = resolution;

		mAnimator = new TimeAnimator();
		mAnimator.setTimeListener(this);
	}

	public void start() {
		if (!mAnimator.isStarted()) {
			mAnimator.start();
		}
	}

	public void stop() {
		if (mAnimator.isStarted()) {
			mAnimator.cancel();
		}
	}

	@Override
	public void onTimeUpdate(TimeAnimator timeAnimator, long l, long l2) {
		long now = l / mResolution;
		if (now != mLast) {
			mLast = now;
			mListener.onTimeChanged(System.currentTimeMillis());
		}
	}

}