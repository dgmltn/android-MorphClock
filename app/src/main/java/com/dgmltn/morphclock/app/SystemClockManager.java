/*
 * Copyright (C) 2014 Doug Melton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
