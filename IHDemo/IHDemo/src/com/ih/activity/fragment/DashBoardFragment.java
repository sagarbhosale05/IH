/**
 * 
 */
package com.ih.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ih.activity.fragment.FeedsScreen;
import com.ih.activity.fragment.ShopScreen;
import com.ih.activity.fragment.TreadingsScreen;
import com.ih.demo.R;
import com.ih.viewpagerindicator.TabPageIndicator;

/**
 * @author abhijeet.bhosale
 * 
 */
public class DashBoardFragment extends SherlockFragment {

	private ViewPager pager;
	TabPageIndicator tabHost;
	private static String[] HOME_TAB_CONTENT;
	private boolean fromDashboardScreen;
	ImageView mapButton;

	View parentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentView = inflater.inflate(R.layout.fragments_tabs, null);

		initializeScreen();
		initializeHandler();

		return parentView;

	}

	private void initializeHandler() {
		// TODO Auto-generated method stub

	}

	private void initializeScreen() {

		HOME_TAB_CONTENT = new String[] { "Feeds", "Shops", "Treadings" };

		if(pager!=null)
			return;
		FragmentAdapter adapter = new FragmentAdapter(getActivity()
				.getSupportFragmentManager());

		pager = (ViewPager) parentView.findViewById(R.id.pager);
		pager.setOffscreenPageLimit(2);
		
		pager.setAdapter(adapter);
		pager.setCurrentItem(0, false);

		TabPageIndicator indicator = (TabPageIndicator) parentView
				.findViewById(R.id.indicator);
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
				fragment = FeedsScreen.newInstance(HOME_TAB_CONTENT[position
						% HOME_TAB_CONTENT.length]);
				break;
			case 1:
				fragment = ShopScreen.newInstance(HOME_TAB_CONTENT[position
						% HOME_TAB_CONTENT.length]);
				break;
			case 2:
				fragment = TreadingsScreen
						.newInstance(HOME_TAB_CONTENT[position
								% HOME_TAB_CONTENT.length]);
				break;
			default:
				break;
			}

			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return HOME_TAB_CONTENT[position % HOME_TAB_CONTENT.length];
		}

		@Override
		public int getCount() {
			return HOME_TAB_CONTENT.length;
		}
		
		@Override
		public Object instantiateItem(ViewGroup arg0, int arg1) {
			
			
			return super.instantiateItem(arg0, arg1);
		}
		
		
	}

}
