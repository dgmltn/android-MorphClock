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
