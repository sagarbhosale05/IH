package com.ih.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ih.demo.R;
import com.ih.model.AppPage;
import com.ih.model.AppPageGroup;

/**
 * @author abhijeet.bhosale
 * 
 */
public class SideBarMenuListAdapter extends BaseExpandableListAdapter {

	/*
	 * data
	 */
	private Context context = null;
	public ArrayList<AppPageGroup> menuAppPageGroups;

	private int activeChildPosition = 0, activeGroupPosition = 1;

	public SideBarMenuListAdapter(Context context,
			ArrayList<AppPageGroup> newAppPageGroups) {
		this.context = context;

		if (menuAppPageGroups != null)
			menuAppPageGroups.clear();
		else
			menuAppPageGroups = new ArrayList<AppPageGroup>();

		menuAppPageGroups.addAll(newAppPageGroups);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseExpandableListAdapter#getChildType(int, int)
	 */
	@Override
	public int getChildType(int groupPosition, int childPosition) {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return menuAppPageGroups.get(groupPosition).getAppPages()
				.get(childPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.sidebar_expandablelistview_subtitle_view, parent,
					false);

			SubtitleViewHolder holder = new SubtitleViewHolder();
			holder.subTitle = (TextView) convertView
					.findViewById(R.id.textview_sidebar_menu_subtitle);

			holder.subTitleIcon = (ImageView) convertView
					.findViewById(R.id.imageview_sidebar_menu_subtitle_icon);

			convertView.setTag(holder);
		}

		SubtitleViewHolder holder = (SubtitleViewHolder) convertView.getTag();

		AppPage currentPage = (AppPage) getChild(groupPosition, childPosition);

		holder.subTitle.setText("" + currentPage.getPageName());

		holder.subTitleIcon.setImageResource(currentPage.getPageIconId());

		if (groupPosition == activeGroupPosition
				&& childPosition == activeChildPosition)
			convertView.setBackgroundResource(R.color.blue);
		else
			convertView.setBackgroundResource(R.color.white);

		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return menuAppPageGroups != null ? menuAppPageGroups.get(groupPosition)
				.getAppPages().size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return menuAppPageGroups.get(groupPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		return menuAppPageGroups != null ? menuAppPageGroups.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * @param immutableList
	 *            the menuAppPageGroups to set
	 */
	public void setMenuAppPageGroups(ArrayList<AppPageGroup> newAppPageGroups) {
		if (menuAppPageGroups != null)
			menuAppPageGroups.clear();
		else
			menuAppPageGroups = new ArrayList<AppPageGroup>();
		menuAppPageGroups.addAll(newAppPageGroups);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.sidebar_expandablelistview_title_view, parent,
					false);
			TitleViewHolder holder = new TitleViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.textview_sidebar_menu_title);
			convertView.setTag(holder);
		}

		TitleViewHolder holder = (TitleViewHolder) convertView.getTag();
		AppPageGroup currentGroupPage = (AppPageGroup) getGroup(groupPosition);
		if (currentGroupPage != null)
			holder.title.setText("" + currentGroupPage.getPageGroupName());
		ExpandableListView expandableListView = (ExpandableListView) parent;
		expandableListView.expandGroup(groupPosition);

		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/*
	 * Holder for the Past view type
	 */
	class SubtitleViewHolder {
		TextView subTitle;
		ImageView subTitleIcon;
	}

	/*
	 * Holder for the Past view type
	 */
	class TitleViewHolder {
		TextView title;
		ImageView titleIcon;
	}

	public void setActiveChildPosition(int activeChildPosition,
			int activeGroupPosition) {
		this.activeChildPosition = activeChildPosition;
		this.activeGroupPosition = activeGroupPosition;
	}

	public int getActiveChildPosition() {
		return activeChildPosition;
	}

	public int getActiveGroupPosition() {
		return activeGroupPosition;
	}

}