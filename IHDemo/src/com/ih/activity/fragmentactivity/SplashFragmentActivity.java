/**
 * 
 */
package com.ih.activity.fragmentactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.utility.Constants;
import com.ih.utility.DeviceInformation;
import com.ih.utility.Utility;

/**
 * @author abhijeet.bhosale
 *
 */
public class SplashFragmentActivity extends SherlockFragmentActivity implements  OnClickListener {
	private int start_app = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == start_app)
				startApplication();
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_screen);
		
		sendMessageToHandler();
		
		DeviceInformation.setDeviceDensity(getWindowManager(), this);
		DeviceInformation.setDeviceDimensions(getWindowManager(), this);
		
		((RelativeLayout)findViewById(R.id.layout)).setOnClickListener(this);

		
		((CustomTextView)findViewById(R.id.settingsVersion)).setText(Utility.getVersionName(this));
	}

	private void sendMessageToHandler() {
		handler.sendEmptyMessageDelayed(start_app,
				Constants.DEFAULT_SPLASH_SCREEN_TIME);
	}

	private void startApplication() {
		/*
		 * If the user taps the splash screen, then we are starting activity.
		 * the below statement will prevent app from opening activity twice.
		 */
		
		handler.removeMessages(start_app);
		Intent intent = new Intent(this, HomeFragmentActivity.class);
		startActivity(intent);
		this.finish();
	}


	@Override
	public void onClick(View v) {
		startApplication();
	}

}
