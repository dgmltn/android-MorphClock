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

/**
 * Created by dmelton on 8/13/14.
 */
public class SkyLinearGradientDrawable extends LinearGradientDrawable {

	private float mStartAngle;
	private float mFullAngle;
	private int mAlpha;

	public SkyLinearGradientDrawable(int[] colors, float[] positions, float angle) {
		super(colors, positions);
		mFullAngle = angle;
		mStartAngle = angle - 12;
	}

	/**
	 * Sets the angle above the horizon of the sun. 90 = sunrise, 180 = sunset.
	 *
	 * @param angle
	 * @return
	 */
	public boolean setAngle(float angle) {
		int alpha = 0;
		if (angle >= mFullAngle) {
			alpha = 255;
		}
		else if (angle >= mStartAngle) {
			alpha = (int) (255 * (angle - mStartAngle) / (mFullAngle - mStartAngle));
		}

		if (alpha != mAlpha) {
			setAlpha(alpha);
			setVisible(alpha > 0, false);
			return true;
		}

		return false;
	}

	@Override
	public void setAlpha(int alpha) {
		super.setAlpha(alpha);
		mAlpha = alpha;
	}

	// PREDEFINED SkyLinearGradientDrawable's

	public static final SkyLinearGradientDrawable POST_MIDNIGHT = new SkyLinearGradientDrawable(
		new int[] {0xff2a3a54, 0xff32425b, 0xff33435B, 0xff364761, 0xff3D4E68, 0xff42526B},
		new float[] {0f, 0.2424f, 0.3704f, 0.4958f, .7522f, 1f},
		0f
	);

	public static final SkyLinearGradientDrawable EARLY_SUNRISE = new SkyLinearGradientDrawable(
		new int[] {
			0xff091B41, 0xff2D345E, 0xff3C4169, 0xff535479, 0xff666283, 0xff8C7F91, 0xffB39C98,
			0xffC6A48C, 0xffD9A96E, 0xffD4844E, 0xff934F44
		},
		new float[] {
			0f, 0.1282f, 0.2478f, 0.3789f, 0.4958f, 0.6184f, 0.7522f, 0.8245f, 0.8887f, 0.9443f, 1f
		},
		84f
	);

	public static final SkyLinearGradientDrawable SUNRISE = new SkyLinearGradientDrawable(
		new int[] {
			0xff8C9ABD, 0xff9EACCF, 0xffBBC1D9, 0xffC6C8DD, 0xffD1D0DE, 0xffD8D6E1, 0xffDCD3D8,
			0xffEDDED9, 0xffF6DFCD, 0xffFFDEBB, 0xffFED9AD, 0xffFFD193, 0xffFFB96B, 0xffFFB55E,
			0xffFEAD51, 0xffFE9E48, 0xffFE8043
		},
		new float[] {
			0f, 0.0725f, 0.259f, 0.3315f, 0.3872f, 0.4429f, 0.4985f, 0.5684f, 0.635f, 0.6963f,
			0.7522f, 0.8135f, 0.8801f, 0.9109f, 0.9387f, 0.9692f, 1f
		},
		90f
	);

	public static final SkyLinearGradientDrawable DAY = new SkyLinearGradientDrawable(
		new int[] {
			0xff2E5A89, 0xff37699A, 0xff487CAE, 0xff568ABC, 0xff669CCB, 0xff96B9D9, 0xffA6C5D9
		},
		new float[] {0f, 0.2478f, 0.4958f, 0.6296f, 0.7522f, 0.8774f, 1f},
		96f
	);

	public static final SkyLinearGradientDrawable EARLY_SUNSET = new SkyLinearGradientDrawable(
		new int[] {
			0xff747CC5, 0xff968BC5, 0xffB294B0, 0xffD69076, 0xffFA963E, 0xffE96D31, 0xff6A1C2A
		},
		new float[] {0f, 0.2478f, 0.4958f, 0.7019f, 0.8022f, 0.8801f, 1f},
		273f
	);

	public static final SkyLinearGradientDrawable DUSK_SUNSET = new SkyLinearGradientDrawable(
		new int[] {
			0xff193259, 0xff1C3C66, 0xff1E4471, 0xff21507E, 0xff235987, 0xff25618E, 0xff276693,
			0xff29719C, 0xff2B7AA3, 0xff328FB1, 0xff6CACCA, 0xffB0D0E3, 0xffE3EEF5, 0xffECF4F8,
			0xffFAF9F2, 0xffF8F4E5, 0xffF1E9CB, 0xffDFCD87, 0xffD5BC60, 0xffBB9250, 0xff8E735F
		},
		new float[] {
			0f, 0.1365f, 0.2478f, 0.3733f, 0.4485f, 0.4958f, 0.5293f, 0.5732f, 0.6045f, 0.6602f,
			0.7048f, 0.7522f, 0.8162f, 0.844f, 0.897f, 0.9192f, 0.9414f, 0.9553f, 0.9692f, 0.9888f,
			1f
		},
		279f
	);

	public static final SkyLinearGradientDrawable DUSK = new SkyLinearGradientDrawable(
		new int[] {0xff0A1C4E, 0xff132660, 0xff4E67C3, 0xff8CAFFF, 0xffBAC0F0, 0xffF4EEF2},
		new float[] {0f, .02478f, 0.4958f, 0.7522f, 0.8831f, 1f},
		285f
	);

	public static final SkyLinearGradientDrawable PRE_MIDNIGHT = new SkyLinearGradientDrawable(
		new int[] {0xff2a3a54, 0xff32425b, 0xff33435B, 0xff364761, 0xff3D4E68, 0xff42526B},
		new float[] {0f, 0.2424f, 0.3704f, 0.4958f, .7522f, 1f},
		291f
	);

}
