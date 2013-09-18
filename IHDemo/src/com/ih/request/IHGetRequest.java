package com.ih.request;

import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class IHGetRequest extends IHRequest {
	private static final String TAG = IHGetRequest.class.getSimpleName();

	private IHGetRequest(Context cont, String url, IHRequestType t) {
		super(cont, t);
		super.serviceURL = url;
		super.requestType = t;
	}

	@Override
	protected void parseJSONResponse(InputStream inStream, IHRequestType ihType) {
		String stream;
		JSONObject jArray = new JSONObject();
		switch (ihType) {
		case GET_SHOP_DETAILS:
			
			break;

		default:
			break;
		}
		
		
		
	}

	@Override
	protected void execute() {
		if (serviceURL == null) 
			return;
		
		Log.d(TAG, "url:\t" + serviceURL);
		final HttpGet get = new HttpGet(serviceURL);
		get.setHeader("accept", "application/json");
		executeRequest(get);
	}
}
