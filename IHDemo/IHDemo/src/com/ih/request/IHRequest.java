package com.ih.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;

import android.content.Context;
import android.util.Log;

import com.ih.utility.HttpManager;
import com.ih.utility.IOUtilities;

public abstract class IHRequest {

	public IHRequestType requestType;
	private static final int RETRY_LIMIT = 3;
	private static final String TAG = IHRequest.class.getSimpleName();
	private int retryLimitCounter = 0;
	protected String serviceURL = null;

	protected IHRequestListener listener = new DefaultIHRequestListener();

	public static enum IHRequestType {
		GET_SHOP_DETAILS, POST_SHOP_DETAILS, POST_LOGIN, POST_CREATE_ACCOUNT

	}

	protected abstract void parseJSONResponse(InputStream inStream,
			IHRequest.IHRequestType ihType);

	protected abstract void execute();

	private class DefaultIHRequestListener implements IHRequestListener {
		private DefaultIHRequestListener() {
		}

		@Override
		public void onRequestComplete(Object obj) {
		}

		@Override
		public void onError(int errorCode, String errorMessage) {
		}
	}

	public static interface IHRequestListener {
		public static final int BAD_PARAMETER_TYPE_ERROR = -1;
		public static final int NETWORK_ERROR = 1;
		public static final int SERVER_ERROR = 2;
		public static final int AUTHENTICATION_ERROR = 3;
		public static final int LOCAL_ERROR = 4;
		public static final int TIMEOUT = 5;

		void onRequestComplete(Object obj);

		void onError(int errorCode, String errorMessage);
	}

	public IHRequest(Context cont) {
	}

	public IHRequest(Context cont, IHRequestType t) {
		this(cont);
		this.requestType = t;
	}

	public IHRequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(IHRequestType t) {
		requestType = t;
	}

	public void addListener(IHRequestListener lis) {
		listener = lis;
	}

	protected void executeRequest(HttpGet get) {
		HttpEntity entity = null;
		InputStream is = null;
		try {
			final HttpResponse response = HttpManager.execute(get);
			final int statusCode = response.getStatusLine().getStatusCode();
			String reasonPhrase = response.getStatusLine().getReasonPhrase();
			Log.d("executeRequest", "statusCode: " + statusCode
					+ " reasonPhrase:" + reasonPhrase);
			entity = response.getEntity();
			/*
			 * Header[] headers = response.getAllHeaders(); for(int i = 0;
			 * i<headers.length; i++) Logger.i(TAG, headers[i].getName() + ": "
			 * + headers[i].getValue());
			 */

			is = entity != null ? entity.getContent()
					: new ByteArrayInputStream("".getBytes("UTF-8"));
			switch (statusCode) {
			case HttpStatus.SC_CREATED:
			case HttpStatus.SC_OK:
				parseJSONResponse(is, requestType);

				break;

			case HttpStatus.SC_UNAUTHORIZED:
				entity.consumeContent();
				entity = null;
				is.close();
				is = null;
				if (retryLimitCounter < RETRY_LIMIT) {

					retryLimitCounter++;
					executeRequest(get);
				} else {
					listener.onError(IHRequestListener.TIMEOUT,
							"Retry Authenticate Timeout. Error: " + statusCode);
				}
				break;
			default:
				listener.onError(IHRequestListener.SERVER_ERROR,
						"Server Error: " + statusCode + " [" + reasonPhrase
								+ "]" + " : " + IOUtilities.streamToString(is));
				break;
			}

		} catch (ConnectTimeoutException e) {
			listener.onError(IHRequestListener.NETWORK_ERROR,
					"connection time out");
		} catch (IOException e) {
			listener.onError(IHRequestListener.NETWORK_ERROR, e.getMessage());
		} finally {
			if (entity != null) {
				try {
					if (is != null)
						is.close();
					entity.consumeContent();
				} catch (IOException e) {
				}
			}
		}
	}

	protected void executeRequest(HttpPost post) {
		HttpEntity entity = null;
		InputStream is = null;
		try {
			final HttpResponse response = HttpManager.execute(post);
			final int statusCode = response.getStatusLine().getStatusCode();
			String reasonPhrase = response.getStatusLine().getReasonPhrase();
			Log.d(TAG, "Status Code: " + statusCode + " reasonPhrase:"
					+ reasonPhrase);

			entity = response.getEntity();
			is = entity.getContent();

			switch (statusCode) {
			case HttpStatus.SC_CREATED:
			case HttpStatus.SC_OK:
				// Logger.log(Logger.VERBOSE, TAG, "Response: " +
				// IOUtilities.streamToString(is));
				parseJSONResponse(is, requestType);
				;
				break;
			case HttpStatus.SC_UNAUTHORIZED:
				entity.consumeContent();
				entity = null;
				is.close();
				is = null;
				if (retryLimitCounter < RETRY_LIMIT) {
					retryLimitCounter++;
					executeRequest(post);
				} else {
					listener.onError(IHRequestListener.AUTHENTICATION_ERROR,
							"Retry Authenticate Timeout. Error: " + statusCode);
				}
				break;
			case HttpStatus.SC_BAD_REQUEST:
				// Happens when check in fail.
				String errStr = IOUtilities.streamToString(is);
				Log.d(TAG, errStr);
				listener.onError(statusCode, "400 Bad Request." + " : "
						+ errStr);
				break;
			default:
				listener.onError(IHRequestListener.SERVER_ERROR,
						"Server Error: " + statusCode + " [" + reasonPhrase
								+ "]" + " : " + IOUtilities.streamToString(is));
				break;
			}
		} catch (IOException e) {
			listener.onError(IHRequestListener.NETWORK_ERROR, e.getMessage());
		} finally {
			if (entity != null) {
				try {
					if (is != null)
						is.close();
					entity.consumeContent();
				} catch (IOException e) {
				}
			}
		}
	}

}
