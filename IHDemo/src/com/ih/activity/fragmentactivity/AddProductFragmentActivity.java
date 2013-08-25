package com.ih.activity.fragmentactivity;

import android.os.Bundle;

import com.ih.BaseActivity;
import com.ih.demo.R;

public class AddProductFragmentActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_addproduct);
		setActionBarHomeAsUpEnabled(true);
	}
	
	
}
