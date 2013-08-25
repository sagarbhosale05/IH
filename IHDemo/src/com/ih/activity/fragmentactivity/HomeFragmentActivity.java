/**
 * 
 */
package com.ih.activity.fragmentactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ih.BaseActivity;
import com.ih.IHApp;
import com.ih.activity.fragment.DashBoardFragment;
import com.ih.activity.fragment.HomeFragment;
import com.ih.activity.fragment.LogInFragment;
import com.ih.activity.fragment.SignUpFragment;
import com.ih.demo.R;
import com.ih.utility.Utility;
import com.ih.utility.navigationdrawer.DrawerBaseActivity;

/**
 * @author abhijeet.bhosale
 * 
 */
public class HomeFragmentActivity extends DrawerBaseActivity implements
		OnBackStackChangedListener {
	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initializeDrawer();
		addHomeFragment();
		getSupportFragmentManager().addOnBackStackChangedListener(this);

	}

	/**
	 * Initialize drawer.
	 */
	private void initializeDrawer() {
		mMenuDrawer.setContentView(R.layout.fragment_container);
		/*buttonSlideMenu = (ImageView) findViewById(R.id.button_slideMenu);
		buttonSlideMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mMenuDrawer.toggleMenu();
			}
		});*/
		loadDrawerData();
	}
	
	private void addHomeFragment() {

		if (Utility.isUserAlreadySignedUp(this) != 0) {
			if (ifDisplayingFragmentIsSignInORSignUp())
				getSupportFragmentManager().popBackStackImmediate();

			addDasBoardFragment();
			return;
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment homeFragment = new HomeFragment();
		transaction.add(R.id.fragment_container, homeFragment,
				getString(R.string.home_tag));
		transaction.setBreadCrumbTitle(getString(R.string.home_screentitle));
		transaction.commit();
	}

	private void addDasBoardFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment dashBoardFragment = new DashBoardFragment();
		transaction.add(R.id.fragment_container, dashBoardFragment,
				getString(R.string.home_tag));
		transaction.setBreadCrumbTitle(getString(R.string.home_screentitle));
		transaction.commit();
	}

	@Override
	public void onResume() {
		if (isLogoutProcessing) {
			isLogoutProcessing = false;
			super.clearBackStack();
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
				if (ifDisplayingFragmentIsSignInORSignUp())
					getSupportFragmentManager().popBackStackImmediate();
				getSupportFragmentManager().popBackStackImmediate();

				//addDasBoardFragment();
			}
		}
		super.onResume();
		// TODO:written publishInstallAsync
		com.facebook.Settings.publishInstallAsync(this,
				getString(R.string.app_id));
	}

	/**
	 * As we don't have separate callback method that is called when fragment
	 * resumes. When user navigates from Fragment1 -> fragment2 When pressed
	 * back from Fragment2, Fragment1's onResume should get called but it does
	 * not get called hence we handle the onresume of everything in this
	 * activity. Whenever any backstack entry is pushed or popped, this callback
	 * is fired.
	 */

	@Override
	public void onBackStackChanged() {
		FragmentManager manager = getSupportFragmentManager();
		Fragment fragment = null;

		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			fragment = manager.findFragmentByTag(getString(R.string.home_tag));
			if (fragment != null)
				setScreenTitle(getString(R.string.home_screentitle));
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
			fragment = manager.findFragmentByTag(getString(R.string.home_tag));
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

	SignUpFragment signUpScreen;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onAttachFragment(android.app.Fragment)
	 */
	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);

		String fragmentSimpleName = fragment.getClass().getSimpleName();

		if (fragmentSimpleName.equals("SignUpScreen"))
			signUpScreen = (SignUpFragment) fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * This is done because we want that when the user presses back from main
	 * home screen, user should exit from the application. As the callback
	 * {@link #onBackPressed()} method is not called inside fragment, we are
	 * handling it inside the parent activity. When the user presses back we
	 * check whether the current screen is home and if yes then we broadcast a
	 * message to all activities to finish them.
	 */
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

	private void handleBackPressForOtherScreens() {
		FragmentManager manager = getSupportFragmentManager();
		Fragment fragment = null;

		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			fragment = manager.findFragmentByTag(getString(R.string.home_tag));
			if (fragment != null) {
				if (fragment instanceof LogInFragment) {
					// ((LogInFragment) fragment).onBackPressed();
					return;
				}
				if (fragment instanceof SignUpFragment) {
					// ((SignUpFragment) fragment).onBackPressed();
					return;
				}

			}
			return;
		}
		FragmentManager.BackStackEntry backEntry = null;
		if (getSupportFragmentManager().getBackStackEntryCount() > 0)
			backEntry = (this.getSupportFragmentManager()
					.getBackStackEntryAt(this.getSupportFragmentManager()
							.getBackStackEntryCount() - 1));

		if (backEntry == null)
			return;
		fragment = manager.findFragmentByTag(backEntry.getName());
		if (fragment != null) {
			if (fragment instanceof LogInFragment) {
				// ((LogInFragment) fragment).onBackPressed();
				return;
			}
			if (fragment instanceof SignUpFragment) {
				// ((SignUpFragment) fragment).onBackPressed();
				return;
			}

		}
	}

	public boolean ifDisplayingFragmentIsHome() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			return true;
		}
		FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
				.getBackStackEntryAt(
						getSupportFragmentManager().getBackStackEntryCount() - 1);
		if (backEntry == null)
			return false;
		String str = backEntry.getName();

		Fragment fragment = getSupportFragmentManager().findFragmentByTag(str);
		if (fragment != null && fragment instanceof HomeFragment) {
			return true;
		}
		return false;
	}

	public boolean ifDisplayingFragmentIsSignInORSignUp() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			return true;
		}
		FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
				.getBackStackEntryAt(
						getSupportFragmentManager().getBackStackEntryCount() - 1);
		if (backEntry == null)
			return false;
		String str = backEntry.getName();

		Fragment fragment = getSupportFragmentManager().findFragmentByTag(str);
		if (fragment != null
				&& (fragment instanceof SignUpFragment || fragment instanceof LogInFragment)) {
			return true;
		}
		return false;
	}

}
