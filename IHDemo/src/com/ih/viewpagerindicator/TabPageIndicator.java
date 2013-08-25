package com.ih.viewpagerindicator;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.internal.app.ActionBarWrapper.TabWrapper;
import com.google.ads.t;
import com.ih.demo.R;
import com.ih.utility.Globals;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class TabPageIndicator extends HorizontalScrollView implements PageIndicator
	{
		/** Title text used when no title is provided by the adapter. */
		private static final CharSequence EMPTY_TITLE = "";
		private TabView tabView;
		private TabView tabView0, tabView1, tabView2,tabView3;
		private String screenMode = null;
		private boolean fromPlacesScreen = false;
		private boolean fromDashboard=false;
		private int selectedIndex=0;
		/**
		 * Interface for a callback when the selected tab has been reselected.
		 */
		public interface OnTabReselectedListener
			{
				/**
				 * Callback when the selected tab has been reselected.
				 * 
				 * @param position
				 *            Position of the current center item.
				 */
				void onTabReselected(int position);
			}

		private Runnable mTabSelector;

		private final OnClickListener mTabClickListener = new OnClickListener()
			{
				public void onClick(View view)
					{

						TabView tabView = (TabView) view;
						final int oldSelected = mViewPager.getCurrentItem();
						final int newSelected = tabView.getIndex();
						mViewPager.setCurrentItem(newSelected);
						if (oldSelected == newSelected && mTabReselectedListener != null)
							{
								mTabReselectedListener.onTabReselected(newSelected);
							}

					}
			};

		private final IcsLinearLayout mTabLayout;

		private ViewPager mViewPager;
		private ViewPager.OnPageChangeListener mListener;
		private ImageView mapButton;
		private int mMaxTabWidth;
		private int mSelectedTabIndex;
		private TextView tv;
		private ArrayList<String> screenTitle;
		private OnTabReselectedListener mTabReselectedListener;

		public TabPageIndicator(Context context)
			{
				this(context, null);
			}

		public TabPageIndicator(Context context, AttributeSet attrs)
			{
				super(context, attrs);
				setHorizontalScrollBarEnabled(false);

				mTabLayout = new IcsLinearLayout(context, R.attr.vpiTabPageIndicatorStyle);

				addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
			}

		public void setOnTabReselectedListener(OnTabReselectedListener listener)
			{
				mTabReselectedListener = listener;
			}

		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
			{
				final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
				final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
				setFillViewport(lockedExpanded);

				final int childCount = mTabLayout.getChildCount();
				if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST))
					{
						if (childCount > 2)
							{
								mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
							} else
							{
								mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
							}
					} else
					{
						mMaxTabWidth = -1;
					}

				final int oldWidth = getMeasuredWidth();
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
				final int newWidth = getMeasuredWidth();

				if (lockedExpanded && oldWidth != newWidth)
					{
						setCurrentItem(mSelectedTabIndex);
					}
			}

		private void animateToTab(final int position)
			{
				final View tabView = mTabLayout.getChildAt(position);
				if (mTabSelector != null)
					{
						removeCallbacks(mTabSelector);
					}
				mTabSelector = new Runnable()
					{
						public void run()
							{
								final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
								smoothScrollTo(scrollPos, 0);
								mTabSelector = null;
							}
					};
				post(mTabSelector);
			}

		@Override
		public void onAttachedToWindow()
			{
				super.onAttachedToWindow();
				if (mTabSelector != null)
					{
						post(mTabSelector);
					}
			}

		@Override
		public void onDetachedFromWindow()
			{
				super.onDetachedFromWindow();
				if (mTabSelector != null)
					{
						removeCallbacks(mTabSelector);
					}
			}

		private void addTab(int index, CharSequence text, int iconResId, int lastItem)
			{
				tabView = new TabView(getContext());
				tabView.mIndex = index;
				tabView.setFocusable(true);
				if (index == 0)
					{
						tabView0 = tabView;
					} else if (index == 1)
					{
						tabView1 = tabView;
					} else if (index == 2)
					{
						tabView2 = tabView;
					}
					else if (index == 3)
					{
						tabView3 = tabView;
					}
				

				if (index == lastItem - 1)
					{
						if (!TextUtils.isEmpty(screenMode) && TextUtils.equals(screenMode, "Favorite Icon"))
							{
								Drawable img = getContext().getResources().getDrawable(R.drawable.favorite_btn_unselect);
								tabView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
								tabView.setPadding(10, 15, 0, 15);
							} else
							{
								tabView.setPadding(0, 15, 0, 15);
							}
					} else
					{
						/*Drawable img = getContext().getResources().getDrawable(R.drawable.global_tabs_divider);
						tabView.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
						tabView.setPadding(0, 15, 0, 15);*/
					}
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 0, 0, 5);
				tabView.setLayoutParams(params);
				tabView.setGravity(Gravity.CENTER);
				tabView.setOnClickListener(mTabClickListener);
				tabView.setText(text);

				if (iconResId != 0)
					{
						tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
					}
				mTabLayout.setBackgroundColor(getResources().getColor(R.color.blue));

				mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, MATCH_PARENT, 1));
			}

		@Override
		public void onPageScrollStateChanged(int arg0)
			{
				if (mListener != null)
					{
						mListener.onPageScrollStateChanged(arg0);
					}
			}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				if (mListener != null)
					{
						mListener.onPageScrolled(arg0, arg1, arg2);
					}

			}

		@Override
		public void onPageSelected(int arg0)
			{
				if (tv != null)
					{
						switch (arg0)
							{
							case 0:
								tv.setText("Featured Places");

								break;
							case 1:
								tv.setText("Places");

								break;
							case 2:
								tv.setText("Favorite Places");

								break;

							default:
								break;
							}
					}
				if (!TextUtils.isEmpty(screenMode) && TextUtils.equals(screenMode, "Favorite Icon"))
					{
						if (arg0 == 2)
							{
								Drawable img = getContext().getResources().getDrawable(R.drawable.favorite_btn_select);
								tabView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
								tabView.setPadding(10, 15, 0, 15);
							} else
							{
								Drawable img = getContext().getResources().getDrawable(R.drawable.favorite_btn_unselect);
								tabView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
								tabView.setPadding(10, 15, 0, 15);
							}
					}
				if (mapButton != null)
					{
						if (arg0 == 0)
							{
								mapButton.setVisibility(View.VISIBLE);
							} else if (arg0 == 1)
							{
								mapButton.setVisibility(View.GONE);
							} else if (arg0 == 2)
							{
								mapButton.setVisibility(View.VISIBLE);
							}
					}
				setCurrentItem(arg0);
				if (mListener != null)
					{
						mListener.onPageSelected(arg0);
					}
				if (fromPlacesScreen && Globals.hasRefreshFeatured && arg0 == 0)
					{
						Globals.hasRefreshFeatured = false;
						//FeaturedPlaces.downloadData();
					}
			}

		@Override
		public void setViewPager(ViewPager view)
			{
				if (mViewPager == view)
					{
						return;
					}
				if (mViewPager != null)
					{
						mViewPager.setOnPageChangeListener(null);
					}
				final PagerAdapter adapter = view.getAdapter();
				if (adapter == null)
					{
						throw new IllegalStateException("ViewPager does not have adapter instance.");
					}
				mViewPager = view;
				view.setOnPageChangeListener(this);
				notifyDataSetChanged(selectedIndex);
			}

		@Override
		public void setViewPager(ViewPager view, ImageView mapButton, String screenMode, boolean fromplacesScreen,int selectedIndex,boolean fromDashboard)
			{
				this.screenMode = screenMode;
				this.mapButton = mapButton;
				this.fromPlacesScreen = fromplacesScreen;
				this.selectedIndex=selectedIndex;
				this.fromDashboard=fromDashboard;
				if (mViewPager == view)
					{
						return;
					}
				if (mViewPager != null)
					{
						mViewPager.setOnPageChangeListener(null);
					}
				final PagerAdapter adapter = view.getAdapter();
				if (adapter == null)
					{
						throw new IllegalStateException("ViewPager does not have adapter instance.");
					}
				mViewPager = view;
				view.setOnPageChangeListener(this);
				notifyDataSetChanged(selectedIndex);
			}

		@Override
		public void setViewPager(ViewPager view, ImageView mapButton)
			{
				this.screenMode = screenMode;
				this.mapButton = mapButton;
				if (mViewPager == view)
					{
						return;
					}
				if (mViewPager != null)
					{
						mViewPager.setOnPageChangeListener(null);
					}
				final PagerAdapter adapter = view.getAdapter();
				if (adapter == null)
					{
						throw new IllegalStateException("ViewPager does not have adapter instance.");
					}
				mViewPager = view;
				view.setOnPageChangeListener(this);
				notifyDataSetChanged(selectedIndex);
			}

		@Override
		public void setViewPager(ViewPager view, TextView tv, int screenIndicator, ArrayList<String> screenTitles)
			{
				this.tv = tv;
				if (mViewPager == view)
					{
						return;
					}
				if (mViewPager != null)
					{
						mViewPager.setOnPageChangeListener(null);
					}
				final PagerAdapter adapter = view.getAdapter();
				if (adapter == null)
					{
						throw new IllegalStateException("ViewPager does not have adapter instance.");
					}
				mViewPager = view;
				view.setOnPageChangeListener(this);
				notifyDataSetChanged(selectedIndex);
			}

		public void notifyDataSetChanged(int selectedtab)
			{
				mTabLayout.removeAllViews();
				PagerAdapter adapter = mViewPager.getAdapter();
				IconPagerAdapter iconAdapter = null;
				if (adapter instanceof IconPagerAdapter)
					{
						iconAdapter = (IconPagerAdapter) adapter;
					}
				final int count = adapter.getCount();
				for (int i = 0; i < count; i++)
					{
						CharSequence title = adapter.getPageTitle(i);
						if (title == null)
							{
								title = EMPTY_TITLE;
							}
						int iconResId = 0;
						if (iconAdapter != null)
							{
								iconResId = iconAdapter.getIconResId(i);
							}
						addTab(i, title, iconResId, count);
					}
				if (mSelectedTabIndex > count)
					{
						mSelectedTabIndex = count - 1;
					}
				if(fromDashboard)
					{
						mSelectedTabIndex=selectedtab;
						setCurrentItem(mSelectedTabIndex);
					}
				else
					{
						setCurrentItem(mSelectedTabIndex);
					}
				requestLayout();
			}

		@Override
		public void setViewPager(ViewPager view, int initialPosition)
			{
				setViewPager(view);
				setCurrentItem(initialPosition);
			}

		@Override
		public void setCurrentItem(int item)
			{
//				item=selectedIndex;
				if (mViewPager == null)
					{
						throw new IllegalStateException("ViewPager has not been bound.");
					}
				mSelectedTabIndex = item;
				mViewPager.setCurrentItem(item);

				final int tabCount = mTabLayout.getChildCount();
				for (int i = 0; i < tabCount; i++)
					{
						final View child = mTabLayout.getChildAt(i);
						final boolean isSelected = (i == item);
						child.setSelected(isSelected);
						if (isSelected)
							{
							if (tabCount == 4){
								if (item == 0)
									{
										tabView0.setTextColor(getResources().getColor(R.color.white));
										tabView1.setTextColor(getResources().getColor(R.color.light_gray));
										tabView2.setTextColor(getResources().getColor(R.color.light_gray));
										tabView3.setTextColor(getResources().getColor(R.color.light_gray));
										
									} else if (item == 1)
									{
										tabView1.setTextColor(getResources().getColor(R.color.white));
										tabView0.setTextColor(getResources().getColor(R.color.light_gray));
										tabView2.setTextColor(getResources().getColor(R.color.light_gray));
										tabView3.setTextColor(getResources().getColor(R.color.light_gray));
									} else if (item == 2)
									{
										tabView2.setTextColor(getResources().getColor(R.color.white));
										tabView1.setTextColor(getResources().getColor(R.color.light_gray));
										tabView0.setTextColor(getResources().getColor(R.color.light_gray));
										tabView3.setTextColor(getResources().getColor(R.color.light_gray));
									}
									else if (item == 4)
									{
										tabView2.setTextColor(getResources().getColor(R.color.light_gray));
										tabView1.setTextColor(getResources().getColor(R.color.light_gray));
										tabView0.setTextColor(getResources().getColor(R.color.light_gray));
										tabView3.setTextColor(getResources().getColor(R.color.white));
									}
								
								
							}
							else if (tabCount == 3)
									{
										if (item == 0)
											{
												tabView0.setTextColor(getResources().getColor(R.color.white));
												tabView1.setTextColor(getResources().getColor(R.color.light_gray));
												tabView2.setTextColor(getResources().getColor(R.color.light_gray));
											} else if (item == 1)
											{
												tabView1.setTextColor(getResources().getColor(R.color.white));
												tabView0.setTextColor(getResources().getColor(R.color.light_gray));
												tabView2.setTextColor(getResources().getColor(R.color.light_gray));
											} else if (item == 2)
											{
												tabView2.setTextColor(getResources().getColor(R.color.white));
												tabView1.setTextColor(getResources().getColor(R.color.light_gray));
												tabView0.setTextColor(getResources().getColor(R.color.light_gray));
											}
									} else if (tabCount == 2)
									{
										if (item == 0)
											{
												tabView0.setTextColor(getResources().getColor(R.color.white));
												tabView1.setTextColor(getResources().getColor(R.color.light_gray));
											} else if (item == 1)
											{
												tabView1.setTextColor(getResources().getColor(R.color.white));
												tabView0.setTextColor(getResources().getColor(R.color.light_gray));
											}
									}
								animateToTab(item);

							}
					}
			}

		@Override
		public void setOnPageChangeListener(OnPageChangeListener listener)
			{
				mListener = listener;
			}

		private class TabView extends TextView
			{
				private int mIndex;

				public TabView(Context context)
					{
						super(context, null, R.attr.vpiTabPageIndicatorStyle);
					}

				@Override
				public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
					{
						super.onMeasure(widthMeasureSpec, heightMeasureSpec);
						if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth)
							{
								super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
							}
					}

				public int getIndex()
					{
						return mIndex;
					}
			}

		@Override
		public void setViewPager(ViewPager view, String screenMode)
			{
				this.screenMode = screenMode;
				if (mViewPager == view)
					{
						return;
					}
				if (mViewPager != null)
					{
						mViewPager.setOnPageChangeListener(null);
					}
				final PagerAdapter adapter = view.getAdapter();
				if (adapter == null)
					{
						throw new IllegalStateException("ViewPager does not have adapter instance.");
					}
				mViewPager = view;
				view.setOnPageChangeListener(this);
				notifyDataSetChanged(selectedIndex);

			}

	}
