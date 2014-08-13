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
