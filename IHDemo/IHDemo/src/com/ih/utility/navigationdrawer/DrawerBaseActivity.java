package com.ih.utility.navigationdrawer;

import java.util.UUID;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;

import com.actionbarsherlock.view.MenuItem;
import com.ih.BaseActivity;
import com.ih.adapters.SideBarMenuListAdapter;
import com.ih.demo.R;
import com.ih.model.AppPage;
import com.ih.model.AppPageGroup;
import com.ih.model.StaticMenuData;
import com.ih.utility.Utility;
import com.ih.utility.navigationdrawer.menudrawer.MenuDrawer;
import com.ih.utility.navigationdrawer.menudrawer.MenuDrawer.OnDrawerStateChangeListener;

/**
 * The Class DrawerBaseActivity.
 */
public abstract class DrawerBaseActivity extends BaseActivity {

	/** The btn check in. */
	protected ImageView buttonSlideMenu;

	/** The btn width. */
	int btnWidth;

	/** The dialog mode. */
	int dialogMode = -1;

	/** The Constant STATE_MENUDRAWER. */
	private static final String STATE_MENUDRAWER = "com.qonect.utility.navigationdrawer.menudrawer.samples.WindowSample.menuDrawer";

	/** The Constant STATE_ACTIVE_VIEW_ID. */
	private static final String STATE_ACTIVE_VIEW_ID = "com.qonect.utility.navigationdrawer.menudrawer.samples.WindowSample.activeViewId";

	/** The menu drawer. */
	protected MenuDrawer mMenuDrawer;

	/** The context. */
	public static Context context;

	/** The m active view id. */
	protected int activeViewId;

	private ExpandableListView sideBarMenuExpandableListView;
	private SideBarMenuListAdapter sideBarMenuListAdapter;
	public static String prevGrpName, prevChildName;
	private View preItem;

	/**
	 * Called when the activity is first created.
	 * 
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		initialiseDrawer();
		if (savedInstanceState != null) {
			activeViewId = savedInstanceState.getInt(STATE_ACTIVE_VIEW_ID);
		}
		context = this;

	}

	// Calling or not calling loadDrawerData() in onCreate in base activity
	// depends on functionality/requirement
	/**
	 * Load sidebar data.
	 */
	protected void loadDrawerData() {

		// Load the Data(which is saved -shared pref or db) for the Menu Drawer
		// here.
		// Eg. Adapter for a listview, define onItemClickListener.
		// To call this in every child class.
		if (sideBarMenuListAdapter != null) {
			sideBarMenuListAdapter.setMenuAppPageGroups(StaticMenuData
					.getStaticSideBarMenu(DrawerBaseActivity.this));
			sideBarMenuExpandableListView.setAdapter(sideBarMenuListAdapter);
			sideBarMenuListAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * @param isDashBoardActivity
	 * 
	 */
	public void loadPage(UUID group_uuid, UUID child_uuid, String pageType) {

	}

	/**
	 * 
	 */
	protected void callLocationSetting() {
		// Check if SupportMapFragment is already added, If yes remove to
		// prevent "Duplicate id" error.

	}

	/**
	 * Initialise side bar.
	 */
	private void initialiseDrawer() {

		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
		mMenuDrawer.setMenuView(R.layout.drawer_layout);
		mMenuDrawer.setOnDrawerStateChangeListener(drawerStateChangeListener);
		// Initialize the rest of the layout components here.
		sideBarMenuExpandableListView = (ExpandableListView) findViewById(R.id.expandablelistview_sidebar_menu);
		sideBarMenuListAdapter = new SideBarMenuListAdapter(
				DrawerBaseActivity.this,
				StaticMenuData.getStaticSideBarMenu(DrawerBaseActivity.this));
		sideBarMenuExpandableListView.setAdapter(sideBarMenuListAdapter);
		sideBarMenuListAdapter.notifyDataSetChanged();
		sideBarMenuExpandableListView.setGroupIndicator(null);
		prevGrpName = "";
		prevChildName = "";
		sideBarMenuExpandableListView
				.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View view, int groupPosition, int childPosition,
							long id) {
						AppPageGroup selectedAppPageGroup = (AppPageGroup) sideBarMenuListAdapter
								.getGroup(groupPosition);
						sideBarMenuExpandableListView.setItemChecked(
								sideBarMenuExpandableListView
										.getCheckedItemPosition(), false);
						if (preItem != null)
							preItem.setBackgroundResource(R.color.white);
						view.setBackgroundResource(R.color.blue);
						preItem = view;
						AppPage appPage = (AppPage) selectedAppPageGroup
								.getAppPages().get(childPosition);
						if (mMenuDrawer != null) {
							mMenuDrawer.setActiveView(view, childPosition);
							sideBarMenuListAdapter.setActiveChildPosition(
									childPosition, groupPosition);
							if (!TextUtils.isEmpty(prevGrpName)
									&& !TextUtils.isEmpty(prevChildName)) {
								if (prevGrpName.equals(selectedAppPageGroup
										.getPageGroupName())
										&& prevChildName.equals(appPage
												.getPageName())) {
									mMenuDrawer.toggleMenu();

									prevChildName = appPage.getPageName();
									prevGrpName = selectedAppPageGroup
											.getPageGroupName();
									return false;
								}

							}

							prevChildName = appPage.getPageName();
							prevGrpName = selectedAppPageGroup
									.getPageGroupName();
							mMenuDrawer.toggleMenu();

						}
						return false;

					}

				});

		/*
		 * sideBarMenuExpandableListView.setOnGroupClickListener(new
		 * OnGroupClickListener() {
		 * 
		 * @Override public boolean onGroupClick(ExpandableListView parent, View
		 * v, int groupPosition, long id) {
		 * sideBarMenuExpandableListView.setItemChecked
		 * (sideBarMenuExpandableListView.getCheckedItemPosition(),false );
		 * return false; } });
		 */

		sideBarMenuExpandableListView.setItemChecked(4, true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle inState) {
		super.onRestoreInstanceState(inState);
		mMenuDrawer.restoreState(inState.getParcelable(STATE_MENUDRAWER));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android
	 * .os .Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.saveState());
		outState.putInt(STATE_ACTIVE_VIEW_ID, activeViewId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.customtab.baseclasses.CustomTabFragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN
				|| drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}
		displayExitDialog();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Watson#onMenuItemSelected(int,
	 * com.actionbarsherlock.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (mMenuDrawer != null)
				mMenuDrawer.toggleMenu();

			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * Share app. Call default share send/ application of devices.
	 */
	private void shareApp(String subject, String text) {
		try {
			Intent shareAppIntent = new Intent(Intent.ACTION_SEND);

			shareAppIntent.setType("text/plain");
			shareAppIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
			shareAppIntent.putExtra(Intent.EXTRA_TEXT, text);

			startActivity(Intent.createChooser(shareAppIntent,"Shar App"));
		} catch (ActivityNotFoundException e) {
			Utility.showToast(getBaseContext(),
					getString(R.string.activity_not_found_txt));
		}
	}

	/**
	 * Show info app web page.
	 */
	private void infoApp() {
		openExternalLink("www.google.com");
	}

	/**
	 * Show info app web page.
	 */
	private void qonectApp() {
		openExternalLink("www.google.com");
	}

	private void openExternalLink(String link) {
		try {
			Intent infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
			startActivity(infoIntent);
		} catch (ActivityNotFoundException e) {
			Utility.showToast(getBaseContext(),
					getString(R.string.activity_not_found_txt));
		}
	}

	private Builder alertDialogBuilder;
	private AlertDialog dialog;

	/**
	 * 
	 */
	private void displayExitDialog() {

		alertDialogBuilder = new AlertDialog.Builder(this)
				.setCancelable(false)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(getString(R.string.app_name))
				.setMessage(
						getString(R.string.closing_app_confirmation_message_txt))
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						}).setNegativeButton(android.R.string.no, null);

		dialog = alertDialogBuilder.create();
		if (dialog != null && !dialog.isShowing())
			dialog.show();
	}

	OnDrawerStateChangeListener drawerStateChangeListener = new OnDrawerStateChangeListener() {
		@Override
		public void onDrawerStateChange(int oldState, int newState) {

			// Hide keyboard on drawer State Changed
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if(imm!=null && getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null)
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	};

}