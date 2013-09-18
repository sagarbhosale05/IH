package com.ih.service;

import android.content.Context;

import com.ih.AppURL;
import com.ih.model.Login;
import com.ih.request.IHPostRequest;
import com.ih.request.IHRequest.IHRequestListener;
import com.ih.request.IHRequestAsyncTask;
import com.ih.utility.Constants;

/**
 * A singleton class used to handle all the service calls and <BR/>
 * store global shared instances such as database and user accounts.
 * 
 * @author Jay
 */
public class ServiceHelper {
	private static final String TAG = ServiceHelper.class.getSimpleName();
	private static final long WAIT_CONNECTION_TH = 3000;
	private static ServiceHelper _INSTANCE = null;
	private static volatile boolean connected = false;

	/* Global Shared Instances */
	private Context appContext;

	private ServiceHelper() {
		throw new AssertionError();
	}

	private ServiceHelper(final Context cont) {
		initInstances(cont);
	}

	public void releaseService() {
		if (_INSTANCE != null && connected) {
			connected = false;
			_INSTANCE = null;
		}
	}

	/**
	 * Initialize the global shared instances stored in ServiceHelper singleton
	 * 
	 * @param Context
	 *            cont
	 */
	private void initInstances(final Context cont) {
		appContext = cont;

	}

	/**
	 * @param getApplicationContext
	 *            ()
	 * @return
	 */
	public static ServiceHelper getInstance(Context applicationContext) {
		if (_INSTANCE == null) {
			_INSTANCE = new ServiceHelper(applicationContext);
		}
		return _INSTANCE;
	}

	public Context getContext() {
		return appContext;
	}

	/**
	 * Verify user credentials.
	 * 
	 * @param login
	 * @param requestListener
	 */
	public void login(Login login, IHRequestListener requestListener) {

		IHPostRequest request = IHPostRequest.login(appContext, login);
		new IHRequestAsyncTask(request, requestListener).execute();
	}

	public static String getIHHostURL() {
		if (AppURL.isProdServer())
			return Constants.PRO_IH_HOST;
		else
			return Constants.DEV_IH_HOST;

	}

}
