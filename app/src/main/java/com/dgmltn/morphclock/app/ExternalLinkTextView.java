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

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dmelton on 10/12/14.
 */
public class ExternalLinkTextView extends TextView implements View.OnClickListener {

	private String mUrl;

	public ExternalLinkTextView(Context context) {
		super(context);
		init(context, null);
	}

	public ExternalLinkTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ExternalLinkTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ExternalLinkTextView);
		mUrl = attributes.getString(R.styleable.ExternalLinkTextView_url);
		attributes.recycle();

		setOnClickListener(this);
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	@Override
	public void onClick(View v) {
		if (!TextUtils.isEmpty(mUrl)) {
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(mUrl));
			getContext().startActivity(i);
		}
	}
}
