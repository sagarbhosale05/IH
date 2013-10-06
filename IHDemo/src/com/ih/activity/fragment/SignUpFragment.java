package com.ih.activity.fragment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.internal.SessionAuthorizationType;
import com.facebook.internal.SessionTracker;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.ih.BaseActivity;
import com.ih.PreferencesManager;
import com.ih.activity.fragmentactivity.ShopDetailFragmentActivity;
import com.ih.customwidgets.CustomButton;
import com.ih.customwidgets.CustomEditText;
import com.ih.database.DBAdapter;
import com.ih.demo.R;
import com.ih.model.User;
import com.ih.utility.Constants;
import com.ih.utility.Globals;
import com.ih.utility.Utility;
import com.twitter.data.UserData;
import com.utility.TwitterPostCallback;
import com.utility.TwitterUtil;

/**
 * @author pragati.deshmukh Screen that enables user to sign up to the
 *         application. Signing up enables features as favorites, change
 *         password. the user can signup through native login credential or
 *         facebook and twitter.
 * 
 */
public class SignUpFragment extends SherlockFragment implements
		OnClickListener, TwitterPostCallback, OnEditorActionListener {
	private CustomButton btnSignUp, btnConnectWithFb, btnConnectWithTw;
	private CustomEditText edtTxtFirstName, edtTxtLastName, edtTxtEmailId,
			edtTxtPassword;
	private String firstName, lastName, emailId, password;
	private TwitterUtil twitterUtil;
	private Handler handler;
	private UserData data;
	public int registrationHappeningThrough = 0;
	private AlertDialog dialog;
	private View parentView;
	private boolean navigatingFromModelDetails;
	private String accountId;
	private GraphUser user = null;
	private SessionTracker sessionTracker;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {

		parentView = paramLayoutInflater.inflate(R.layout.sign_up_layout, null);
		sessionTracker = new SessionTracker(getActivity(),
				new LoginButtonCallback(), null, false);
		initializeScreen();
		initializeHandler();

		// This is done because the events like onTouch, onClick are
		// passed to the fragment below the current fragment hence
		// we are adding a touch listener to the whole and returning true
		// which means that the touch event is handled.
		parentView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		return parentView;
	}

	@Override
	public void onPause() {
		super.onPause();
		Utility.hideSoftKeyboard(SignUpFragment.this.getActivity());
	}

	private void initializeHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				switch (msg.what) {
				case Constants.GET_TWITTER_ID:
					new TwitterIdTask().execute((Void[]) null);
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

	public void onBackPressed() {
		if (twitterUtil != null)
			twitterUtil.clearTasks();
	}

	@Override
	public void onResume() {
		if (getTag().equalsIgnoreCase(getString(R.string.home_tag)))
			((BaseActivity) getActivity()).setActionBarHomeAsUpEnabled(true);

		((BaseActivity) getActivity())
				.setBackgroundResource(R.drawable.activity_bg);
		super.onResume();
	}

	private void initializeScreen() {

		btnSignUp = (CustomButton) parentView.findViewById(R.id.btnSignUp);

		((BaseActivity) getActivity())
				.setScreenTitle(getString(R.string.sign_up_screentitle));
		btnConnectWithTw = (CustomButton) parentView
				.findViewById(R.id.btnConnectWithTw);
		btnConnectWithFb = (CustomButton) parentView
				.findViewById(R.id.btnLoginWithFb);
		btnConnectWithFb.setOnClickListener(this);
		edtTxtEmailId = (CustomEditText) parentView
				.findViewById(R.id.editTextEmailId);
		edtTxtFirstName = (CustomEditText) parentView
				.findViewById(R.id.editTextFirstName);
		edtTxtLastName = (CustomEditText) parentView
				.findViewById(R.id.editTextLastName);
		edtTxtPassword = (CustomEditText) parentView
				.findViewById(R.id.editTextPassword);

		edtTxtPassword.setOnEditorActionListener(this);
		btnSignUp.setOnClickListener(this);
		btnConnectWithTw.setOnClickListener(this);

		dialog = new AlertDialog.Builder(SignUpFragment.this.getActivity())
				.create();
		dialog.setButton(AlertDialog.BUTTON_NEUTRAL,
				getString(R.string.ok_btn_text),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		if (PreferencesManager.getIsIceCreamSandWich(SignUpFragment.this
				.getActivity())) {
			edtTxtEmailId.setTextColor(getResources().getColor(
					R.color.grey_shade11));
			edtTxtPassword.setTextColor(getResources().getColor(
					R.color.grey_shade11));
			edtTxtFirstName.setTextColor(getResources().getColor(
					R.color.grey_shade11));
			edtTxtLastName.setTextColor(getResources().getColor(
					R.color.grey_shade11));
		}
		checkForCallingScreen();

		/*
		 * TextView myCustomLink = (TextView) parentView
		 * .findViewById(R.id.agree_termsOfUse);
		 */
		/*
		 * String text = getString(R.string.signupAgreeToTerms);
		 * SpannableStringBuilder stringBuilder = new
		 * SpannableStringBuilder(text);
		 */
		/*
		 * final MyCustomSpannable customSpannable = new MyCustomSpannable(
		 * getString(R.string.termsOfUse)) {
		 * 
		 * @Override public void onClick(View widget) { Intent intent = new
		 * Intent(SignUpFragment.this.getActivity(),
		 * TermsAndConditionsFragmentActivity.class);
		 * intent.putExtra("screenTitle",
		 * getResources().getString(R.string.Settings_TermsOfUse));
		 * startActivity(intent); } };
		 */
		/*
		 * stringBuilder.setSpan(customSpannable, 34, text.length(),
		 * Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		 */

		/*
		 * myCustomLink.setText(stringBuilder, BufferType.SPANNABLE);
		 * myCustomLink.setMovementMethod(LinkMovementMethod.getInstance());
		 */

		properties.setReadPermissions(Arrays.asList("email"),
				sessionTracker.getOpenSession());

	}

	class MyCustomSpannable extends ClickableSpan {
		String Url;

		public MyCustomSpannable(String Url) {
			this.Url = Url;
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			// Customize your Text Look if required
			ds.setColor(getResources().getColor(R.color.link_color_blue));
		}

		@Override
		public void onClick(View widget) {
		}

		public String getUrl() {
			return Url;
		}
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.btnSignUp:
			if (checkNoFieldEmpty()) {
				if (Utility.checkEmailCorrect(edtTxtEmailId.getText()
						.toString())) {
					registrationHappeningThrough = Constants.UNIQUE_APP_ID;
					signUpUser();

				} else {
					dialog.setMessage(getString(R.string.valid_email));
					dialog.show();
				}
			} else {
				dialog.setMessage(getString(R.string.mandatory_fields));
				dialog.show();
			}
			break;
		case R.id.btnConnectWithTw:
			registrationHappeningThrough = Constants.UNIQUE_TWITTER_ID;
			connectWithTwitter();

			break;
		case R.id.btnLoginWithFb:
			registrationHappeningThrough = Constants.UNIQUE_FACEBOOK_ID;

			connectWithFacebook();
			break;

		}
	}

	private boolean checkNoFieldEmpty() {
		if (edtTxtFirstName.getText().toString().trim().length() == 0
				|| edtTxtLastName.getText().toString().trim().length() == 0
				|| edtTxtEmailId.getText().toString().trim().length() == 0
				|| edtTxtPassword.getText().toString().trim().length() == 0) {
			if (edtTxtFirstName.getText().toString().trim().length() == 0)
				edtTxtFirstName.setText("");
			if (edtTxtLastName.getText().toString().trim().length() == 0)
				edtTxtLastName.setText("");
			if (edtTxtEmailId.getText().toString().trim().length() == 0)
				edtTxtEmailId.setText("");
			if (edtTxtPassword.getText().toString().trim().length() == 0)
				edtTxtPassword.setText("");
			return false;
		}
		return true;
	}

	private void connectWithTwitter() {
		try {
			twitterUtil = new TwitterUtil(SignUpFragment.this.getActivity(),
					this, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET,
					Constants.TWICPIC_API_KEYS, Constants.YFROG_API_KEYS);
			twitterUtil.loginTwitter();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connectWithFacebook() {

		final Session openSession = Session.getActiveSession();
		if (openSession != null && openSession.getState().isOpened()) {
			// If the Session is currently open, it must mean we can directly
			// fetch user info.
			fetchUserInfo();

		} else {
			Session currentSession = Session.getActiveSession();
			if (currentSession == null || currentSession.getState().isClosed()) {
				sessionTracker.setSession(null);
				Session session = new Session.Builder(getActivity())
						.setApplicationId(
								getActivity().getString(R.string.app_id))
						.build();
				Session.setActiveSession(session);
				currentSession = session;
			}
			if (!currentSession.isOpened()) {
				Session.OpenRequest openRequest = null;
				/* if (this != null) { */
				openRequest = new Session.OpenRequest(this);
				/*
				 * } else if (context instanceof Activity) { openRequest = new
				 * Session.OpenRequest( (Activity) context); }
				 */
				if (openRequest != null) {
					openRequest.setDefaultAudience(properties.defaultAudience);
					openRequest.setPermissions(properties.permissions);
					openRequest.setLoginBehavior(properties.loginBehavior);
					openRequest.setIsLegacy(true);
					openRequest.setCallback(new LoginButtonCallback());

					if (SessionAuthorizationType.PUBLISH
							.equals(properties.authorizationType)) {
						currentSession.openForPublish(openRequest);
					} else {
						currentSession.openForRead(openRequest);
					}
				}
			}
		}

	}

	private void signUpUser() {
		DBAdapter dbAdapter = new DBAdapter(getActivity());

		firstName = edtTxtFirstName.getText().toString();
		lastName = edtTxtLastName.getText().toString();
		emailId = edtTxtEmailId.getText().toString();
		password = edtTxtPassword.getText().toString();

		accountId = emailId;

		try {
			dbAdapter.open();
			User userData = new User();
			userData.setEmailId(emailId);
			userData.setFirstName(firstName);
			userData.setLastName(lastName);
			userData.setPassword(password);
			if (dbAdapter.insertNewUser(userData) != -1) {
				Toast.makeText(getActivity().getBaseContext(), "User created",
						Toast.LENGTH_SHORT).show();
				saveUserDetails();
				return;
			}
			Toast.makeText(getActivity().getBaseContext(),
					"User not created. Please try later.", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			// TODO: handle exception
		}

		finally {
			if (dbAdapter != null)
				dbAdapter.close();
		}
	}

	@Override
	public void responseFromTwitterUtil(boolean wasPostSuccessful, int errorCode) {
		handler.sendEmptyMessage(Constants.GET_TWITTER_ID);
	}

	private class TwitterIdTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(
					SignUpFragment.this.getActivity(), "",
					getString(R.string.signup_progress_text));
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (twitterUtil == null) {
					AlertDialog dialog = new AlertDialog.Builder(
							SignUpFragment.this.getActivity())
							.setTitle(getString(R.string.alertTitle))
							.setMessage(
									getString(R.string.loginToTwitterForDetails))
							.create();
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
					data = twitterUtil.getUserDetails();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			sendTwitterDetailsToServer();
			super.onPostExecute(result);
		}

	}

	/**
	 * Create namevaluepair with user's information including firstname,
	 * lastname, twitter id. As twitter does not allow fetching email id, we
	 * pass screen name instead.
	 */
	public void sendTwitterDetailsToServer() {
		if (data == null)
			return;

		String[] names = data.getfullName().split(" ");

		String firstName = "";
		String lastName = "";

		if (names.length > 0) {
			firstName = names[0];
			if (names.length > 1)
				lastName = names[1];
		}

		accountId = data.getScreenName();
		saveUserDetails();

	}

	/**
	 * Create namevaluepair with user's information including firstname,
	 * lastname, email, facebook id .
	 */
	public void sendFacebookDetailsToServer(String firstName, String lastName,
			String email, String fbId) {

		registrationHappeningThrough = Constants.UNIQUE_FACEBOOK_ID;

		accountId = email;
		saveUserDetails();

	}

	private void startApplication() {
		BaseActivity.isLogoutProcessing=false;
		Intent intent = new Intent(	getActivity(), ShopDetailFragmentActivity.class);
		getActivity().startActivity(intent);
		getActivity().finish();
	
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE
				|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			((ScrollView) parentView.findViewById(R.id.layoutScrollView))
					.fullScroll(ScrollView.FOCUS_DOWN);
			Utility.hideSoftKeyboard(SignUpFragment.this.getActivity());
			return true;
		} else
			return false;
	}

	private void checkForCallingScreen() {
		if (getArguments() != null)
			navigatingFromModelDetails = getArguments().getBoolean(
					"navigatingFromModelDetails", false);
	}

	private LoginButtonProperties properties = new LoginButtonProperties();

	static class LoginButtonProperties {
		private SessionDefaultAudience defaultAudience = SessionDefaultAudience.FRIENDS;
		private List<String> permissions = Collections.<String> emptyList();
		private SessionAuthorizationType authorizationType = null;
		private OnErrorListener onErrorListener;
		private SessionLoginBehavior loginBehavior = SessionLoginBehavior.SUPPRESS_SSO;
		private Session.StatusCallback sessionStatusCallback;

		public void setOnErrorListener(OnErrorListener onErrorListener) {
			this.onErrorListener = onErrorListener;
		}

		public OnErrorListener getOnErrorListener() {
			return onErrorListener;
		}

		public void setDefaultAudience(SessionDefaultAudience defaultAudience) {
			this.defaultAudience = defaultAudience;
		}

		public SessionDefaultAudience getDefaultAudience() {
			return defaultAudience;
		}

		public void setReadPermissions(List<String> permissions, Session session) {
			if (SessionAuthorizationType.PUBLISH.equals(authorizationType)) {
				throw new UnsupportedOperationException(
						"Cannot call setReadPermissions after setPublishPermissions has been called.");
			}
			if (validatePermissions(permissions, SessionAuthorizationType.READ,
					session)) {
				this.permissions = permissions;
				authorizationType = SessionAuthorizationType.READ;
			}
		}

		public void setPublishPermissions(List<String> permissions,
				Session session) {
			if (SessionAuthorizationType.READ.equals(authorizationType)) {
				throw new UnsupportedOperationException(
						"Cannot call setPublishPermissions after setReadPermissions has been called.");
			}
			if (validatePermissions(permissions,
					SessionAuthorizationType.PUBLISH, session)) {
				this.permissions = permissions;
				authorizationType = SessionAuthorizationType.PUBLISH;
			}
		}

		private boolean validatePermissions(List<String> permissions,
				SessionAuthorizationType authType, Session currentSession) {
			if (SessionAuthorizationType.PUBLISH.equals(authType)) {
				if (com.facebook.internal.Utility.isNullOrEmpty(permissions)) {
					throw new IllegalArgumentException(
							"Permissions for publish actions cannot be null or empty.");
				}
			}
			if (currentSession != null && currentSession.isOpened()) {
				if (!com.facebook.internal.Utility.isSubset(permissions,
						currentSession.getPermissions())) {
					Log.e("Facebook SignUp",
							"Cannot set additional permissions when session is already open.");
					return false;
				}
			}
			return true;
		}

		List<String> getPermissions() {
			return permissions;
		}

		public void clearPermissions() {
			permissions = null;
			authorizationType = null;
		}

		public void setLoginBehavior(SessionLoginBehavior loginBehavior) {
			this.loginBehavior = loginBehavior;
		}

		public SessionLoginBehavior getLoginBehavior() {
			return loginBehavior;
		}

		public void setSessionStatusCallback(Session.StatusCallback callback) {
			this.sessionStatusCallback = callback;
		}

		public Session.StatusCallback getSessionStatusCallback() {
			return sessionStatusCallback;
		}
	}

	private class LoginButtonCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {

			if (session != null && state.isOpened()) {
				sessionTracker.setSession(session);
				fetchUserInfo();

			} else
				sessionTracker.setSession(null);
			if (exception != null) {
				handleError(exception);
			}

		}
	};

	void handleError(Exception exception) {
		if (properties.onErrorListener != null) {
			if (exception instanceof FacebookException) {
				properties.onErrorListener
						.onError((FacebookException) exception);
			} else {
				properties.onErrorListener.onError(new FacebookException(
						exception));
			}
		}
	}

	private Session userInfoSession = null; // the Session used to fetch the
	private UserInfoChangedCallback userInfoChangedCallback; // current user
																// info

	private void fetchUserInfo() {
		final Session currentSession = Session.getActiveSession();
		if (currentSession != null) {
			if (currentSession != userInfoSession) {
				Request request = Request.newMeRequest(currentSession,
						new Request.GraphUserCallback() {
							@Override
							public void onCompleted(GraphUser me,
									Response response) {
								if (currentSession == sessionTracker
										.getOpenSession()) {
									user = me;

									String fbId = user.getId();
									String firstName = user.getFirstName();
									String lastName = user.getLastName();
									String email = (String) user.asMap().get(
											"email");
									String userName = user.getUsername();
									if (TextUtils.isEmpty(email))
										email = userName;

									sendFacebookDetailsToServer(firstName,
											lastName, email, fbId);

									if (userInfoChangedCallback != null) {
										userInfoChangedCallback
												.onUserInfoFetched(user);
									}
								}
								if (response.getError() != null) {
									handleError(response.getError()
											.getException());
								}
							}
						});
				Request.executeBatchAsync(request);
				userInfoSession = currentSession;
			}
		} else {
			user = null;
			if (userInfoChangedCallback != null) {
				userInfoChangedCallback.onUserInfoFetched(user);
			}
		}

	}

	private void saveUserDetails() {

		if (accountId != null) {
			/*
			 * Save the account email id , as we need to display it on home
			 * screen once the user logs in.
			 */
			Utility.saveUserDetails(getActivity(), accountId);
			/*
			 * Save the user token, as its needed when using features like
			 * favorites, change password.
			 */
			Utility.saveUniqueId(SignUpFragment.this.getActivity(), accountId,
					registrationHappeningThrough);
		}

		// if the response is successful and if the sign in screen
		// is reached from bicycle details screen then the user
		// is trying to favorite the bicycle so close the current
		// screen and refresh previous and favorites screen by
		// setting variables.
		if (navigatingFromModelDetails) {
			Globals.isFavoritesModified = true;
			Globals.isSignedInFromBicycleDetails = true;
			SignUpFragment.this.getFragmentManager().popBackStackImmediate();
		} else
			startApplication();
		// SignUpFragment.this.getFragmentManager().popBackStackImmediate();
	}

}
