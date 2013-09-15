package com.ih.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Collection;

public class CollectionProductInfoScreen extends SherlockFragment {

	private String mContent;
	private View root;
	private Context context;

	private Collection collection;

	public static CollectionProductInfoScreen newInstance(String content,
			Collection collection) {
		CollectionProductInfoScreen fragment = new CollectionProductInfoScreen();
		fragment.mContent = content;
		fragment.collection = collection;
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
		root = (ViewGroup) inflater.inflate(
				R.layout.collection_product_info_screen, null);
		context = this.getActivity();

		return root;
	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

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

		initializeScreen();
		initializeHandler();

	}

	private void initializeScreen() {

		((CustomTextView) root
				.findViewById(R.id.productDescriptionValueTextView))
				.setText(collection.getCollectionDescription());
		((CustomTextView) root.findViewById(R.id.sizeValueTextView))
				.setText(""+collection.getCollectionSizeDimensions());
		((CustomTextView) root.findViewById(R.id.priceValueTextView))
				.setText(""+collection.getCollectionPrice());
		((CustomTextView) root.findViewById(R.id.materialValueTextView))
				.setText(collection.getCollectionMaterial());
		
	}

}
