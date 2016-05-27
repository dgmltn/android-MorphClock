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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dgmltn.morphclock.app.sky.SkyView;
import com.dgmltn.morphclock.app.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final View content = LayoutInflater.from(this).inflate(R.layout.main_activity, null);
		setContentView(content);

		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider.setOnVisibilityChangeListener(
			new SystemUiHider.OnVisibilityChangeListener() {
				@Override
				@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
				public void onVisibilityChange(boolean visible) {
					if (visible && AUTO_HIDE) {
						// Schedule a hide().
						delayedHide(AUTO_HIDE_DELAY_MILLIS);
					}

					if (visible) {
						getActionBar().show();
					}
					else {
						getActionBar().hide();
					}
				}
			});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isInfoViewShowing()) {
					hideInfoView();
				}
				else if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				}
				else {
					mSystemUiHider.show();
				}
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		if (!isInfoViewShowing()) {
			delayedHide(100);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		ClockContainerLayout clock = Ui.findView(this, R.id.clock_container);
		clock.startTicking();
		SkyView sky = Ui.findView(this, R.id.sky);
		sky.startTicking();
	}

	@Override
	protected void onPause() {
		super.onPause();
		ClockContainerLayout clock = Ui.findView(this, R.id.clock_container);
		clock.stopTicking();
		SkyView sky = Ui.findView(this, R.id.sky);
		sky.stopTicking();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_info:
			if (isInfoViewShowing()) {
				hideInfoView();
			}
			else {
				showInfoView();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		if (isInfoViewShowing()) {
			hideInfoView();
			return;
		}
		super.onBackPressed();
	}

	private void showInfoView() {
		View view = Ui.findView(this, R.id.app_info_view);
		view.setVisibility(View.VISIBLE);
		ObjectAnimator showInfo = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
		ObjectAnimator scrollUp = ObjectAnimator.ofFloat(view, "translationY", 500, 0);

		View clock = Ui.findView(this, R.id.clock_container);
		ObjectAnimator hideClock = ObjectAnimator.ofFloat(clock, "alpha", 0f);

		AnimatorSet set = new AnimatorSet();
		set.playTogether(showInfo, scrollUp, hideClock);
		set.start();

		mHideHandler.removeCallbacks(mHideRunnable);
	}

	private boolean isInfoViewShowing() {
		View view = Ui.findView(this, R.id.app_info_view);
		return view.getVisibility() == View.VISIBLE;
	}

	private void hideInfoView() {
		final View view = Ui.findView(this, R.id.app_info_view);
		ObjectAnimator hideInfo = ObjectAnimator.ofFloat(view, "alpha", 0f);
		ObjectAnimator scrollDown = ObjectAnimator.ofFloat(view, "translationY", 0, 500);

		View clock = Ui.findView(this, R.id.clock_container);
		ObjectAnimator showClock = ObjectAnimator.ofFloat(clock, "alpha", 1f);

		AnimatorSet set = new AnimatorSet();
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.GONE);
			}
		});
		set.playTogether(hideInfo, scrollDown, showClock);
		set.start();

		delayedHide(AUTO_HIDE_DELAY_MILLIS);
	}

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
