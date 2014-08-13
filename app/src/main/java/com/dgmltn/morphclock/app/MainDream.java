package com.dgmltn.morphclock.app;

import android.service.dreams.DreamService;

/**
 * Created by dmelton on 8/7/14.
 */
public class MainDream extends DreamService {

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();

		setContentView(R.layout.main_dream);

		setFullscreen(true);
	}

}
