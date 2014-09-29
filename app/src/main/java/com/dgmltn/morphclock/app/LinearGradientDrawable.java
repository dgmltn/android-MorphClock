package com.dgmltn.morphclock.app;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;

/**
 * Created by dmelton on 8/13/14.
 */
public class LinearGradientDrawable extends PaintDrawable {

	private int[] mColors;
	private float[] mPositions;

	ShapeDrawable.ShaderFactory mShaderFactory = new ShapeDrawable.ShaderFactory() {
		@Override
		public Shader resize(int width, int height) {
			return new LinearGradient(0, 0, 0, height,
				mColors, mPositions, Shader.TileMode.CLAMP);
		}
	};

	public LinearGradientDrawable(int[] colors, float[] positions) {
		mColors = colors;
		mPositions = positions;

		setShape(new RectShape());
		setShaderFactory(mShaderFactory);
	}
}
