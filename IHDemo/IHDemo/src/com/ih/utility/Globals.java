package com.ih.utility;


// TODO: Auto-generated Javadoc
/**
 * The Class Globals.
 */
/**
 * @author Hardyesh.Gupta
 * 
 */
public class Globals
	{

	

	/** The is favorites modified. */
	public static boolean isFavoritesModified = false;
	
	/** The is signed in from bicycle details. */
	public static boolean isSignedInFromBicycleDetails = false;
	
		/** The thread pool. */
		public static ThreadPool threadPool = null;

		/** The finish application. */
		public static boolean finishApp = false;

		/** The finish application. */
		public static boolean restartApp = false;

		/** required by image cache. */

		public static String imageCacheFolderPath;

		/** The internal application image cache path. */
		public static String internalAppImageCachePath;

		/** The is back button pressed. */
		public static boolean isBackButtonPressed = false;

		/** The is navigated to settings screen. */
		public static boolean isNavigatedToSettingsScreen = false;

		/** The is navigated to settings screen. */
		public static boolean isNavigatedFromSplashScreen = false;

		/** The is search mode on. */
		public static boolean isSearchModeOn = false;

		// this will vary for SD-card and for within application cache
		/** The image cache limit. */
		public static int imageCacheLimit;

		/** The has category clicked. */
		public static boolean hasCategoryClicked = false;

		/** The search criteria name. */
		public static String searchKeyWord = "";

		/** The device id. */
		public static String deviceID;

		public static boolean isLocationRefreshed = false;

		public static String registrationID;

		/**
		 * This variable is used to tell if the fav status of the store has been
		 * changed or not.
		 */
		public static boolean isStoreFavoriteStatusChanged = false;

		public static boolean isProductFavoriteStatusChanged = false;

		public static boolean isFeaturedStoreFavoriteStatusChanged = false;

		public static boolean isDealFavoriteStatusChanged = false;

		public static boolean isFeaturedDealFavoriteStatusChanged = false;

		/**
		 * This variable is used to tell if the store rating status of the store
		 * has been changed or not.
		 */
		public static boolean isStoreRatingStatusChanged = false;

		public static boolean isFeaturedStoreRatingStatusChanged = false;

		public static boolean isProductRatingStatusChanged = false;

		/** The should refresh data on favorite status changes. */
		public static boolean shouldRefreshDataOnFavoriteStatusChanged = false;

		/** The is clickon check in button. */
		public static boolean isClickonCheckInButton = false;

		/** The is clickon claim button. */
		public static boolean isClickonClaimButton = false;

		/**
		 * The flag is used so that we can show store fav status locally ie
		 * correct data when user navigates back.
		 */
		public static boolean isStoreSetAsFav = false;

		public static boolean isDealSetAsFav = false;

		public static boolean isProductSetAsFav = false;

		/** The is store favorite status changes deals detail screen. */
		public static boolean isStoreFavoriteStatusChangedDealsDetailScreen = false;

		/** The is store rating status changes deals detail screen. */
		public static boolean isStoreRatingStatusChangedDealsDetailScreen = false;

		/**
		 * This flag is used to determine if application is working on user's
		 * actual location or searxhed location.
		 */
		public static volatile boolean hasUserSettedLocationPrefrences = false;
		public static volatile boolean hasUserSearched = false;
		public static volatile boolean hasRefreshFeatured = false;

		/* Local Maintenance of user Rating */
		/** The user rating. */
		public static int userRating = -1;

		public static int productUserRating = -1;

		/** The places screen selected item site id. */
		public static int placesScreenSelectedItemSiteId = -1;

		/** The sites array list. */

		/**
		 * this variable is used when ratings,favorites are changes from maps or
		 * user searches on maps.
		 */
		public static boolean shouldResetDataOnPlacesScreen = false;

		/**
		 * this is flag that is used to determine if fav mode was there in maps
		 * screen, ths is used to maintain consistency of fav and all stores
		 * mode of maps screen with places screen.
		 */
		public static boolean shouldChangeScreenModeForPlaces = false;

		/** The should close places screen. */
		public static boolean shouldClosePlacesScreen = false;

		/**
		 * this is flag that is used to determine if user has searched aything
		 * on maps screen.
		 */
		public static boolean hasUserSearchedOnMapsScreen = false;

		/** The time flag for rewards dashboard. */

		public static boolean timeFlagForRewardsDashboard = false;

		/** The my rewards dashboard time. */
		public static long myRewardsDashboardTime = 0;

		/** The my rewards count. */
		public static int myRewardsCount = 0;

		/**
		 * This flag is used since when email composer is used and back button
		 * is pressed, onBackPressed method of this activity is also called.
		 */
		public static boolean isEmailComposerLaunched = false;

		/** The is claim it screen current activity. */
		public static boolean isClaimItScreenCurrentActivity = false;

		/** The Searched location. To show on deal tab pop up message */
		public static String searchedLocation = "";

		/**
		 * This will contain the screen title that will be displayed as a header
		 * either a category or blank if searched for anythng else
		 */
		public static String screenTitleForSearchTerm;

		public static int scaledHeightOfImage;
		public static int scaledWidthOfImage;

		public static int deviceDensity;

		public static int selectedCategoryIndex = -1;

		public static String shareType = null;

		public static String dealRewardPlaceId = null;

		public static String placeId = null;

		public static int sidebar_selected_position = -1;
		public static int sidebar_selected_position_feedback = -1;
		public static int sidebar_selected_position_app = -1;
	}
