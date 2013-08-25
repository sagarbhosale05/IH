package com.ih.activity.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.GridView;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.BaseActivity;
import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Collection;

public class CollectionScreen extends SherlockFragment {

	private String mContent;
	private View root;
	private Context context;
	ArrayList<Collection> collection;
	GridView gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.collection_screen, null);
		context = this.getActivity();

		return root;
	}

	public static CollectionScreen newInstance(String content,
			ArrayList<Collection> collection) {
		CollectionScreen fragment = new CollectionScreen();
		fragment.mContent = content;
		fragment.collection = collection;
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gridView = (GridView) root.findViewById(R.id.collectionGridview);

		initializeScreen();
		initializeHandler();
	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}

	ProductCollectionAdapter productCollectionAdapter;

	private void initializeScreen() {
		productCollectionAdapter = new ProductCollectionAdapter(getActivity(),
				collection);
		gridView.setAdapter(productCollectionAdapter);
		productCollectionAdapter.notifyDataSetChanged();

		((CustomTextView) getActivity().findViewById(
				R.id.collectionNameTextView)).setText(mContent);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				addCollectionDetailFragment(position);

			}
		});
	}

	protected void addCollectionDetailFragment(int position) {
		Intent collectionDetailIntent = new Intent(getActivity(),
				CollectionDetailScreen.class);
		collectionDetailIntent.putExtra("collectionObj",
				collection.get(position));
		collectionDetailIntent.putExtra("title",
				((BaseActivity)getActivity()).getScreenTitle());
		
		startActivity(collectionDetailIntent);

	}

	public class ProductCollectionAdapter extends BaseAdapter {

		Context context;

		ArrayList<Collection> collections;
		ViewHolder viewHolder;
		LayoutInflater layoutInflater;

		public ProductCollectionAdapter(Context context,
				ArrayList<Collection> collections) {

			this.context = context;
			this.collections = collections;
			layoutInflater = ((Activity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {

			return collections != null ? collections.size() : 0;
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
						R.layout.collection_gridview_item, null);
				viewHolder.collectionImage = (ImageView) convertView
						.findViewById(R.id.collectionImageView);
				viewHolder.collectionNameText = (CustomTextView) convertView
						.findViewById(R.id.collectionTextView);
				viewHolder.collectionPriceText = (CustomTextView) convertView
						.findViewById(R.id.collectionPriceTextView);
				viewHolder.collectionInStockText = (CustomTextView) convertView
						.findViewById(R.id.collectionInStockTextView);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
				viewHolder.resetViews();
			}
			if (collections.get(position).isCollectiontInStock())
				viewHolder.collectionInStockText.setText("In Stock");
			else
				viewHolder.collectionInStockText.setText("No Stock");

			viewHolder.collectionImage.setImageResource(collections.get(
					position).getCollectionRes());
			viewHolder.collectionNameText.setText(collections.get(position)
					.getCollectionName());
			viewHolder.collectionPriceText.setText("Rs. "
					+ collections.get(position).getCollectionPrice());

			return convertView;
		}

		public class ViewHolder {

			private CustomTextView collectionNameText;
			private CustomTextView collectionPriceText;
			private ImageView collectionImage;
			private CustomTextView collectionInStockText;

			public void resetViews() {

				collectionImage.invalidate();
			}

		}

	}

}
