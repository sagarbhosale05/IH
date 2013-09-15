package com.ih.utility;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * The Class HttpManager.
 */
public class HttpManager {
	
	/** The Constant DEFAULT_HTTP_CLIENT. */
	private static final DefaultHttpClient DEFAULT_HTTP_CLIENT;
	static {
        final HttpParams params = new BasicHttpParams(); 
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");

        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
        HttpConnectionParams.setSoTimeout(params, 10 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        HttpClientParams.setRedirecting(params, true);
        
        ConnManagerParams.setMaxTotalConnections(params, 20);
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
        ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);        
        DEFAULT_HTTP_CLIENT = new DefaultHttpClient(manager, params); 
    }

	/**
	 * Instantiates a new http manager.
	 */
	private HttpManager() { 
		
    }

    /**
     * Execute.
     *
     * @param head the head
     * @return the http response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static HttpResponse execute(HttpHead head) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(head);
    }

    /**
     * Execute.
     *
     * @param host the host
     * @param get the get
     * @return the http response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static HttpResponse execute(HttpHost host, HttpGet get) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(host, get);
    }
    
    /**
     * Execute.
     *
     * @param host the host
     * @param post the post
     * @return the http response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static HttpResponse execute(HttpHost host, HttpPost post) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(host, post);
    }

    /**
     * Execute.
     *
     * @param get the get
     * @return the http response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static HttpResponse execute(HttpGet get) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(get);
    }
    
    /**
     * Execute.
     *
     * @param post the post
     * @return the http response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static HttpResponse execute(HttpPost post) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(post);
    }
    
    /**
     * Execute.
     *
     * @param get the get
     * @param responseHandler the response handler
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String execute(HttpGet get, ResponseHandler<String> responseHandler) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(get, responseHandler);
    }
    
    /**
     * Execute.
     *
     * @param post the post
     * @param responseHandler the response handler
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String execute(HttpPost post, ResponseHandler<String> responseHandler) throws IOException {
        return DEFAULT_HTTP_CLIENT.execute(post, responseHandler);
    }
}
