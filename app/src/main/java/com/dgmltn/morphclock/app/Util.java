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

import java.util.Calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Created by dmelton on 8/7/14.
 */
public class Util {

	/**
	 * Returns the screen width/height, as a Point (x is width, y is height).
	 */
	@TargetApi(13)
	@SuppressWarnings("deprecation")
	public static Point getScreenSize(Context context) {
		Point size = new Point();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		size.x = metrics.widthPixels;
		size.y = metrics.heightPixels;
		return size;
	}

	private static final Calendar sCal = Calendar.getInstance();

	public static long millisSinceMidnight(long now) {
		sCal.setTimeInMillis(now);
		sCal.set(Calendar.HOUR_OF_DAY, 0);
		sCal.set(Calendar.MINUTE, 0);
		sCal.set(Calendar.SECOND, 0);
		sCal.set(Calendar.MILLISECOND, 0);
		return now - sCal.getTimeInMillis();
	}

}
