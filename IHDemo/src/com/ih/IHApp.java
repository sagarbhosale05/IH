/**
 * 
 */
package com.ih;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.ih.imagecache.Cache;
import com.ih.imagecache.ImageCache;
import com.ih.imagecache.ImageCache.ImageCacheParams;

/**
 * @author abhijeet.bhosale
 * 
 */
public class IHApp extends Application {
	private static Context context;
	private static final String IMAGE_CACHE_DIR = "thumbs";
	public static String FINSIH_ACTION = "finish.action";

	/**
	 * The default shared bitmap cache that is shared across the application.
	 */
	private final Cache<String, Bitmap> sharedBitmapCache = new Cache<String, Bitmap>(
			25);

	private ImageCache imageCache;

	public ImageCache getImageCache() {
		return imageCache;
	}

	public Cache<String, Bitmap> getSharedBitmapCache() {
		return sharedBitmapCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		setUpCache();
		setDeviceVersion();
		generateKeyHash();
	}

	/**
	 * Use this method to generate key hash for facebook. The key hash is
	 * required to be saved in the settings of the application inside facebook's
	 * developer account. {@linkplain https
	 * ://developers.facebook.com/docs/getting
	 * -started/facebook-sdk-for-android/3.0/}
	 */
	private void generateKeyHash() {
		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo("com.ih.demo",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e1) {
			Log.e("name not found", e1.toString());
		}

		catch (NoSuchAlgorithmException e) {
			Log.e("no such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("exception", e.toString());
		}

	}

	/**
	 * The method checks if the build sdk version is >= 4 and sets true / false
	 * accordingly inside {@link SharedPreferences}
	 */
	private void setDeviceVersion() {
		PreferencesManager.setIsIceCreamSandWich(IHApp.this,
				Build.VERSION.SDK_INT >= 14 ? true : false);
	}

	public static Context getAppContext() {
		return IHApp.context;
	}

	private void setUpCache() {
		ImageCacheParams cacheParams = new ImageCacheParams(IMAGE_CACHE_DIR);
		imageCache = new ImageCache(this, cacheParams);
		// Allocate a third of the per-app memory limit to the bitmap memory
		// cache. This value
		// should be chosen carefully based on a number of factors. Refer to the
		// corresponding
		// Android Training class for more discussion:
		// http://developer.android.com/training/displaying-bitmaps/
		// In this case, we aren't using memory for much else other than this
		// activity and the
		// ImageDetailActivity so a third lets us keep all our sample image
		// thumbnails in memory
		// at once.
		// cacheParams.memCacheSize = 1024 * 1024 *
		// Utils.getMemoryClass(getActivity()) / 3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.d("Cleanup", "Clear cache on Low Memory");
		sharedBitmapCache.clear();
		imageCache.clearCaches();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onTerminate()
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
		if (sharedBitmapCache != null)
			sharedBitmapCache.clear();
		Log.d("Cleanup", "Clear cache on Low Memory");
		if (imageCache != null)
			imageCache.clearCaches();
	}

}
