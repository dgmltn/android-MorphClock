package com.dgmltn.morphclock.app;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by dmelton on 8/8/14. Draws a gradient representing the sky at a particular sun
 * angle.
 */
public class SkyDrawable extends Drawable {

	private static class GradientValues {
		public int[] colors;
		public float[] positions;

		public GradientValues(int[] colors, float[] positions) {
			this.colors = colors;
			this.positions = positions;
		}
	}

	private static class SkyGradient {
		public GradientValues values;
		public float startAngle;
		public float fullAngle;

		public SkyGradient(GradientValues values, float startAngle, float fullAngle) {
			this.values = values;
			this.startAngle = startAngle;
			this.fullAngle = fullAngle;
		}

		private int mHeight;
		private LinearGradient mGradient;

		public LinearGradient getLinearGradient(int height) {
			if (mGradient == null || mHeight != height) {
				mHeight = height;
				mGradient = new LinearGradient(0, 0, 0, mHeight, values.colors, values.positions, Shader.TileMode.CLAMP);
			}
			return mGradient;
		}
	}

	private static final GradientValues G_EARLY_SUNRISE = new GradientValues(
		new int[] {
			0xff091B41, 0xff2D345E, 0xff3C4169, 0xff535479, 0xff666283, 0xff8C7F91, 0xffB39C98,
			0xffC6A48C, 0xffD9A96E, 0xffD4844E, 0xff934F44
		},
		new float[] {
			0f, 0.1282f, 0.2478f, 0.3789f, 0.4958f, 0.6184f, 0.7522f, 0.8245f, 0.8887f, 0.9443f, 1f
		}
	);

	private static final GradientValues G_SUNRISE = new GradientValues(
		new int[] {
			0xff8C9ABD, 0xff9EACCF, 0xffBBC1D9, 0xffC6C8DD, 0xffD1D0DE, 0xffD8D6E1, 0xffDCD3D8,
			0xffEDDED9, 0xffF6DFCD, 0xffFFDEBB, 0xffFED9AD, 0xffFFD193, 0xffFFB96B, 0xffFFB55E,
			0xffFEAD51, 0xffFE9E48, 0xffFE8043
		},
		new float[] {
			0f, 0.0725f, 0.259f, 0.3315f, 0.3872f, 0.4429f, 0.4985f, 0.5684f, 0.635f, 0.6963f,
			0.7522f, 0.8135f, 0.8801f, 0.9109f, 0.9387f, 0.9692f, 1f
		}
	);

	private static final GradientValues G_DAY = new GradientValues(
		new int[] {
			0xff2E5A89, 0xff37699A, 0xff487CAE, 0xff568ABC, 0xff669CCB, 0xff96B9D9, 0xffA6C5D9
		},
		new float[] {0f, 0.2478f, 0.4958f, 0.6296f, 0.7522f, 0.8774f, 1f}
	);

	private static final GradientValues G_EARLY_SUNSET = new GradientValues(
		new int[] {
			0xff747CC5, 0xff968BC5, 0xffB294B0, 0xffD69076, 0xffFA963E, 0xffE96D31, 0xff6A1C2A
		},
		new float[] {0f, 0.2478f, 0.4958f, 0.7019f, 0.8022f, 0.8801f, 1f}
	);

	private static final GradientValues G_DUSK_SUNSET = new GradientValues(
		new int[] {
			0xff193259, 0xff1C3C66, 0xff1E4471, 0xff21507E, 0xff235987, 0xff25618E, 0xff276693,
			0xff29719C, 0xff2B7AA3, 0xff328FB1, 0xff6CACCA, 0xffB0D0E3, 0xffE3EEF5, 0xffECF4F8,
			0xffFAF9F2, 0xffF8F4E5, 0xffF1E9CB, 0xffDFCD87, 0xffD5BC60, 0xffBB9250, 0xff8E735F
		},
		new float[] {
			0f, 0.1365f, 0.2478f, 0.3733f, 0.4485f, 0.4958f, 0.5293f, 0.5732f, 0.6045f, 0.6602f,
			0.7048f, 0.7522f, 0.8162f, 0.844f, 0.897f, 0.9192f, 0.9414f, 0.9553f, 0.9692f, 0.9888f,
			1f
		}
	);

	private static final GradientValues G_DUSK = new GradientValues(
		new int[] {0xff0A1C4E, 0xff132660, 0xff4E67C3, 0xff8CAFFF, 0xffBAC0F0, 0xffF4EEF2},
		new float[] {0f, .02478f, 0.4958f, 0.7522f, 0.8831f, 1f}
	);

	private static final GradientValues G_NIGHT = new GradientValues(
		new int[] {0xff2a3a54, 0xff32425b, 0xff33435B, 0xff364761, 0xff3D4E68, 0xff42526B},
		new float[] {0f, 0.2424f, 0.3704f, 0.4958f, .7522f, 1f}
	);

	private static ArrayList<SkyGradient> sGradients = new ArrayList<SkyGradient>();

	static {
		sGradients.add(new SkyGradient(G_NIGHT, 0f, 0f));
		sGradients.add(new SkyGradient(G_EARLY_SUNRISE, 72f, 84f));
		sGradients.add(new SkyGradient(G_SUNRISE, 78f, 90f));
		sGradients.add(new SkyGradient(G_DAY, 84f, 96f));
		sGradients.add(new SkyGradient(G_EARLY_SUNSET, 267f, 273f));
		sGradients.add(new SkyGradient(G_DUSK_SUNSET, 273f, 279f));
		sGradients.add(new SkyGradient(G_DUSK, 279f, 285f));
		sGradients.add(new SkyGradient(G_NIGHT, 285f, 291f));
	}

	private int[] mAlphas = new int[sGradients.size()];
	private boolean mAlphasChanged = true;
	private Paint mPaint = new Paint();

	private float mSunAngle;

	/**
	 * Set the angle of the sun. Let's define 0 degrees as midnight, 90 as sunrise, 180 as noon,
	 * 270 as sunset.
	 *
	 * @param sunAngle
	 */
	public void setSunAngle(float sunAngle) {
		mSunAngle = sunAngle;

		for (int i = 0; i < sGradients.size(); i++) {
			SkyGradient gradient = sGradients.get(i);
			float alpha = (mSunAngle - gradient.startAngle) / (gradient.fullAngle - gradient.startAngle);
			int iAlpha = Math.max(0, Math.min(255, (int) (255 * alpha)));
			if (mAlphas[i] != iAlpha) {
				mAlphasChanged = true;
				mAlphas[i] = iAlpha;
			}
		}

		if (mAlphasChanged) {
			invalidateSelf();
		}
	}

	@Override
	public void draw(Canvas canvas) {
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		int max = sGradients.size();
		for (int i = 0; i < max; i++) {
			if (mAlphas[i] > 0 && (i == max - 1 || mAlphas[i + 1] != 255)) {
				SkyGradient gradient = sGradients.get(i);
				mPaint.setShader(gradient.getLinearGradient(h));
				mPaint.setAlpha(mAlphas[i]);
				canvas.drawRect(0, 0, w, h, mPaint);
			}
		}
	}

	@Override
	public void setAlpha(int i) {
		//ignore
	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {
		//ignore
	}

	@Override
	public int getOpacity() {
		return 0;
	}
}
