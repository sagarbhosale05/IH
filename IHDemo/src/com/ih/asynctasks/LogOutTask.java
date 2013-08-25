package com.ih.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ih.BaseActivity;

/**
 * @author pragati.deshmukh The Class is responsible for log out user from 
 * facebook twitter and application.
 * This calls {@link BaseActivity#signOutUser()}
 */
public class LogOutTask extends AsyncTask<Void, Void, Void> {
	private Context asynContext;
	private String dialogMessage;
	private ProgressDialog progressDialog;
	private BaseActivity activity;

	/**
	 * Instantiates a new my async task.
	 * 
	 * @param mContext
	 *            the m context
	 * @param URL
	 *            the url
	 */
	public LogOutTask(Context mContext) {
		this.asynContext = mContext;
	}

	/**
	 * Instantiates a new my async task.
	 * 
	 * @param mContext
	 *            the m context
	 * @param dialogMessage
	 *            the dialog message
	 * @param URL
	 *            the url
	 * @param cmgArguments
	 *            the cmg arguments
	 */
	public LogOutTask(Context mContext, String dialogMessage,
			BaseActivity activity) {
		this.asynContext = mContext;
		this.dialogMessage = dialogMessage;
		this.activity = activity;
	}

	
	@Override
	protected void onPreExecute() {
		if (asynContext != null) {
			progressDialog = new ProgressDialog(asynContext);
			progressDialog.setMessage(dialogMessage);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		activity.signOutUser();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (progressDialog != null)
			progressDialog.dismiss();
		
		activity.broadcastLogoutAction();
	}

}
