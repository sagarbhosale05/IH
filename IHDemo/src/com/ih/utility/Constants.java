package com.ih.utility;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	/**
	 * The default time to show the splash screen.
	 */
	public static final int IO_BUFFER_SIZE = 8 * 1024;
	public static final long DEFAULT_SPLASH_SCREEN_TIME = 2000;


	public static String CONSUMER_KEY = "mYWDIxVyBRbZAe04qO3HDA";
	public static String CONSUMER_SECRET = "Be2VKueTERCm6F0VxRy39PkTrXMMT4feoZLP1uiewgI";
	public static String TWICPIC_API_KEYS = "aed74e74e1b2aebe16dc5e276dfd630a";
	public static String YFROG_API_KEYS = "58CINTUY93b056aa3e452a5764884fd6188974ee";

	public static final int GET_TWITTER_ID = 1;
	public static final int GET_FACEBOOK_ID = 1;

	public static final int UNIQUE_TWITTER_ID = 1;
	public static final int UNIQUE_FACEBOOK_ID = 2;
	public static final int UNIQUE_APP_ID = 3;

	public static final int SEARCH_MODEL_NAME = 1;
	public static final int SEARCH_YEAR_MIN_RANGE = 2;
	public static final int SEARCH_YEAR_MAX_RANGE = 3;
	public static final int SEARCH_PRICE_MIN_RANGE = 4;
	public static final int SEARCH_PRICE_MAX_RANGE = 5;

	public static final int HTTP_GET = 0;
	public static final int HTTP_POST_JSON = 1;
	public static final int HTTP_POST_PARAMETER = 2;
	public static final int HTTP_PUT_JSON = 3;
	public static final int HTTP_PUT_PARAMETER = 4;
	public static final int HTTP_DELETE_JSON = 5;
	public static final int HTTP_DELETE_PARAMETER = 6;

	@SuppressWarnings("serial")
	public static final List<String> PERMISSIONS = new ArrayList<String>() {
		{
			add("publish_stream");
		}
	};

	/**
	 * The constants for the connection.
	 */
	public class Connection {
		/**
		 * The default timeout to setup the connection.
		 */
		public static final int DEFAULT_CONNECTION_ESTABLISH_TIMEOUT = 7000;

		/**
		 * The default timeout for getting the full data.
		 */
		public static final int DEFAULT_DATA_FETCH_TIMEOUT = 10000;
	}

	// screen ids.
	public static final int ADD_TO_FAVORITE = 1;
	public static final int DELETE_FROM_FAVORITE = 2;
	public static final int CREATE_USER = 3;
	public static final int UPDATE_PASSWORD = 4;
	public static final int UPDATE_SETTINGS = 5;
	public static final int ACCOUNT_DETAILS = 6;

	public static final int POST_TWITTER_SUCCESSFULL = 11;
	public static final int POST_TWITTER_UNSUCCESSFULL = 12;
	public static final int POST_TWITTER_DUPLICATE = 10;

	public static final int PERFORM_PUBLISH = 3;

	public static final int POST_FACEBOOK_UNSUCCESSFULL = 1;
	public static final int POST_FACEBOOK_SUCCESSFUL = 2;
	public static final String FACEBOOK_APP_ID = "171752716302841";
	//public static final String FACEBOOK_APP_ID = "270601226395051";
	
	
	
	public static final int FACEBOOK_ACTIVITY_REQ_CODE = 32665;

	public static final int AD_RECEIVED = 1;

	public static final String CACHE_MAKE_LIST_KEY = "MakeList";
	
	
	
}
