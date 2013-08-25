package com.ih.model;

import java.util.ArrayList;

import com.ih.demo.R;

import android.content.Context;

public class StaticMenuData {

	public static ArrayList<AppPageGroup> getStaticSideBarMenu(
			Context context) {
		ArrayList<AppPageGroup> appPageGroups=new ArrayList<AppPageGroup>();
		
		AppPageGroup appPageGroup0=new AppPageGroup();
		appPageGroup0.setPageGroupId(0);
		appPageGroup0.setPageGroupName("Info");
		appPageGroup0.setPageGroupIconId(R.drawable.ic_launcher);
		ArrayList<AppPage> pages0=new ArrayList<AppPage>();
		AppPage appPage00=new AppPage();
		appPage00.setPageIconId(R.drawable.ic_launcher);
		appPage00.setPageId(0);
		appPage00.setPageName("Profile");
		pages0.add(appPage00);
		appPageGroup0.setAppPages(pages0);
		appPageGroups.add(appPageGroup0);
		
		AppPageGroup appPageGroup1=new AppPageGroup();
		appPageGroup1.setPageGroupId(1);
		appPageGroup1.setPageGroupName("Product");
		appPageGroup1.setPageGroupIconId(R.drawable.ic_launcher);
		ArrayList<AppPage> pages1=new ArrayList<AppPage>();
		
		AppPage appPage10=new AppPage();
		appPage10.setPageIconId(R.drawable.ic_launcher);
		appPage10.setPageId(0);
		appPage10.setPageName("Product1");
		pages1.add(appPage10);

		AppPage appPage11=new AppPage();
		appPage11.setPageIconId(R.drawable.ic_launcher);
		appPage11.setPageId(1);
		appPage11.setPageName("Product2");
		pages1.add(appPage11);

		AppPage appPage12=new AppPage();
		appPage12.setPageIconId(R.drawable.ic_launcher);
		appPage12.setPageId(2);
		appPage12.setPageName("Product3");
		pages1.add(appPage12);

		AppPage appPage13=new AppPage();
		appPage13.setPageIconId(R.drawable.ic_launcher);
		appPage13.setPageId(3);
		appPage13.setPageName("Product4");
		pages1.add(appPage13);

		appPageGroup1.setAppPages(pages1);
		appPageGroups.add(appPageGroup1);
		
		return appPageGroups;
	}

}
