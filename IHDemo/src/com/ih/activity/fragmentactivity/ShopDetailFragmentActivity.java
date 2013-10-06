package com.ih.activity.fragmentactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ih.BaseActivity;
import com.ih.IHApp;
import com.ih.activity.fragment.CollectionScreen;
import com.ih.activity.fragment.ShopInfoScreen;
import com.ih.activity.fragment.ShopProductsScreen;
import com.ih.database.DBAdapter;
import com.ih.demo.R;
import com.ih.model.Shop;
import com.ih.utility.Utility;
import com.ih.viewpagerindicator.TabPageIndicator;

public class ShopDetailFragmentActivity extends BaseActivity implements
		OnBackStackChangedListener {

	private ViewPager pager;
	private TabPageIndicator tabHost;
	private static String[] SHOP_DETAIL_TAB_CONTENT;
	private Shop shop;
	private DBAdapter dbAdapter;
	private FragmentAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragments_tabs);
		setScreenTitle("Saturday Shoppee");
		initializeScreen();
		getSupportFragmentManager().addOnBackStackChangedListener(this);
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
		adapter = new FragmentAdapter(getSupportFragmentManager());

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(1);

		pager.setAdapter(adapter);
		pager.setCurrentItem(0, false);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager, 0);

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
			} else {
				this.invalidateOptionsMenu();
				((BaseActivity) this).setActionBarHomeAsUpEnabled(false);
				if (adapter != null)
					adapter.notifyDataSetChanged();
			}
			return;
		}
		if (backEntry != null) {
			String str = backEntry.getName();

			fragment = manager.findFragmentByTag(str);
			if (fragment != null) {
				fragment.onResume();
				this.invalidateOptionsMenu();
				((BaseActivity) this).setActionBarHomeAsUpEnabled(true);
			}
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object
		 * )
		 */
		@Override
		public int getItemPosition(Object object) {

			if (object instanceof ShopInfoScreen) {
				((ShopInfoScreen) object).update();
			}

			if (object instanceof ShopProductsScreen) {
				((ShopProductsScreen) object).update();
			}
			return super.getItemPosition(object);
		}
	}

	@Override
	public void onBackPressed() {

		if (ifDisplayingFragmentIsHome()) {
			Intent intentBroadcast = new Intent(IHApp.FINSIH_ACTION);
			intentBroadcast.putExtra("FINISH", "ACTION.FINISH.LOGOUT");
			sendBroadcast(intentBroadcast);
		} else {
			// A check for sign in / sign up / model details screen is done as
			// we are going to handle back press event on those screens.
			handleBackPressForOtherScreens();
		}
		super.onBackPressed();

	}

	public boolean ifDisplayingFragmentIsHome() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			return true;
		}
		FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
				.getBackStackEntryAt(
						getSupportFragmentManager().getBackStackEntryCount() - 1);
		if (backEntry == null)
			return true;
		String str = backEntry.getName();

		Fragment fragment = getSupportFragmentManager().findFragmentByTag(str);
		if (fragment != null
				&& (fragment instanceof ShopProductsScreen || fragment instanceof ShopInfoScreen)) {
			return true;
		}
		return false;
	}

	@Override
	public void onResume() {

		if (isLogoutProcessing) {
			isLogoutProcessing = false;
			finish();
		} else {
			// This is done because as we are retaining the
			// screen state as it is, it is possible that user opens
			// sign in / sign up screen from two
			// flows,android.support.v4.app.FragmentManager.getBackStackEntryCount()hen
			// we should
			// not allow user to login multiple times.
			// so if the user is already registered and on Home flow ,
			// if sign in or sign up is displayed pop the fragment.
			if (Utility.isUserAlreadySignedUp(this) != 0) {
			}

			onBackStackChanged();
		}
		super.onResume();
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
