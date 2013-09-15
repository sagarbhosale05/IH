package com.ih;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author pragati.deshmukh The class is responsible for storing ad retrieving
 *         if the device build sdk is >= 4. This is required because in the
 *         application, if the version is ice cream sandwich or more the
 *         edittext background goes black because of holo theme, then the font
 *         color for edittext should be light grey. When the version is below
 *         ice cream sandwich, then the font color should be dark grey.
 */
public class PreferencesManager {
	private static final String PREF_NAME = "pref";

	/**
	 * @param context
	 * @param deviceID
	 *            The method puts boolean into {@link SharedPreferences} The
	 *            boolean is true if the device sdk version is >=4 false
	 *            otherwise
	 */
	public static void setIsIceCreamSandWich(Context context, Boolean deviceID) {
		if (context != null) {
			SharedPreferences.Editor editor = context.getSharedPreferences(
					PREF_NAME, Context.MODE_PRIVATE).edit();
			editor.putBoolean("deviceV", deviceID);
			editor.commit();
		}
	}

	/**
	 * @param context
	 * @return returns true if the device sdk version is >=4 false otherwise
	 */
	public static Boolean getIsIceCreamSandWich(Context context) {
		if (context != null) {
			SharedPreferences prefs = context.getSharedPreferences(PREF_NAME,
					Context.MODE_PRIVATE);
			return prefs.getBoolean("deviceV", false);
		}
		return null;
	}

	public static void setRegistrationHappeningThrough(Context context,
			int registrationId) {
		if (context != null) {
			SharedPreferences.Editor editor = context.getSharedPreferences(
					PREF_NAME, Context.MODE_PRIVATE).edit();
			editor.putInt("registrationId", registrationId);
			editor.commit();
		}
	}

	public static int getRegistrationHappeningThrough(Context context) {
		if (context != null) {
			SharedPreferences prefs = context.getSharedPreferences(PREF_NAME,
					Context.MODE_PRIVATE);
			return prefs.getInt("registrationId", 0);
		}
		return 0;
	}

}
