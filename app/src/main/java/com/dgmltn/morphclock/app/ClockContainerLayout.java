package com.dgmltn.morphclock.app;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bydavy.morpher.DigitalClockView;
import com.bydavy.morpher.font.DFont;

/**
 * Created by dmelton on 8/8/14.
 */
public class ClockContainerLayout extends LinearLayout implements SystemClockManager.SystemClockListener {

	private static SimpleDateFormat sHoursMinutesFormat = new SimpleDateFormat("H:mm");
	private static SimpleDateFormat sSecondsFormat = new SimpleDateFormat("ss");
	private static SimpleDateFormat sDateFormat = new SimpleDateFormat("c, MMMM d");

	private DigitalClockView mHoursMinutesView;
	private DigitalClockView mSecondsView;
	private TextView mDateView;
	private SystemClockManager mSystemClockManager;
	private View mClockContainer;

	String mLastTime = "";

	public ClockContainerLayout(Context context) {
		super(context);
		init(context);
	}

	public ClockContainerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ClockContainerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setOrientation(VERTICAL);

		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.widget_clock_container, this);

		// Setup the clock ticker
		mSystemClockManager = new SystemClockManager(this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		// Set up the clock
		mClockContainer = findViewById(R.id.clock_container);
		mHoursMinutesView = (DigitalClockView) findViewById(R.id.hours_minutes);
		mSecondsView = (DigitalClockView) findViewById(R.id.seconds);
		mDateView = (TextView) findViewById(R.id.date);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mSystemClockManager.start();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mSystemClockManager.stop();
	}

	@Override
	public void onTimeChanged(long time) {
		String thisTime = sHoursMinutesFormat.format(time);

		// Hour/minute changed, move the clock around the screen a bit
		if (!thisTime.equals(mLastTime) && mClockContainer.getWidth() > 0 && mClockContainer.getHeight() > 0) {
			mLastTime = thisTime;

			Point screen = Util.getScreenSize(getContext());

			int clockWidth = mClockContainer.getWidth();
			int screenWidth = screen.x;
			int clockHeight = mClockContainer.getHeight();
			int screenHeight = screen.y;

			double translateX = 0.9f * (Math.random() * (screenWidth - clockWidth) - this.getLeft());
			double translateY = 0.9f * (Math.random() * (screenHeight - clockHeight) - this.getTop());

			this.animate()
				.translationX((float)translateX)
				.translationY((float)translateY)
				.setDuration(mHoursMinutesView.getMorphingDuration())
				.start();
		}

		// Useful when a long morphing duration is set otherwise we never see the destination number as it's always morphing
		mHoursMinutesView.setTime(thisTime, false /*!mHoursMinutesView.isMorphingAnimationRunning()*/);

		mSecondsView.setTime(sSecondsFormat.format(time), false /*!mSecondsView.isMorphingAnimationRunning()*/);

		mDateView.setText(sDateFormat.format(time));
	}

}
