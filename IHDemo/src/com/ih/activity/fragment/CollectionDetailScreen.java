package com.ih.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ih.BaseActivity;
import com.ih.customwidgets.CustomTextView;
import com.ih.demo.R;
import com.ih.model.Collection;
import com.ih.viewpagerindicator.TabPageIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CollectionDetailScreen extends BaseActivity {

	private ViewPager pager;
	private TabPageIndicator tabHost;
	private static String[] COLLECTION_TAB_CONTENT;
	private boolean fromDashboardScreen;
	private ImageView mapButton;

	private String mContent;
	private Context context;
	private Collection collection;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.collection_fragment_tab);
		collection = (Collection) getIntent().getExtras().getSerializable(
				"collectionObj");
		screenTitle = getIntent().getExtras().getString("title");
		setScreenTitle();
		initializeScreen();
		initializeHandler();
		this.setActionBarHomeAsUpEnabled(true);

	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}

	private void initializeScreen() {
		ImageLoader imageLoader = ImageLoader.getInstance();
		((CustomTextView) findViewById(R.id.collectionName)).setText(collection
				.getCollectionName());

		if (TextUtils.isEmpty(collection.getCollectiontImageUrl()))
			imageLoader.displayImage(
					"drawable//" + collection.getCollectionRes(),
					((ImageView) findViewById(R.id.collectionImage)));
		else {
			imageLoader.displayImage((collection.getCollectiontImageUrl()
					.startsWith("http") || collection.getCollectiontImageUrl()
					.startsWith("https")) ? collection.getCollectiontImageUrl()
					: "file://" + collection.getCollectiontImageUrl(),
					((ImageView) findViewById(R.id.collectionImage)));

		}

		COLLECTION_TAB_CONTENT = new String[] { "Product Info", "Comments",
				"Loved By" };

		if (pager != null)
			return;
		FragmentAdapter adapter = new FragmentAdapter(
				getSupportFragmentManager());

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(2);

		pager.setAdapter(adapter);
		pager.setCurrentItem(0, false);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		if (fromDashboardScreen) {
			indicator.setViewPager(pager, 2);
		} else {
			indicator.setViewPager(pager, 0);
		}

	}

	class FragmentAdapter extends FragmentStatePagerAdapter {

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;

			switch (position) {
			case 0:
				fragment = CollectionProductInfoScreen.newInstance(
						COLLECTION_TAB_CONTENT[position
								% COLLECTION_TAB_CONTENT.length], collection);
				break;
			case 1:
				fragment = CollectionCommentsScreen
						.newInstance(COLLECTION_TAB_CONTENT[position
								% COLLECTION_TAB_CONTENT.length]);
				break;
			case 2:
				fragment = CollectionLovedByScreen
						.newInstance(COLLECTION_TAB_CONTENT[position
								% COLLECTION_TAB_CONTENT.length]);
				break;
			default:
				break;
			}

			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return COLLECTION_TAB_CONTENT[position
					% COLLECTION_TAB_CONTENT.length];
		}

		@Override
		public int getCount() {
			return COLLECTION_TAB_CONTENT.length;
		}

		@Override
		public Object instantiateItem(ViewGroup arg0, int arg1) {

			return super.instantiateItem(arg0, arg1);
		}

	}

}
