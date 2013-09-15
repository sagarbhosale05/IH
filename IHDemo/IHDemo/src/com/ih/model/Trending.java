package com.ih.model;

import java.util.ArrayList;

import com.ih.demo.R;

public class Trending {

	private int trendingId;
	private int trendingRes;
	
	private String trendingText;
	private String trendingImageUrl;
	private String trendingShopName;

	public int getTrendingId() {
		return trendingId;
	}

	public void setTrendingId(int trendingId) {
		this.trendingId = trendingId;
	}

	public String getTrendingText() {
		return trendingText;
	}

	public void setTrendingText(String trendingText) {
		this.trendingText = trendingText;
	}

	public String getTrendingImageUrl() {
		return trendingImageUrl;
	}

	public void setTrendingImageUrl(String trendingImageUrl) {
		this.trendingImageUrl = trendingImageUrl;
	}

	public String getTrendingShopName() {
		return trendingShopName;
	}

	public void setTrendingShopName(String trendingShopName) {
		this.trendingShopName = trendingShopName;
	}

	public static ArrayList<Trending> getTrendings() {

		ArrayList<Trending> trendings = new ArrayList<Trending>();

		Trending trending = new Trending();

		trending.setTrendingId(1);
		trending.setTrendingText("Denim Jeans");
		trending.setTrendingShopName("Saturday Shopee");
		trending.setTrendingImageUrl("");
		trending.setTrendingRes(R.drawable.deniumjeans);
		trendings.add(trending);

		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.goldearing);
		trending.setTrendingId(2);
		trending.setTrendingText("Gold earing");
		trending.setTrendingShopName("Mahalakshmi Jwellers");
		trending.setTrendingImageUrl("");
		trendings.add(trending);

		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.deniumjeans);
		trending.setTrendingId(3);
		trending.setTrendingText("Denim Jeans");
		trending.setTrendingShopName("Saturday Shopee");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.goldearing);
		trending.setTrendingId(4);
		trending.setTrendingText("Gold earing");
		trending.setTrendingShopName("Mahalakshmi Jwellers");
		trending.setTrendingImageUrl("");
		trendings.add(trending);

		

		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.deniumjeans);
		trending.setTrendingId(5);
		trending.setTrendingText("Denim Jeans");
		trending.setTrendingShopName("Saturday Shopee");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.goldearing);
		trending.setTrendingId(6);
		trending.setTrendingText("Gold earing");
		trending.setTrendingShopName("Mahalakshmi Jwellers");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.deniumjeans);
		trending.setTrendingId(7);
		trending.setTrendingText("Denim Jeans");
		trending.setTrendingShopName("Saturday Shopee");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.goldearing);
		trending.setTrendingId(8);
		trending.setTrendingText("Gold earing");
		trending.setTrendingShopName("Mahalakshmi Jwellers");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.deniumjeans);
		trending.setTrendingId(9);
		trending.setTrendingText("Denim Jeans");
		trending.setTrendingShopName("Saturday Shopee");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.goldearing);
		trending.setTrendingId(10);
		trending.setTrendingText("Gold earing");
		trending.setTrendingShopName("Mahalakshmi Jwellers");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.deniumjeans);
		trending.setTrendingId(11);
		trending.setTrendingText("Denim Jeans");
		trending.setTrendingShopName("Saturday Shopee");
		trending.setTrendingImageUrl("");
		trendings.add(trending);


		 trending = new Trending();
		 trending.setTrendingRes(R.drawable.goldearing);
		trending.setTrendingId(12);
		trending.setTrendingText("Gold earing");
		trending.setTrendingShopName("Mahalakshmi Jwellers");
		trending.setTrendingImageUrl("");
		trendings.add(trending);

		return trendings;

	}

	/**
	 * @return the trendingRes
	 */
	public int getTrendingRes() {
		return trendingRes;
	}

	/**
	 * @param trendingRes the trendingRes to set
	 */
	public void setTrendingRes(int trendingRes) {
		this.trendingRes = trendingRes;
	}

}
