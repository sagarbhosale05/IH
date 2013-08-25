package com.ih.activity.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Review;
import com.ih.model.Shop;

public class ShopReviewScreen extends SherlockFragment {

	private String mContent;
	private View root;
	private Context context;

	ListView listView;
	Shop shop;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.shop_review_screen, null);
		context = this.getActivity();

		return root;
	}

	public static ShopReviewScreen newInstance(String content, Shop shop) {
		ShopReviewScreen fragment = new ShopReviewScreen();
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

	ShopReviewAdapter shopReviewAdapter;

	private void initializeScreen() {
		shopReviewAdapter = new ShopReviewAdapter(getActivity(), reviews);
		listView.setAdapter(shopReviewAdapter);
		shopReviewAdapter.notifyDataSetChanged();

	}

	ArrayList<Review> reviews;

	/**
	 * 
	 */
	private void downloadData() {

		reviews = Review.getReviews();
	}

	public class ShopReviewAdapter extends BaseAdapter {

		Context context;

		ArrayList<Review> reviews;
		ViewHolder viewHolder;
		LayoutInflater layoutInflater;

		public ShopReviewAdapter(Context context, ArrayList<Review> reviews) {

			this.context = context;
			this.reviews = reviews;
			layoutInflater = ((Activity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {

			return reviews != null ? reviews.size() : 0;
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
						R.layout.review_listview_row, null);
				viewHolder.reviewImage = (ImageView) convertView
						.findViewById(R.id.reviewImageView);
				viewHolder.reviewsText = (CustomTextView) convertView
						.findViewById(R.id.reviewTextView);
				viewHolder.userNameText = (CustomTextView) convertView
						.findViewById(R.id.userTextView);
				viewHolder.durationText = (CustomTextView) convertView
						.findViewById(R.id.durationTextView);
				viewHolder.ratingBar = (RatingBar) convertView
						.findViewById(R.id.reviewRatings);

				convertView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder) convertView.getTag();

			viewHolder.reviewImage.setImageResource(R.drawable.review);
			viewHolder.reviewsText.setText(reviews.get(position)
					.getReviewText());
			viewHolder.userNameText.setText(reviews.get(position)
					.getReviewUserName());
			viewHolder.durationText.setText(reviews.get(position)
					.getReviewDuration());
			viewHolder.ratingBar.setRating(reviews.get(position)
					.getReviewRating());

			return convertView;
		}

		public class ViewHolder {

			private CustomTextView reviewsText;
			private CustomTextView userNameText;
			private CustomTextView durationText;
			private RatingBar ratingBar;
			private ImageView reviewImage;

		}

	}

}
