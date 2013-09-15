/**
 * 
 */
package com.ih.activity.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Feed;

/**
 * @author abhijeet.bhosale
 * 
 */
public class FeedsScreen extends Fragment {
	private String mContent;
	private View root;
	private Context context;
	ListView listView;

	public static FeedsScreen newInstance(String content) {
		FeedsScreen fragment = new FeedsScreen();
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
		root = (ViewGroup) inflater.inflate(R.layout.feeds_screen, null);
		context = this.getActivity();

		
		return root;
	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}
	FeedsAdapter feedsAdapter;

	private void initializeScreen() {
		feedsAdapter=new FeedsAdapter(getActivity(), feeds);
		listView.setAdapter(feedsAdapter);
		feedsAdapter.notifyDataSetChanged();

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

	ArrayList<Feed> feeds;

	/**
	 * 
	 */
	private void downloadData() {

		feeds = Feed.getDummyFeeds();
	}

	public class FeedsAdapter extends BaseAdapter {

		Context context;

		ArrayList<Feed> feeds;
		ViewHolder viewHolder;
		LayoutInflater layoutInflater;

		public FeedsAdapter(Context context, ArrayList<Feed> feeds) {

			this.context = context;
			this.feeds = feeds;
			layoutInflater = ((Activity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {

			return feeds != null ? feeds.size() : 0;
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
						R.layout.feed_listview_row, null);
				viewHolder.feedImage = (ImageView) convertView
						.findViewById(R.id.feedImageView);
				viewHolder.feedText = (CustomTextView) convertView
						.findViewById(R.id.feedTextView);

				convertView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder) convertView.getTag();
		
			viewHolder.feedImage.setImageResource(R.drawable.feeds_icon);
			viewHolder.feedText.setText(feeds.get(position).getFeedText());

			return convertView;
		}

		public class ViewHolder {

			private CustomTextView feedText;
			private ImageView feedImage;

		}

	}

}
