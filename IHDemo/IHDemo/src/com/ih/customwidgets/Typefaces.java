package com.ih.customwidgets;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * 
 * @author pragati.deshmukh The class is written because when loading a custom
 *         font from assets, memory leak is possible. The discussion is
 *         available at http://code.google.com/p/android/issues/detail?id=9904
 *         This class will load the fonts only once and hence memory is not
 *         possible
 */
public class Typefaces {
	private static final String TAG = "Typefaces";

	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	public static Typeface get(Context c, String assetPath) {
		synchronized (cache) {
			if (!cache.containsKey(assetPath)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							assetPath);
					cache.put(assetPath, t);
				} catch (Exception e) {
					Log.e(TAG, "Could not get typeface '" + assetPath
							+ "' because " + e.getMessage());
					return null;
				}
			}
			return cache.get(assetPath);
		}
	}
}