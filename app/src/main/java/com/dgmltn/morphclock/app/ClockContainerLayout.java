package com.dgmltn.morphclock.app;

import java.text.SimpleDateFormat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
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

	private static final String TAG = ClockContainerLayout.class.getSimpleName();

	private static SimpleDateFormat sHoursMinutesFormat = new SimpleDateFormat("H:mm");
	private static SimpleDateFormat sSecondsFormat = new SimpleDateFormat("ss");
	private static SimpleDateFormat sDateFormat = new SimpleDateFormat("c, MMMM d");

	private DigitalClockView mHoursMinutesView;
	private DigitalClockView mSecondsView;
	private TextView mDateView;
	private SystemClockManager mSystemClockManager;
	private View mClockContainer;

	long mHhmm = 0;
	long mSs = 0;

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

		mRelativePositionX = 0.5f;
		mRelativePositionY = 0.5f;
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
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		Point screen = Util.getScreenSize(getContext());
		mScreenWidth = screen.x;
		mScreenHeight = screen.y;

		mWidth = w;
		mHeight = h;

		adjustRelativePositions();
	}

	private float mWidth, mHeight, mScreenWidth, mScreenHeight, mRelativePositionX, mRelativePositionY;

	public void setRelativePositionX(float x) {
		mRelativePositionX = x;
		adjustRelativePositions();
	}

	public float getRelativePositionX() {
		return mRelativePositionX;
	}

	public void setRelativePositionY(float y) {
		mRelativePositionY = y;
		adjustRelativePositions();
	}

	public float getRelativePositionY() {
		return mRelativePositionY;
	}

	private void adjustRelativePositions() {
		setTranslationX(mRelativePositionX * (mScreenWidth - mWidth));
		setTranslationY(mRelativePositionY * (mScreenHeight - mHeight));
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mSystemClockManager.stop();
	}

	@Override
	public void onTimeChanged(long time) {

		// Hour/minute
		if (time / DateUtils.MINUTE_IN_MILLIS != mHhmm) {
			AnimatorSet set = new AnimatorSet();
			set.play(ObjectAnimator.ofFloat(this, "relativePositionX", (float) Math.random()))
				.with(ObjectAnimator.ofFloat(this, "relativePositionY", (float) Math.random()));
			set.setDuration(mHoursMinutesView.getMorphingDuration()).start();

			// Useful when a long morphing duration is set otherwise we never see the destination number as it's always morphing
			String hhmm = sHoursMinutesFormat.format(time);
			mHoursMinutesView.setTime(hhmm, !mHoursMinutesView.isMorphingAnimationRunning());

			mHhmm = time / DateUtils.MINUTE_IN_MILLIS;
		}

		// Second
		if (time / DateUtils.SECOND_IN_MILLIS != mSs) {
			mSs = time / DateUtils.SECOND_IN_MILLIS;
			String ss = sSecondsFormat.format(time);

			mSecondsView.setTime(ss, !mSecondsView.isMorphingAnimationRunning());
		}

		// Date
		mDateView.setText(sDateFormat.format(time));
	}

}
