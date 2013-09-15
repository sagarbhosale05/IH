package com.ih.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.demo.R;

public class CollectionLovedByScreen extends SherlockFragment {

	private String mContent;
	private View root;
	private Context context;

	ListView listView;

	public static CollectionLovedByScreen newInstance(String content) {
		CollectionLovedByScreen fragment = new CollectionLovedByScreen();
		fragment.mContent = content;
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
		root = (ViewGroup) inflater.inflate(R.layout.collection_product_loved_by_screen, null);
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
		listView = (ListView) root.findViewById(R.id.listview);

		initializeScreen();
		initializeHandler();

	}

	private void initializeScreen() {
		// TODO Auto-generated method stub
		
	}

}
