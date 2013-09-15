package com.ih.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.ih.demo.R;

public class Shop implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5110732701368784232L;
	private int shopId;
	private int shopRes;
	
	private String shopName;
	private String shopAddress;
	private String shopCategory;
	private String shopPhone;
	private String shopWebSite;
	private String shopEmail;
	private String shopFBLike;
	private String shopHrs;
	private String shopImageUrl;
	private String shopLat;
	private String shopLong;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public String getShopWebSite() {
		return shopWebSite;
	}

	public void setShopWebSite(String shopWebSite) {
		this.shopWebSite = shopWebSite;
	}

	public String getShopEmail() {
		return shopEmail;
	}

	public void setShopEmail(String shopEmail) {
		this.shopEmail = shopEmail;
	}

	public String getShopFBLike() {
		return shopFBLike;
	}

	public void setShopFBLike(String shopFBLike) {
		this.shopFBLike = shopFBLike;
	}

	public String getShopHrs() {
		return shopHrs;
	}

	public void setShopHrs(String shopHrs) {
		this.shopHrs = shopHrs;
	}

	public String getShopImageUrl() {
		return shopImageUrl;
	}

	public void setShopImageUrl(String shopImageUrl) {
		this.shopImageUrl = shopImageUrl;
	}

	public String getShopLat() {
		return shopLat;
	}

	public void setShopLat(String shopLat) {
		this.shopLat = shopLat;
	}

	public String getShopLong() {
		return shopLong;
	}

	public void setShopLong(String shopLong) {
		this.shopLong = shopLong;
	}

	public static ArrayList<Shop> getShops() {

		ArrayList<Shop> shops = new ArrayList<Shop>();

		Shop shop=new Shop();
		
		shop.setShopId(1);
		shop.setShopName("Saturday Shopee");
		shop.setShopAddress("24, Circuit Road, Jodhpur");
		shop.setShopCategory("Clothing, Fashion");
		shop.setShopEmail("hello@saturdayshopee.com");
		shop.setShopFBLike("1,102");
		shop.setShopHrs("10AM-5PM (Tuesday/Saturday)");
		shop.setShopImageUrl("");
		shop.setShopLat("26.234456");
		shop.setShopLong("73.024406");
		shop.setShopPhone("0291-2430575");
		shop.setShopWebSite("saturdayshopee.com");
		shop.setShopRes(R.drawable.shop1);
		shops.add(shop);
		
		shop=new Shop();
		shop.setShopId(2);
		shop.setShopName("Fashionara");
		shop.setShopAddress("Hitech City, Hyderabad");
		shop.setShopCategory("Clothing, Jwellery");
		shop.setShopEmail("hello@fashionara.com");
		shop.setShopFBLike("1,102");
		shop.setShopHrs("10AM-5PM (Tuesday/Saturday)");
		shop.setShopImageUrl("");
		shop.setShopLat("17.44319");
		shop.setShopLong("78.377752");
		shop.setShopPhone("0291-2430123");
		shop.setShopWebSite("fashionara.com");
		shop.setShopRes(R.drawable.shop2);
		shops.add(shop);
		
		shop=new Shop();
		shop.setShopId(3);
		shop.setShopName("eBay India");
		shop.setShopAddress("Pune");
		shop.setShopCategory("Electronics, Online");
		shop.setShopEmail("hello@ebay.com");
		shop.setShopFBLike("1,102");
		shop.setShopHrs("10AM-5PM (Tuesday/Saturday)");
		shop.setShopImageUrl("");
		shop.setShopLat("18.504354");
		shop.setShopLong("73.858337");
		shop.setShopPhone("0291-2430456");
		shop.setShopWebSite("ebay.com");
		shop.setShopRes(R.drawable.shop3);
		shops.add(shop);
		
		shop=new Shop();
		shop.setShopId(4);
		shop.setShopName("Shoppers Stop");
		shop.setShopAddress("Inorbit Mall, Malad, Mumbai");
		shop.setShopCategory("Fashion, Clothing");
		shop.setShopEmail("hello@shoppers.com");
		shop.setShopFBLike("1,102");
		shop.setShopHrs("10AM-5PM (Tuesday/Saturday)");
		shop.setShopImageUrl("");
		shop.setShopLat("19.172045");
		shop.setShopLong("72.835836");
		shop.setShopPhone("0291-2400000");
		shop.setShopWebSite("shoppers.com");
		shop.setShopRes(R.drawable.shop4);
		shops.add(shop);
		
		
		shop=new Shop();
		shop.setShopId(5);
		shop.setShopName("Jai Hind Collection");
		shop.setShopAddress("Chinchwad, Pune");
		shop.setShopCategory("Fashion, Clothing");
		shop.setShopEmail("hello@jaihind.com");
		shop.setShopFBLike("1,102");
		shop.setShopHrs("10AM-5PM (Tuesday/Saturday)");
		shop.setShopImageUrl("");
		shop.setShopLat("18.504354");
		shop.setShopLong("73.858337");
		shop.setShopPhone("0291-2400000");
		shop.setShopWebSite("jaihind.com");
		shop.setShopRes(R.drawable.shop5);
		shops.add(shop);
		
		return shops;

	}

	public int getShopRes() {
		return shopRes;
	}

	public void setShopRes(int shopRes) {
		this.shopRes = shopRes;
	}

	
	public static ContentValues getContentValues(Shop shop) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("NAME", shop.getShopName());
		contentValues.put("ADDRESS", shop.getShopAddress());
		contentValues.put("IMAGE_URL", shop.getShopImageUrl());
		contentValues.put("CATEGORY", shop.getShopCategory());
		contentValues.put("PHONE", shop.getShopPhone());
		contentValues.put("WEBSITE", shop.getShopWebSite());
		contentValues.put("EMAIL", shop.getShopEmail());
		contentValues.put("FBLIKE", shop.getShopFBLike());
		contentValues.put("HOURS", shop.getShopHrs());
		contentValues.put("SHOP_LATLONG", shop.getShopLat()+","+shop.getShopLong());
		return contentValues;
	}
	
	public static Shop getShop(Cursor shopCursor) {

			
			Shop shop=new Shop();
			shop.setShopId(shopCursor.getInt(shopCursor.getColumnIndex("ID")));
			shop.setShopName(shopCursor.getString(shopCursor.getColumnIndex("NAME")));
			shop.setShopAddress(shopCursor.getString(shopCursor.getColumnIndex("ADDRESS")));
			shop.setShopCategory(shopCursor.getString(shopCursor.getColumnIndex("CATEGORY")));
			shop.setShopEmail(shopCursor.getString(shopCursor.getColumnIndex("EMAIL")));
			shop.setShopFBLike(shopCursor.getString(shopCursor.getColumnIndex("FBLIKE")));
			shop.setShopHrs(shopCursor.getString(shopCursor.getColumnIndex("HOURS")));
			shop.setShopImageUrl(shopCursor.getString(shopCursor.getColumnIndex("IMAGE_URL")));
			shop.setShopLat(shopCursor.getString(shopCursor.getColumnIndex("SHOP_LATLONG")).split(";")[0]);
			shop.setShopLong(shopCursor.getString(shopCursor.getColumnIndex("SHOP_LATLONG")).split(";")[1]);
			shop.setShopPhone(shopCursor.getString(shopCursor.getColumnIndex("PHONE")));
			shop.setShopWebSite(shopCursor.getString(shopCursor.getColumnIndex("WEBSITE")));
			shop.setShopRes(R.drawable.shop1);
		return shop;
		
		
		
	}
	
	
	
	
	
}
