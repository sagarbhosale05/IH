package com.ih.activity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.database.DBAdapter;
import com.ih.demo.R;
import com.ih.model.Shop;

public class ShopInfoScreen extends SherlockFragment {

	private String mContent;
	private View root;
	private Context context;
	Shop shop;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.shop_info_screen, null);
		context = this.getActivity();

		return root;
	}

	public static ShopInfoScreen newInstance(String content, Shop shop) {
		ShopInfoScreen fragment = new ShopInfoScreen();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			builder.append(content).append(" ");
		}
		builder.deleteCharAt(builder.length() - 1);
		fragment.mContent = builder.toString();
		fragment.shop = shop;
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initializeScreen();
		initializeHandler();

	}

	private void initializeHandler() {

	}

	private void initializeScreen() {
		DBAdapter dbAdapter=new DBAdapter(getActivity());
		dbAdapter.open();
		shop=dbAdapter.getShop();
		if (shop == null)
			return;
		if (shop.getShopImageUrl() != null)
			((ImageView) getActivity().findViewById(R.id.shopDetailImageView))
					.setImageURI(Uri.parse(shop.getShopImageUrl()));
		else
			((ImageView) getActivity().findViewById(R.id.shopDetailImageView))
					.setImageResource(R.drawable.ic_launcher);
		((TextView) getActivity().findViewById(R.id.shopNameTextView))
				.setText(shop.getShopName());
		((TextView) getActivity().findViewById(R.id.shopAddessValueTextView))
				.setText(shop.getShopAddress());
		((TextView) getActivity().findViewById(R.id.shopCategoryValueTextView))
				.setText(shop.getShopCategory());
		((TextView) getActivity().findViewById(R.id.shopPhoneValueTextView))
				.setText(shop.getShopPhone());
		((TextView) getActivity().findViewById(R.id.shopWebsiteValueTextView))
				.setText(shop.getShopWebSite());
		((TextView) getActivity().findViewById(R.id.shopEmailValueTextView))
				.setText(shop.getShopEmail());
		((TextView) getActivity().findViewById(R.id.shopFBlikeValueTextView))
				.setText(shop.getShopFBLike());
		((TextView) getActivity().findViewById(R.id.shopHrsValueTextView))
				.setText(shop.getShopHrs());

	}

}
