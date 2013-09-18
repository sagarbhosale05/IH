package com.ih.request;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

import com.ih.model.Login;
import com.ih.service.ServiceHelper;

public class IHPostRequest extends IHRequest {
	private static final String TAG = IHPostRequest.class.getSimpleName();
	private String params = null;

	private IHPostRequest(Context cont, String url, String body, IHRequestType t) {
		super(cont, t);
		this.serviceURL = url;
		this.params = body;
		this.requestType = t;
	}

	public IHPostRequest(Context cont, String url, String string,
			String params, IHRequestType t) {
		this(cont, url, string, t);
		this.params = params;
	}

	/**
	 * Parse server response. And store in respective model class.
	 */
	@Override
	protected void parseJSONResponse(InputStream inStream, IHRequestType ihType) {

		switch (ihType) {
		case POST_CREATE_ACCOUNT:

			break;
		case POST_LOGIN:

			break;

		default:
			break;
		}

	}

	@Override
	protected void execute() {
		if (serviceURL == null)
			return;

		final HttpPost post = new HttpPost(serviceURL);
		Log.d(TAG, "URL: " + serviceURL);

		try {
			Object entity = null;
			if (params != null && !"".equals(params)) {
				String[] paramArray = params.split("&");

				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				for (int i = 0; i < paramArray.length; i++) {
					String[] param = paramArray[i].split("=");
					postParameters.add(new BasicNameValuePair(param[0],
							param.length > 1 ? param[1] : ""));
				}
				entity = new UrlEncodedFormEntity(postParameters);

			}

			post.setEntity(entity instanceof StringEntity ? (StringEntity) entity
					: (MultipartEntity) entity);
			executeRequest(post);

		} catch (UnsupportedEncodingException e) {
			listener.onError(IHRequestListener.LOCAL_ERROR, e.getMessage());
		} catch (Exception e) {
			listener.onError(IHRequestListener.LOCAL_ERROR, e.getMessage());
		}

	}

	public static IHPostRequest login(Context cont, Login login) {

		final String url = "http://" + ServiceHelper.getIHHostURL() + "/login";
		StringWriter writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			serializer.write(login, writer);
		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
			return null;
		}
		return new IHPostRequest(cont, url, writer.toString(),
				IHRequestType.POST_LOGIN);
	}
}
