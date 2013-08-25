package com.ih.activity.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Product;
import com.ih.model.Shop;

public class ShopProductsScreen extends SherlockFragment {

	private String mContent;
	private View root;
	private Context context;
	ListView listView;
	Shop shop;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater
				.inflate(R.layout.shop_products_screen, null);
		context = this.getActivity();

		return root;
	}

	public static ShopProductsScreen newInstance(String content, Shop shop) {
		ShopProductsScreen fragment = new ShopProductsScreen();
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
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) root.findViewById(R.id.listview);
		downloadData();

		initializeScreen();
		initializeHandler();
	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}

	ShopProductAdapter shopProductAdapter;

	private void initializeScreen() {
		shopProductAdapter = new ShopProductAdapter(getActivity(), products);
		listView.setAdapter(shopProductAdapter);
		shopProductAdapter.notifyDataSetChanged();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				addCollectionFragment(position);
			}
		});

	}

	protected void addCollectionFragment(int position) {

		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment collectionFragment = CollectionScreen.newInstance(products
				.get(position).getProductName(), products.get(position)
				.getCollections());
		transaction.add(R.id.fragment_container, collectionFragment,
				getString(R.string.shop_detail_tag));
		transaction.addToBackStack(getString(R.string.shop_detail_tag));
		transaction.setBreadCrumbTitle(getString(R.string.shop_detail));
		transaction.commit();

	}

	ArrayList<Product> products;

	/**
	 * 
	 */
	private void downloadData() {

		products = Product.getproducts();
	}

	public class ShopProductAdapter extends BaseAdapter {

		Context context;

		ArrayList<Product> products;
		ViewHolder viewHolder;
		LayoutInflater layoutInflater;

		public ShopProductAdapter(Context context, ArrayList<Product> products) {

			this.context = context;
			this.products = products;
			layoutInflater = ((Activity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {

			return products != null ? products.size() : 0;
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
						R.layout.products_listview_row, null);
				viewHolder.productImage = (ImageView) convertView
						.findViewById(R.id.productImageView);
				viewHolder.collectionNameText = (CustomTextView) convertView
						.findViewById(R.id.productTextView);
				viewHolder.collectionCountText = (CustomTextView) convertView
						.findViewById(R.id.collectionCountTextView);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
				viewHolder.resetView();
			}

			viewHolder.productImage.setImageResource(products.get(position)
					.getProductRes());
			viewHolder.collectionNameText.setText(products.get(position)
					.getProductName());
			viewHolder.collectionCountText.setText(""
					+ products.get(position).getProductCoollectionCount());

			return convertView;
		}

		public class ViewHolder {

			private CustomTextView collectionNameText;
			private CustomTextView collectionCountText;
			private ImageView productImage;

			public void resetView() {
				productImage.invalidate();
			}

		}

	}

}
