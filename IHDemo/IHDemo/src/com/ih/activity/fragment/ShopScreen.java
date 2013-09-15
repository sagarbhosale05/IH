/**
 * 
 */
package com.ih.activity.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.ih.activity.fragmentactivity.ShopDetailFragmentActivity;
import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Shop;

/**
 * @author abhijeet.bhosale
 * 
 */
public class ShopScreen extends Fragment {

	private String mContent;
	private View root;
	private Context context;
	ListView listView;

	public static ShopScreen newInstance(String content) {
		ShopScreen fragment = new ShopScreen();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			builder.append(content).append(" ");
		}
		builder.deleteCharAt(builder.length() - 1);
		fragment.mContent = builder.toString();
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.shops_screen, null);
		context = this.getActivity();

		return root;
	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}

	ShopsAdapter shopsAdapter;

	private void initializeScreen() {
		shopsAdapter = new ShopsAdapter(getActivity(), shops);
		listView.setAdapter(shopsAdapter);
		shopsAdapter.notifyDataSetChanged();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent shopDetails = new Intent(getActivity(),
						ShopDetailFragmentActivity.class);
				shopDetails.putExtra("shopObj", shops.get(position));
				shopDetails.putExtra("screenTitle", shops.get(position)
						.getShopName());
				startActivity(shopDetails);

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) root.findViewById(R.id.listview);
		downloadData();

		initializeScreen();
		initializeHandler();

	}

	ArrayList<Shop> shops;

	/**
	 * 
	 */
	private void downloadData() {

		shops = Shop.getShops();
	}

	public class ShopsAdapter extends BaseAdapter {

		Context context;

		ArrayList<Shop> shops;
		ViewHolder viewHolder;
		LayoutInflater layoutInflater;

		public ShopsAdapter(Context context, ArrayList<Shop> shops) {

			this.context = context;
			this.shops = shops;
			layoutInflater = ((Activity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {

			return shops != null ? shops.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = layoutInflater.inflate(
						R.layout.shops_listview_row, null);
				viewHolder.shopImage = (ImageView) convertView
						.findViewById(R.id.shopImageView);
				viewHolder.shopName = (CustomTextView) convertView
						.findViewById(R.id.shopTextView);
				viewHolder.shopAddress = (CustomTextView) convertView
						.findViewById(R.id.shopAddressTextView);
				viewHolder.shopCategory = (CustomTextView) convertView
						.findViewById(R.id.shopCategoryTextView);
				viewHolder.shopRating = (RatingBar) convertView
						.findViewById(R.id.shopRatings);

				convertView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder) convertView.getTag();

			viewHolder.shopImage.setImageResource(shops.get(position)
					.getShopRes());
			viewHolder.shopName.setText(shops.get(position).getShopName());
			viewHolder.shopAddress
					.setText(shops.get(position).getShopAddress());
			viewHolder.shopCategory.setText(shops.get(position)
					.getShopCategory());

			return convertView;
		}

		public class ViewHolder {

			private CustomTextView shopName;
			private ImageView shopImage;
			private CustomTextView shopAddress;
			private CustomTextView shopCategory;
			private RatingBar shopRating;

		}

	}

}
