package com.ih.activity.fragmentactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.ih.BaseActivity;
import com.ih.activity.fragment.CollectionScreen;
import com.ih.activity.fragment.ShopInfoScreen;
import com.ih.activity.fragment.ShopMapScreen;
import com.ih.activity.fragment.ShopProductsScreen;
import com.ih.activity.fragment.ShopReviewScreen;
import com.ih.database.DBAdapter;
import com.ih.demo.R;
import com.ih.model.Shop;
import com.ih.viewpagerindicator.TabPageIndicator;

public class ShopDetailFragmentActivity extends BaseActivity implements
		OnBackStackChangedListener {

	private ViewPager pager;
	TabPageIndicator tabHost;
	private static String[] SHOP_DETAIL_TAB_CONTENT;
	private boolean fromDashboardScreen;
	private ImageView mapButton;
	private Shop shop;
	private View parentView;
	DBAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragments_tabs);

		setScreenTitle("Saturday Shoppee");
		initializeScreen();
		// getSupportFragmentManager().addOnBackStackChangedListener(this);
		this.setActionBarHomeAsUpEnabled(true);
	}

	private void initializeScreen() {
		dbAdapter = new DBAdapter(this);
		try {
			dbAdapter.open();
			shop = dbAdapter.getShop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbAdapter != null)
				dbAdapter.close();
		}
		SHOP_DETAIL_TAB_CONTENT = new String[] { "Store Info", "Products" };

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
			indicator.setViewPager(pager, 0);
		} else {
			indicator.setViewPager(pager, 0);
		}

	}

	@Override
	public void onBackStackChanged() {

		FragmentManager manager = getSupportFragmentManager();
		Fragment fragment = null;

		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			fragment = manager
					.findFragmentByTag(getString(R.string.shop_detail_tag));
			if (fragment != null)
				setScreenTitle(getString(R.string.shop_detail));
		}
		FragmentManager.BackStackEntry backEntry = null;
		if (getSupportFragmentManager().getBackStackEntryCount() > 0)
			backEntry = (this.getSupportFragmentManager()
					.getBackStackEntryAt(this.getSupportFragmentManager()
							.getBackStackEntryCount() - 1));

		if (backEntry != null)
			setScreenTitle(backEntry.getBreadCrumbTitle().toString());

		manager = getSupportFragmentManager();
		fragment = null;

		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			fragment = manager
					.findFragmentByTag(getString(R.string.shop_detail_tag));
			if (fragment != null) {
				fragment.onResume();
				this.invalidateOptionsMenu();
			}
			return;
		}
		String str = backEntry.getName();

		fragment = manager.findFragmentByTag(str);
		if (fragment != null) {
			fragment.onResume();
			this.invalidateOptionsMenu();
			((BaseActivity) this).setActionBarHomeAsUpEnabled(true);
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
				fragment = ShopInfoScreen.newInstance(
						SHOP_DETAIL_TAB_CONTENT[position
								% SHOP_DETAIL_TAB_CONTENT.length], shop);
				break;
			case 1:
				fragment = ShopProductsScreen.newInstance(
						SHOP_DETAIL_TAB_CONTENT[position
								% SHOP_DETAIL_TAB_CONTENT.length], shop);
				break;
			/*
			 * case 2: fragment = ShopMapScreen.newInstance(
			 * SHOP_DETAIL_TAB_CONTENT[position %
			 * SHOP_DETAIL_TAB_CONTENT.length], shop); break;
			 * 
			 * case 3: fragment = ShopReviewScreen.newInstance(
			 * SHOP_DETAIL_TAB_CONTENT[position %
			 * SHOP_DETAIL_TAB_CONTENT.length], shop); break;
			 */
			default:
				break;
			}

			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return SHOP_DETAIL_TAB_CONTENT[position
					% SHOP_DETAIL_TAB_CONTENT.length];
		}

		@Override
		public int getCount() {
			return SHOP_DETAIL_TAB_CONTENT.length;
		}

	}

	@Override
	public void onBackPressed() {
		handleBackPressForOtherScreens();

		super.onBackPressed();
	}

	private boolean handleBackPressForOtherScreens() {
		FragmentManager manager = getSupportFragmentManager();
		Fragment fragment = null;

		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			fragment = manager
					.findFragmentByTag(getString(R.string.shop_detail_tag));
			if (fragment != null) {
				if (fragment instanceof CollectionScreen) {
					return true;
				}

			}
			return false;
		}
		FragmentManager.BackStackEntry backEntry = null;
		if (getSupportFragmentManager().getBackStackEntryCount() > 0)
			backEntry = (this.getSupportFragmentManager()
					.getBackStackEntryAt(this.getSupportFragmentManager()
							.getBackStackEntryCount() - 1));

		if (backEntry == null)
			return false;
		fragment = manager.findFragmentByTag(backEntry.getName());
		if (fragment != null) {
			if (fragment instanceof CollectionScreen) {
				return true;
			}

		}
		return false;
	}

}
