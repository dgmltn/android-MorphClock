package com.dgmltn.morphclock.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by dmelton on 10/11/14.
 */
public class AppInfoView extends BoundedFrameLayout {

	public AppInfoView(Context context) {
		super(context);
		init(context, null);
	}

	public AppInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public AppInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.app_info_view, this, true);

		linkify(R.id.text_twitter_link_dgmltn, R.id.text_twitter_link_davyleggieri,
			R.id.text_gplus_link_davyleggieri, R.id.text_mit_license_1,
			R.id.text_mit_license_2, R.id.text_web_link_klausbrunner);

		Ui.findView(this, R.id.daydream_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openDaydreamSettings();
			}
		});
	}

	private void linkify(int... ids) {
		MovementMethod lmm = LinkMovementMethod.getInstance();
		for (int id : ids) {
			TextView textview = Ui.findView(this, id);
			textview.setMovementMethod(LinkMovementMethod.getInstance());
		}
	}

	private void openDaydreamSettings() {
		Intent intent;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			intent = new Intent(Settings.ACTION_DREAM_SETTINGS);
		} else {
			intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
		}
		getContext().startActivity(intent);
	}
}
