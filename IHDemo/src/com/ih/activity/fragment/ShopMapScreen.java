package com.ih.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.demo.R;
import com.ih.model.Shop;

public class ShopMapScreen extends SherlockFragment {

	private String mContent;
	private View root;
	private Context context;
	Shop shop;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.shop_map_screen, null);
		context = this.getActivity();

		return root;
	}

	public static ShopMapScreen newInstance(String content,Shop shop) {
		ShopMapScreen fragment = new ShopMapScreen();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			builder.append(content).append(" ");
		}
		builder.deleteCharAt(builder.length() - 1);
		fragment.mContent = builder.toString();
		fragment.shop=shop;
		return fragment;
	}

}
