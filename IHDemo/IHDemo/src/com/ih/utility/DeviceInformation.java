package com.ih.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ih.demo.R;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceInformation.
 */
public class DeviceInformation {
	
	/**
	 * Sets the device density.
	 *
	 * @param windowManager the window manager
	 * @param context the context
	 */
	public static void setDeviceDensity(WindowManager windowManager,
			Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);

		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_device_info),
				Context.MODE_PRIVATE);
		preferences
				.edit()
				.putFloat(context.getString(R.string.shared_pref_key_density),
						metrics.density).commit();
	}

	/**
	 * Gets the device density.
	 *
	 * @param context the context
	 * @return the device density
	 */
	public static float getDeviceDensity(Context context) {
		float density = 1f;
		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_device_info),
				Context.MODE_PRIVATE);
		density = preferences.getFloat(
				context.getString(R.string.shared_pref_key_density), 1f);

		return density;
	}

	/**
	 * Sets the device dimensions.
	 *
	 * @param windowManager the window manager
	 * @param context the context
	 */
	public static void setDeviceDimensions(WindowManager windowManager,
			Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);

		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_device_info),
				Context.MODE_PRIVATE);
		preferences
				.edit()
				.putInt(context
						.getString(R.string.shared_pref_key_dimen_height),
						metrics.heightPixels).commit();
		preferences
				.edit()
				.putInt(context.getString(R.string.shared_pref_key_dimen_width),
						metrics.widthPixels).commit();
	}

	/**
	 * Gets the device dimensions.
	 *
	 * @param context the context
	 * @return the device dimensions
	 */
	public static int[] getDeviceDimensions(Context context) {
		int[] dimen = new int[2];
		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_device_info),
				Context.MODE_PRIVATE);
		dimen[0] = preferences.getInt(
				context.getString(R.string.shared_pref_key_dimen_height), 0);

		dimen[1] = preferences.getInt(
				context.getString(R.string.shared_pref_key_dimen_width), 0);

		return dimen;
	}
}
