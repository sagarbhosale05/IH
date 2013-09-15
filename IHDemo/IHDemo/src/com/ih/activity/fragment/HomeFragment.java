package com.ih.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.BaseActivity;
import com.ih.activity.fragmentactivity.ShopDetailFragmentActivity;
import com.ih.demo.R;
import com.ih.utility.Utility;

/**
 * @author pragati.deshmukh Main landing screen of the application displaying
 *         options for used bicycles, sign in , signup
 */
public class HomeFragment extends SherlockFragment implements OnClickListener {
	private Button btnSignIn, btnSignUp;
	private View parentView;

	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		parentView = arg0.inflate(R.layout.home_screen, null);
		initializeScreen();
		return parentView;
	}

	@Override
	public void onResume() {
		// The check is done again in onResume apart from onCreateView
		// because if the user logs out from another activity, the user
		// gets navigated to the home screen where onResume is called.
		// We need refresh screen here also to make modifications to the UI.
		checkIfUserIsSignedInAndHideUI();
		((BaseActivity) getActivity()).setActionBarHomeAsUpEnabled(false);
		((BaseActivity) this.getActivity()).invalidateOptionsMenu();

		if (Utility.isUserAlreadySignedUp(getActivity()) != 0) {

			addShopDashBoardFragment();
		}
		super.onResume();

	}

	private void initializeScreen() {
		btnSignIn = (Button) parentView.findViewById(R.id.btnHomeSignIn);
		btnSignUp = (Button) parentView.findViewById(R.id.btnSignUp);
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);

		checkIfUserIsSignedInAndHideUI();
		((BaseActivity) getActivity())
				.setScreenTitle(getString(R.string.home_screentitle));

		String android_id = Secure.getString(
				getActivity().getContentResolver(), Secure.ANDROID_ID);

		Log.e("ANDROID_ID", android_id);

	}

	/**
	 * If the user is signed up, then there is no need to display sign in and
	 * sign up buttons hence the check.
	 */
	private void checkIfUserIsSignedInAndHideUI() {
		if (Utility.isUserAlreadySignedUp(this.getActivity()) != 0) {
			btnSignIn.setVisibility(View.INVISIBLE);
			btnSignUp.setVisibility(View.INVISIBLE);

		} else {
			btnSignIn.setVisibility(View.VISIBLE);
			btnSignUp.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHomeSignIn:
			launchSignInScreen();
			break;
		case R.id.btnSignUp:
			launchSignUpScreen();
			break;
		}
	}

	private void launchSignUpScreen() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment signUpFragment = new SignUpFragment();
		transaction.add(R.id.fragment_container, signUpFragment,
				getString(R.string.home_tag));
		transaction.addToBackStack(getString(R.string.home_tag));
		transaction.setBreadCrumbTitle(getString(R.string.sign_up_screentitle));
		transaction.commit();
	}

	private void launchSignInScreen() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment signInFragment = new LogInFragment();
		transaction.add(R.id.fragment_container, signInFragment,
				getString(R.string.home_tag));
		transaction.addToBackStack(getString(R.string.home_tag));
		transaction.setBreadCrumbTitle(getString(R.string.sign_in_screentitle));
		transaction.commit();
	}

	private void addDashBoardFragment() {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment dashBoardFragment = new DashBoardFragment();
		transaction.add(R.id.fragment_container, dashBoardFragment,
				getString(R.string.home_tag));
		transaction.setBreadCrumbTitle(getString(R.string.home_screentitle));
		transaction.commit();
	}

	private void addShopDashBoardFragment() {
		Intent shopDetails = new Intent(getActivity(),
				ShopDetailFragmentActivity.class);
		startActivity(shopDetails);
	}

}
