package com.ih.activity.fragmentactivity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

import com.ih.BaseActivity;
import com.ih.demo.R;

/**
 * @author pragati.deshmukh
 * Privacy policy option present inside the settings menu.
 * It loads the HTML file from assets.
 * The @TargetApi(11) is specified as we are using method webview.setLayerType
 * inside {@link #onStart()}.
 * The method is available from 11+ and as our minsdkversion inside 
 * AndroidManifest.xml is 8, it throws compile time error.
 * 
 */
@TargetApi(11)
public class PrivacyPolicyFragmentActivity extends BaseActivity {
	private WebView webview;;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.common_html_layout);
		super.onCreate(arg0);
		screenTitle = (String) getIntent().getExtras().get(screenTitleKey);
		setScreenTitle();
		setActionBarHomeAsUpEnabled(true);
	}

	@Override
	public void onStart() {
		webview = (WebView) findViewById(R.id.staticwebview);
		webview.setBackgroundColor(Color
				.parseColor(getString(R.color.background)));
		if(Build.VERSION.SDK_INT >= 11)
			webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		webview.loadUrl("file:///android_asset/privacy_policy.html");
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.setBackgroundColor(Color
				.parseColor(getString(R.color.background)));
		super.onResume();
	}
}
