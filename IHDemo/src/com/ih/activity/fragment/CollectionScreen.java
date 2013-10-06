package com.ih.activity.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.BaseActivity;
import com.ih.activity.fragmentactivity.AddCollectionFragmentActivity;
import com.ih.customwidgets.CustomTextView;
import com.ih.database.DBAdapter;
import com.ih.demo.R;
import com.ih.model.Collection;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CollectionScreen extends SherlockFragment {

	private String mContent;
	private int productId;
	private View root;
	private Context context;
	private ArrayList<Collection> collection;
	private GridView gridView;
	private DBAdapter dbAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.collection_screen, null);
		context = this.getActivity();

		return root;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		initializeScreen();
	}

	public static CollectionScreen newInstance(String content,
			ArrayList<Collection> collection) {
		CollectionScreen fragment = new CollectionScreen();
		fragment.mContent = content;
		fragment.collection = collection;
		return fragment;
	}

	public static CollectionScreen newInstance(int productId, String productName) {
		CollectionScreen fragment = new CollectionScreen();
		fragment.productId = productId;
		fragment.mContent = productName;
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gridView = (GridView) root.findViewById(R.id.collectionGridview);
		((BaseActivity) getActivity()).setActionBarHomeAsUpEnabled(true);
		initializeScreen();
		initializeHandler();
	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}

	ProductCollectionAdapter productCollectionAdapter;

	private void initializeScreen() {
		dbAdapter = new DBAdapter(getActivity());
		try {
			dbAdapter.open();
			collection = dbAdapter.getCollections("" + productId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbAdapter != null)
				dbAdapter.close();
		}
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

		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				showMenu(arg2);
				return false;
			}
		});
	}

	private void showMenu(final int position) {
		AlertDialog dialog = new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_alert).setTitle("IH")
				.setMessage("Do you need to?").create();
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Edit",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						Intent intent = new Intent(getActivity(),
								AddCollectionFragmentActivity.class);
						intent.putExtra("collectionObj",
								collection.get(position));
						startActivity(intent);
					}
				});
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Delete",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						deleteProduct(position);
					}
				});

		if (dialog != null && dialog.isShowing() == false)
			dialog.show();

	}

	protected void deleteProduct(int position) {
		collection.remove(position);
		productCollectionAdapter.notifyDataSetChanged();
		if (dbAdapter != null) {
			dbAdapter.open();
			dbAdapter.deleteCollections(new String[] { ""
					+ collection.get(position).getCollectionId() });
			dbAdapter.close();
		}
	}

	protected void addCollectionDetailFragment(int position) {
		Intent collectionDetailIntent = new Intent(getActivity(),
				CollectionDetailScreen.class);
		collectionDetailIntent.putExtra("collectionObj",
				collection.get(position));
		collectionDetailIntent.putExtra("title",
				((BaseActivity) getActivity()).getScreenTitle());

		startActivity(collectionDetailIntent);

	}

	public class ProductCollectionAdapter extends BaseAdapter {

		private Context context;

		private ArrayList<Collection> collections;
		private ViewHolder viewHolder;
		private LayoutInflater layoutInflater;
		private ImageLoader imageLoader;

		public ProductCollectionAdapter(Context context,
				ArrayList<Collection> collections) {

			this.context = context;
			this.collections = collections;
			layoutInflater = ((Activity) context).getLayoutInflater();
			imageLoader = ImageLoader.getInstance();
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

			viewHolder.collectionInStockText.setText(collections.get(position)
					.isCollectiontInStock() == 0 ? "In Stock" : collections
					.get(position).isCollectiontInStock() == 1 ? "No Stock"
					: "Soon");

			if (TextUtils.isEmpty(collections.get(position)
					.getCollectiontImageUrl()))
				if (TextUtils.isEmpty(collections.get(position)
						.getCollectionRes()))
					viewHolder.collectionImage
							.setImageResource(R.drawable.ic_launcher);
				else
					imageLoader.displayImage(
							"drawable://"
									+ context.getResources().getIdentifier(
											collections.get(position)
													.getCollectionRes(),
											"drawable",
											context.getPackageName()),
							viewHolder.collectionImage);
			// viewHolder.collectionImage.setImageResource(context.getResources().getIdentifier(collections.get(position).getCollectionRes(),
			// "drawable", context.getPackageName()));

			else {
				imageLoader.displayImage(
						(collections.get(position).getCollectiontImageUrl()
								.startsWith("http") || collections
								.get(position).getCollectiontImageUrl()
								.startsWith("https")) ? collections.get(
								position).getCollectiontImageUrl() : "file:/"
								+ collections.get(position)
										.getCollectiontImageUrl(),
						viewHolder.collectionImage);

			}
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
