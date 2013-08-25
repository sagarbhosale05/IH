/**
 * 
 */
package com.ih;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.Session;
import com.ih.activity.fragmentactivity.AboutScreenFragmentActivity;
import com.ih.activity.fragmentactivity.AccountDetailFragmentActivity;
import com.ih.activity.fragmentactivity.AddProductFragmentActivity;
import com.ih.activity.fragmentactivity.EditProfileFragmentActivity;
import com.ih.activity.fragmentactivity.HomeFragmentActivity;
import com.ih.activity.fragmentactivity.PrivacyPolicyFragmentActivity;
import com.ih.activity.fragmentactivity.TermsAndConditionsFragmentActivity;
import com.ih.asynctasks.LogOutTask;
import com.ih.demo.R;
import com.ih.utility.Constants;
import com.ih.utility.Utility;
import com.twitter.twitterutil.TwitterUtility;

/**
 * @author abhijeet.bhosale
 * 
 */
public class BaseActivity extends SherlockFragmentActivity implements
		OnItemSelectedListener {

	/**
	 * The flag is responsible for check if the {@link #onResume()} is called
	 * after {@link #onCreate(Bundle)} or when the user has pressed back button.
	 */
	public static boolean isOnCreateCalled;

	protected String screenTitle;
	private boolean shouldShowNetworkPopup = false;
	protected AlertDialog dialog;
	protected String screenTitleKey = "screenTitle";

	/**
	 * The variable indicates whether the logout process is going on. This check
	 * is necessary while we logout from the app. we navigate user to the home
	 * screen and while doing it we finish all the activities and only for home
	 * screen we just pop the backstack. This flag will be checked in
	 * {@link HomeFragmentActivity#onResume()}
	 */
	protected static boolean isLogoutProcessing = false;

	/**
	 * The method is used for displaying the menu. The method checks if the user
	 * is logged in and hides the menu items appropriately.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		if (Utility.isUserAlreadySignedUp(this) == 0) {
			menu.findItem(R.id.signOut).setVisible(false);
			menu.findItem(R.id.account_details).setVisible(false);
		}
		if(this.getClass().getSimpleName().equalsIgnoreCase(EditProfileFragmentActivity.class.getSimpleName()))
		{
			menu.findItem(R.id.editProfile).setVisible(false);
		}
		if(this.getClass().getSimpleName().equalsIgnoreCase(AddProductFragmentActivity.class.getSimpleName()))
		{
			menu.findItem(R.id.addProduct).setVisible(false);
		}
		return true;
	}

	public void setScreenTitle(String title) {
		screenTitle = title;

		final ActionBar ab = getSupportActionBar();
		ab.setDisplayShowTitleEnabled(false);

		LayoutInflater inflater = LayoutInflater.from(this);
		View customView = inflater.inflate(R.layout.custom_textview, null);

		TextView titleTV = (TextView) customView
				.findViewById(R.id.action_custom_title);
		titleTV.setText(screenTitle);
		ab.setCustomView(customView);
		ab.setDisplayShowCustomEnabled(true);
	}

	private void addMenu() {
		final ActionBar ab = getSupportActionBar();
		LayoutInflater inflater = LayoutInflater.from(this);
		View customView = inflater.inflate(R.layout.custom_textview, null);
		ab.setCustomView(customView);
		setActionBarForMainActivity();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.header_bg_1px));

	}

	private void setActionBarForMainActivity() {
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayShowHomeEnabled(true);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		ab.setDisplayUseLogoEnabled(false);
		ab.setHomeButtonEnabled(true);
		ab.setDisplayShowTitleEnabled(true);
	}

	/**
	 * @param shouldSetAsUpEnabled
	 * 
	 *            Home as up enabled means that home icon situated on the top
	 *            left corner of action bar will display arrow and it will
	 *            indicate that the the screen is navigating.
	 */

	public void setActionBarHomeAsUpEnabled(boolean shouldSetAsUpEnabled) {
		ActionBar ab = getSupportActionBar();
		ab.setDisplayShowHomeEnabled(true);
		ab.setDisplayHomeAsUpEnabled(shouldSetAsUpEnabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		try {
			isOnCreateCalled = true;
			addMenu();
			shouldShowNetworkPopup = true;

			if (!BaseActivity.this.getClass()
					.equals(HomeFragmentActivity.class))
				registerReceiver(logoutReciver, new IntentFilter(
						IHApp.FINSIH_ACTION));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setBackgroundResource(int resId) {
		if (((LinearLayout) findViewById(R.id.container)) != null)
			((LinearLayout) findViewById(R.id.container))
					.setBackgroundResource(resId);
	}

	public void setBackgroundDrawable(Drawable resId) {
		if (((LinearLayout) findViewById(R.id.container)) != null)
			((LinearLayout) findViewById(R.id.container))
					.setBackgroundDrawable(resId);
	}

	public void setBackgroundColor(int color) {
		if (((LinearLayout) findViewById(R.id.container)) != null)
			((LinearLayout) findViewById(R.id.container))
					.setBackgroundColor(color);
	}

	/**
	 * Called when the activity is brought to the front and it can interact with
	 * the user.
	 */
	@Override
	public void onResume() {
		super.onResume();

		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectionReciever, filter);
		this.invalidateOptionsMenu();
	}

	/**
	 * The method is called when the menu items from the menu is selected. The
	 * method checks for selected item id and open screen accordingly.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item.setChecked(true);
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			if (this.getClass().equals(HomeFragmentActivity.class)) {
				clearBackStack();
			} else {
				intent = new Intent(this, HomeFragmentActivity.class);
				startActivity(intent);
			}
			return true;

		case R.id.account_details:
			int loggedInWith = Utility.checkNativeLogin(this);
			if (Utility.checkNativeLogin(this) != Constants.UNIQUE_APP_ID) {
				dialog = new AlertDialog.Builder(this).create();
				if (loggedInWith == Constants.UNIQUE_TWITTER_ID)
					dialog.setMessage(getResources().getString(
							R.string.logged_in_with_tw));
				else if (loggedInWith == Constants.UNIQUE_FACEBOOK_ID)
					dialog.setMessage(getResources().getString(
							R.string.logged_in_with_fb));

				dialog.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(R.string.ok_btn_text),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				dialog.show();

			} else {
				intent = new Intent(this, AccountDetailFragmentActivity.class);
				intent.putExtra(screenTitleKey, item.getTitle());
				startActivity(intent);
			}
			return true;
		case R.id.termsAndCondition:
			intent = new Intent(this, TermsAndConditionsFragmentActivity.class);
			intent.putExtra(screenTitleKey, item.getTitle());
			startActivity(intent);
			return true;
		case R.id.privacy:
			intent = new Intent(this, PrivacyPolicyFragmentActivity.class);
			intent.putExtra(screenTitleKey, item.getTitle());
			startActivity(intent);
			return true;
		case R.id.about:
			intent = new Intent(this, AboutScreenFragmentActivity.class);
			intent.putExtra(screenTitleKey, item.getTitle());
			startActivity(intent);
			return true;
		case R.id.signOut:
			dialog = new AlertDialog.Builder(this).create();
			dialog.setMessage(getResources().getString(R.string.logout_msg));
			dialog.setButton(AlertDialog.BUTTON1,
					getString(R.string.cancel_btn_text),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			dialog.setButton(AlertDialog.BUTTON2,
					getString(R.string.Settings_SignOut),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							new LogOutTask(BaseActivity.this, "Signing out...",
									BaseActivity.this).execute((Void[]) null);
						}
					});
			dialog.show();
			return true;

		case R.id.editProfile:
			intent = new Intent(this, EditProfileFragmentActivity.class);
			intent.putExtra(screenTitleKey, item.getTitle());
			startActivity(intent);
			return true;
		case R.id.addProduct:
			intent = new Intent(this, AddProductFragmentActivity.class);
			intent.putExtra(screenTitleKey, item.getTitle());
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * {@link SharedPreferences} will be cleared. The method will logout the
	 * user from twitter and facebook.
	 */
	public void signOutUser() {

		if (Session.getActiveSession() != null) {
			Session.getActiveSession().closeAndClearTokenInformation();
			Session.setActiveSession(null);
		}
		Context context = this;

		SharedPreferences preferences = context.getSharedPreferences(
				context.getString(R.string.shared_pref_credentials),
				Context.MODE_PRIVATE);
		Editor credentialsEditor = preferences.edit();
		credentialsEditor.clear().commit();

		try {
			new TwitterUtility(context).clearCredentials();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcastLogoutAction() {
		// we are checking whether the current activity is a home screen
		// because if we are on home screen and calling logout from the same
		// then we just need to broadcast a logout action and clear the
		// home backstack as its the screen currently visible to user.
		// when the user logout we want to navigate to the very first screen.
		// whenever any activity other than home receives a broadcast as logout
		// then that activity finishes itself.
		// home will not be finished as we want to navigate to home screen.
		if (this instanceof HomeFragmentActivity) {
			clearBackStack();
		}
		Intent intentBroadcast = new Intent(IHApp.FINSIH_ACTION);
		intentBroadcast.putExtra("FINISH", "ACTION.FINISH.LOGOUT");
		sendBroadcast(intentBroadcast);

	}

	/**
	 * Method is called when the activity goes to background. Unregister the
	 * receiver then.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.e("pausing before destroying", "pausing before destroying");
		unregisterReceiver(connectionReciever);
		shouldShowNetworkPopup = false;
		isOnCreateCalled = false;

	}

	/**
	 * {@link BroadcastReceiver} for network connection. Whenever the network
	 * connection goes off the system will send broadcast and
	 * {@link BroadcastReceiver#onReceive(Context, Intent)} will be called.
	 */
	private BroadcastReceiver connectionReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (shouldShowNetworkPopup) {
				boolean noConnectivity = intent.getBooleanExtra(
						ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
				if (noConnectivity) {
					dialog = new AlertDialog.Builder(context)
							.setIcon(android.R.drawable.ic_dialog_info)
							.setTitle(
									getResources()
											.getString(
													R.string.no_network_connection_title))
							.setMessage(
									getResources().getString(
											R.string.no_network_connection))
							.create();
					dialog.setButton(AlertDialog.BUTTON_POSITIVE,
							getString(R.string.settings_btn_text),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									Intent intent = new Intent(
											android.provider.Settings.ACTION_WIRELESS_SETTINGS);
									startActivity(intent);
								}
							});

					if (dialog != null && dialog.isShowing() == false)
						dialog.show();
				}
			}
		}
	};

	protected BroadcastReceiver logoutReciver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String action_finish = arg1.getStringExtra("FINISH");
			isLogoutProcessing = true;
			if (action_finish.equalsIgnoreCase("ACTION.FINISH.LOGOUT")) {
				if (!BaseActivity.this.getClass().equals(
						HomeFragmentActivity.class))
					BaseActivity.this.finish();
				// this line unregister the receiver,so that i run only one time
				// and when next time logout is clicked then again it called
				unregisterReceiver(logoutReciver);
			}
		}
	};

	/**
	 * Set title in action bar.
	 */
	protected void setScreenTitle() {
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayShowTitleEnabled(false);

		LayoutInflater inflater = LayoutInflater.from(this);
		View customView = inflater.inflate(R.layout.custom_textview, null);

		TextView titleTV = (TextView) customView
				.findViewById(R.id.action_custom_title);
		titleTV.setText(screenTitle);
		ab.setCustomView(customView);
		ab.setDisplayShowCustomEnabled(true);
	}

	public String getScreenTitle() {
		return screenTitle;
	}

	public void clearBackStack() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
			fragmentManager.popBackStack();
		}
		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
			Fragment fragment = fragmentManager
					.findFragmentByTag(getString(R.string.home_tag));
			if (fragment != null)
				fragment.onResume();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.FragmentActivity#onNewIntent(android.content.Intent
	 * )
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener
	 * #onItemSelected(com.actionbarsherlock.internal.widget.IcsAdapterView,
	 * android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(IcsAdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener
	 * #onNothingSelected(com.actionbarsherlock.internal.widget.IcsAdapterView)
	 */
	@Override
	public void onNothingSelected(IcsAdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
