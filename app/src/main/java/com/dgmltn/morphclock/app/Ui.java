package com.dgmltn.morphclock.app;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

/**
 * Created by dmelton on 9/18/14.
 */
public class Ui {

	@SuppressWarnings("unchecked")
	public static <T extends View> T findView(Activity activity, int id) {
		return (T) activity.findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T findView(Fragment fragment, int id) {
		return (T) fragment.getView().findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T findView(View view, int id) {
		return (T) view.findViewById(id);
	}


}
