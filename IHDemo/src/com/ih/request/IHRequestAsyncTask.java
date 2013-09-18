package com.ih.request;

import android.os.AsyncTask;
import android.os.Handler;

import com.ih.request.IHRequest.IHRequestListener;

public class IHRequestAsyncTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = IHRequestAsyncTask.class.getSimpleName();
	private IHRequestListener listener;
	private IHRequest request;
	private Handler mHandle = new Handler();

	public IHRequestAsyncTask(IHRequest req, IHRequestListener lis) {
		this.request = req;
		this.listener = lis;
		
	}



	@Override
	protected Void doInBackground(Void... arg0) {
		
			IHRequestListener innerlistener = new IHRequestListener() {
				@Override
				public void onRequestComplete(final Object obj) {
					mHandle.post(new Runnable() {
						@Override
						public void run() {
							listener.onRequestComplete(obj);
						}
					});
				}

				@Override
				public void onError(final int errCode, final String errMessage) {
					mHandle.post(new Runnable() {
						@Override
						public void run() {
							listener.onError(errCode, errMessage);
						}
					});
				}
			};
			request.addListener(innerlistener);
		request.execute();
		return null;
	}
}
