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

import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Trending;

/**
 * @author abhijeet.bhosale
 * 
 */
public class TreadingsScreen extends Fragment {

	private String mContent;
	private View root;
	private Context context;
	ListView listView;

	public static TreadingsScreen newInstance(String content) {
		TreadingsScreen fragment = new TreadingsScreen();
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
		root = (ViewGroup) inflater.inflate(R.layout.trendings_screen, null);
		context = this.getActivity();

		return root;
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

	ArrayList<Trending> trendings;

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}

	TredingAdapter tredingsAdapter;

	private void initializeScreen() {
		tredingsAdapter = new TredingAdapter(getActivity(), trendings);
		listView.setAdapter(tredingsAdapter);
		tredingsAdapter.notifyDataSetChanged();

	}

	/**
	 * 
	 */
	private void downloadData() {

		trendings = Trending.getTrendings();
	}

	public class TredingAdapter extends BaseAdapter {

		Context context;

		ArrayList<Trending> trendings;
		ViewHolder viewHolder;
		LayoutInflater layoutInflater;

		public TredingAdapter(Context context, ArrayList<Trending> trendings) {

			this.context = context;
			this.trendings = trendings;
			layoutInflater = ((Activity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {

			return trendings != null ? trendings.size() : 0;
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
						R.layout.trendings_listview_row, null);
				viewHolder.tredingImage = (ImageView) convertView
						.findViewById(R.id.tredingImage);
				viewHolder.tredingText = (CustomTextView) convertView
						.findViewById(R.id.tredingText);
				viewHolder.tredingShopName = (CustomTextView) convertView
						.findViewById(R.id.tredingShopName);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
				viewHolder.resetViews();

			}
			viewHolder.tredingImage.setImageResource(trendings.get(position)
					.getTrendingRes());
			viewHolder.tredingText.setText(trendings.get(position)
					.getTrendingText());
			viewHolder.tredingShopName.setText(trendings.get(position)
					.getTrendingShopName());

			return convertView;
		}

		public class ViewHolder {

			private CustomTextView tredingShopName;
			private ImageView tredingImage;
			private CustomTextView tredingText;

			public void resetViews() {

				tredingImage.invalidate();
			}
		}

	}

}
