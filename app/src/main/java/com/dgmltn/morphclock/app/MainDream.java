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

import android.os.Handler;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.dgmltn.morphclock.app.util.SystemUiHider;

/**
 * Created by dmelton on 8/7/14.
 */
public class MainDream extends DreamService {

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int SYSTEM_UI_VISIBILITY_FLAGS
		= View.SYSTEM_UI_FLAG_LOW_PROFILE
		| View.SYSTEM_UI_FLAG_FULLSCREEN
		| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		;

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();

		View content = LayoutInflater.from(this).inflate(R.layout.main_dream, null);
		setContentView(content);

		content.setSystemUiVisibility(SYSTEM_UI_VISIBILITY_FLAGS);
		content.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				if (visibility == 0) {
					finish();
				}
			}
		});
	}
}
