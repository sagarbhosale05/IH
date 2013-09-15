package com.ih.model;

import java.util.ArrayList;

public class AppPageGroup  {

	private int pageGroupId;
	private String pageGroupName;
	private int pageGroupIconId;
	private ArrayList<AppPage> appPages;
	
	/**
	 * @return the appPages
	 */
	public ArrayList<AppPage> getAppPages() {
		return appPages;
	}
	/**
	 * @param appPages the appPages to set
	 */
	public void setAppPages(ArrayList<AppPage> appPages) {
		this.appPages = appPages;
	}
	/**
	 * @return the pageGroupId
	 */
	public int getPageGroupId() {
		return pageGroupId;
	}
	/**
	 * @param pageGroupId the pageGroupId to set
	 */
	public void setPageGroupId(int pageGroupId) {
		this.pageGroupId = pageGroupId;
	}
	/**
	 * @return the pageGroupName
	 */
	public String getPageGroupName() {
		return pageGroupName;
	}
	/**
	 * @param pageGroupName the pageGroupName to set
	 */
	public void setPageGroupName(String pageGroupName) {
		this.pageGroupName = pageGroupName;
	}
	/**
	 * @return the pageGroupIconId
	 */
	public int getPageGroupIconId() {
		return pageGroupIconId;
	}
	/**
	 * @param pageGroupIconId the pageGroupIconId to set
	 */
	public void setPageGroupIconId(int pageGroupIconId) {
		this.pageGroupIconId = pageGroupIconId;
	}
	
	
}
