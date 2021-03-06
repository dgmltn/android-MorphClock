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

package com.dgmltn.morphclock.app.sky;

import java.util.GregorianCalendar;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;

import com.dgmltn.morphclock.app.SystemClockManager;
import com.dgmltn.morphclock.app.Util;

import net.e175.klaus.solarpositioning.AzimuthZenithAngle;
import net.e175.klaus.solarpositioning.SPA;

/**
 * Created by dmelton on 8/10/14.
 */
public class SkyView extends View implements SystemClockManager.SystemClockListener, LocationListener {

	private static final String TAG = SkyView.class.getSimpleName();

	private static final String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

	private SkyLayerDrawable mBackground;
	private SystemClockManager mSystemClockManager;

	private LocationManager mLocationManager;
	private Location mLocation;

	GregorianCalendar mCalendar;
	private float mSunAngle;

	public SkyView(Context context) {
		super(context);
		init(context);
	}

	public SkyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SkyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mBackground = new SkyLayerDrawable();
		setBackground(mBackground);
		mSystemClockManager = new SystemClockManager(this, 1000);
		mCalendar = new GregorianCalendar();

		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		mLocation = mLocationManager.getLastKnownLocation(LOCATION_PROVIDER);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		startTicking();
	}

	@Override
	protected void onDetachedFromWindow() {
		stopTicking();
		super.onDetachedFromWindow();
	}

	public void startTicking() {
		mSystemClockManager.start();
		mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, 1000, 1000, this);
	}

	public void stopTicking() {
		mSystemClockManager.stop();
		mLocationManager.removeUpdates(this);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// SystemClockManager.SystemClockListener
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onTimeChanged(long time) {
		mCalendar.setTimeInMillis(System.currentTimeMillis() + (int) (DateUtils.HOUR_IN_MILLIS));

		// If we don't know the user's location, just do some generic angle estimation
		if (mLocation == null || (mLocation.getLatitude() == 0 && mLocation.getLongitude() == 0)) {
			long period = DateUtils.DAY_IN_MILLIS;
			long millis = Util.millisSinceMidnight(time);
			mSunAngle = 360f * (millis % period) / period;
		}

		// If we know the user's location, do some special sun position math:
		else {
			AzimuthZenithAngle aza = SPA.calculateSolarPosition(mCalendar,
				mLocation.getLatitude(), mLocation.getLongitude(), mLocation.getAltitude(),
				67, 0, 0);
			float zenithAngle = (float) aza.getZenithAngle();
			mSunAngle = mCalendar.getTime().getHours() < 12 ? 180 - zenithAngle : 180 + zenithAngle;
		}

		mBackground.setSunAngle(mSunAngle);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// LocationListener
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onLocationChanged(Location location) {
		mLocation = location;
		mLocationManager.removeUpdates(this);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}
