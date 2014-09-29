package com.dgmltn.morphclock.app;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

/**
 * Created by dmelton on 8/8/14. Draws a gradient representing the sky at a particular sun
 * angle.
 */
public class SkyLayerDrawable extends LayerDrawable {

	private static Drawable[] sLayers = {
		SkyLinearGradientDrawable.POST_MIDNIGHT,
		SkyLinearGradientDrawable.EARLY_SUNRISE,
		SkyLinearGradientDrawable.SUNRISE,
		SkyLinearGradientDrawable.DAY,
		SkyLinearGradientDrawable.EARLY_SUNSET,
		SkyLinearGradientDrawable.DUSK_SUNSET,
		SkyLinearGradientDrawable.DUSK,
		SkyLinearGradientDrawable.PRE_MIDNIGHT,
	};

	private int[] mAlphas = new int[sLayers.length];

	public SkyLayerDrawable() {
		super(sLayers);
		for (int i = 0; i < mAlphas.length; i++) {
			mAlphas[i] = 255;
			getDrawable(i).setAlpha(mAlphas[i]);
			getDrawable(i).invalidateSelf();
		}
	}

	/**
	 * Set the angle of the sun. Let's define 0 degrees as midnight, 90 as sunrise, 180 as noon,
	 * 270 as sunset.
	 *
	 * @param sunAngle
	 */
	public void setSunAngle(float sunAngle) {
		boolean alphasChanged = false;

		for (int i = 0; i < sLayers.length; i++) {
			SkyLinearGradientDrawable d = (SkyLinearGradientDrawable) getDrawable(i);
			if (d.setAngle(sunAngle)) {
				alphasChanged = true;
			}
		}

		if (alphasChanged) {
			invalidateSelf();
		}
	}
}
