package com.ih.utility;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ih.demo.R;

// TODO: Auto-generated Javadoc
/**
 * The Class Utility.
 */
public class Utility {

	/**
	 * Check how much usable space is available at a given path.
	 * 
	 * @param path
	 *            The path to check
	 * @return The space available in bytes
	 */
	@SuppressLint("NewApi")
	public static long getUsableSpace(File path) {
		final StatFs stats = new StatFs(path.getPath());
		Log.d("", "");
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}

	/**
	 * Check if external storage is built-in or removable.
	 * 
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	@SuppressLint("NewApi")
	public static boolean isExternalStorageRemovable() {
		return true;
	}

	/**
	 * Check if OS version has built-in external cache dir method.
	 * 
	 * @return true, if successful
	 */
	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * Get the external app cache directory.
	 * 
	 * @param context
	 *            The context to use
	 * @return The external cache dir
	 */
	@SuppressLint("NewApi")
	public static File getExternalCacheDir(Context context) {
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir();
		}

		// Before Froyo we need to construct the external cache dir ourselves
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}

	/**
	 * Check if OS version has a http URLConnection bug. See here for more
	 * information:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 
	 * @return true, if successful
	 */
	public static boolean hasHttpConnectionBug() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
	}

	/**
	 * Workaround for bug pre-Froyo, see here for more info:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 */
	public static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (hasHttpConnectionBug()) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @return size in bytes
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {

		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**
	 * Gets the user token.
	 * 
	 * @param context
	 *            the context
	 * @return the user token
	 */
	public static String getUserToken(Context context) {
		String userToken = "";
		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_credentials),
				Context.MODE_PRIVATE);
		if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_app), "")
				.equals("") == false) {
			userToken = preferences.getString(
					context.getString(R.string.shared_pref_key_unique_id_app),
					"");
		} else if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_facebook),
				"").equals("") == false) {
			userToken = preferences
					.getString(
							context.getString(R.string.shared_pref_key_unique_id_facebook),
							"");
		} else if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_twitter),
				"").equals("") == false) {
			userToken = preferences.getString(context
					.getString(R.string.shared_pref_key_unique_id_twitter), "");
		}

		return userToken;
	}

	/**
	 * Check for status code and initialize.
	 * 
	 * @param context
	 *            the context
	 * @param statusCode
	 *            the status code
	 * @return true, if successful
	 */
	public static boolean checkForStatusCodeAndInitialize(Context context,
			int statusCode) {

		boolean isSuccessful = false;

		switch (statusCode) {
		case HttpStatus.SC_OK:
		case HttpStatus.SC_CREATED:
		case HttpStatus.SC_NO_CONTENT:
			isSuccessful = true;
			break;
		}
		return isSuccessful;
	}

	/**
	 * Hide soft keyboard.
	 * 
	 * @param activity
	 *            the activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);

		if (activity.getCurrentFocus() != null)
			inputMethodManager.hideSoftInputFromWindow(activity
					.getCurrentFocus().getWindowToken(), 0);
	}

	/**
	 * Display dialog.
	 * 
	 * @param context
	 *            the context
	 * @param msg
	 *            the msg
	 */
	public static void displayDialog(Context context, String msg) {
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						dialog.dismiss();
					}
				});

		dialog.setMessage(msg);
		dialog.show();
	}

	/**
	 * Check email correct.
	 * 
	 * @param signupEmail
	 *            the signup email
	 * @return true, if successful
	 */

	public static boolean checkEmailCorrect(String signupEmail) {
		if (signupEmail.length() == 0) {
			return false;
		}

		String pttn = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(pttn);
		Matcher m = p.matcher(signupEmail);

		if (m.matches()) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if is digit.
	 * 
	 * @param name
	 *            the name
	 * @return true, if is digit
	 */
	public static boolean isDigit(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the version name.
	 * 
	 * @param context
	 *            the context
	 * @return the version name
	 */
	public static String getVersionName(Context context) {
		StringBuilder builder = new StringBuilder();
		builder.append(context.getString(R.string.Settings_Version));
		builder.append(" ");
		String versionName = "1.0";
		try {
			versionName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			versionName = "1.0";
		}
		builder.append(versionName);
		return builder.toString();
	}

	/**
	 * Save user details.
	 * 
	 * @param context
	 *            the context
	 * @param emailId
	 *            the email id
	 */
	public static void saveUserDetails(Context context, String emailId) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_credentials),
				Context.MODE_PRIVATE);

		preferences
				.edit()
				.putString(context.getString(R.string.account_details_emailid),
						emailId).commit();
	}

	/**
	 * Fetch user details.
	 * 
	 * @param context
	 *            the context
	 * @return the string
	 */
	public static String fetchUserDetails(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_credentials),
				Context.MODE_PRIVATE);

		return preferences.getString(
				context.getString(R.string.account_details_emailid), null);
	}

	/**
	 * Checks if is user already signed up.
	 * 
	 * @param context
	 *            the context
	 * @return the int
	 */
	public static int isUserAlreadySignedUp(Context context) {
		int userSignedUpWith = 0;
		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_credentials),
				Context.MODE_PRIVATE);
		if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_app), "")
				.equals("") == false) {
			userSignedUpWith = Constants.UNIQUE_APP_ID;
		} else if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_facebook),
				"").equals("") == false) {
			userSignedUpWith = Constants.UNIQUE_FACEBOOK_ID;
		} else if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_twitter),
				"").equals("") == false) {
			userSignedUpWith = Constants.UNIQUE_TWITTER_ID;
		}

		return userSignedUpWith;
	}

	/**
	 * Save unique id.
	 * 
	 * @param context
	 *            the context
	 * @param uniqueId
	 *            the unique id
	 * @param signUpMethod
	 *            the sign up method
	 */
	public static void saveUniqueId(Context context, String uniqueId,
			int signUpMethod) {

		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_credentials),
				Context.MODE_PRIVATE);
		Editor credentialsEditor = preferences.edit();
		String key = "";

		switch (signUpMethod) {
		case Constants.UNIQUE_APP_ID:
			key = context.getString(R.string.shared_pref_key_unique_id_app);
			break;
		case Constants.UNIQUE_FACEBOOK_ID:
			key = context
					.getString(R.string.shared_pref_key_unique_id_facebook);
			break;
		case Constants.UNIQUE_TWITTER_ID:
			key = context.getString(R.string.shared_pref_key_unique_id_twitter);
			break;
		default:
			break;
		}

		credentialsEditor.putString(key, uniqueId).commit();
	}

	/**
	 * Check native login.
	 * 
	 * @param context
	 *            the context
	 * @return the int
	 */
	public static int checkNativeLogin(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_credentials),
				Context.MODE_PRIVATE);

		if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_twitter),
				"").equals("") == false) {
			return Constants.UNIQUE_TWITTER_ID;
		} else if (preferences.getString(
				context.getString(R.string.shared_pref_key_unique_id_facebook),
				"").equals("") == false) {
			return Constants.UNIQUE_FACEBOOK_ID;
		}
		return Constants.UNIQUE_APP_ID;
	}

	public static void showToast(Context context, String message) {
		showToast(context, message, Toast.LENGTH_SHORT);
	}

	public static void showToast(Context context, String message, int millisec) {
		Toast.makeText(context, message, millisec).show();
	}

	public static String getPath(Activity activity,Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
