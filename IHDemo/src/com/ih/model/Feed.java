package com.ih.model;

import java.util.ArrayList;

public class Feed {

	private int feedId;
	private String feedText;
	private String feedImageUrl;

	public int getFeedId() {
		return feedId;
	}

	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}

	public String getFeedText() {
		return feedText;
	}

	public void setFeedText(String feedText) {
		this.feedText = feedText;
	}

	public String getFeedImageUrl() {
		return feedImageUrl;
	}

	public void setFeedImageUrl(String feedImageUrl) {
		this.feedImageUrl = feedImageUrl;
	}

	public static ArrayList<Feed> getDummyFeeds() {
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		Feed feed = new Feed();

		feed.setFeedId(1);
		feed.setFeedText("Saturday Shopee added 3 new images in the Summer Collection Album.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(2);
		feed.setFeedText("ABC recommends XYZ jeans at Saturday Shopee.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(3);
		feed.setFeedText("ABC added new album XYZ.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(4);
		feed.setFeedText("ABC recommends XYZ jeans at Saturday Shopee.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(5);
		feed.setFeedText("ABC is having a 60% sale on Winter clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(6);
		feed.setFeedText("Saturday Shopee added 4 new images in the Winter Collection Album.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(7);
		feed.setFeedText("ABC is having a 60% sale on Winter clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(8);
		feed.setFeedText("ABC recommends XYZ jeans at Saturday Shopee.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(9);
		feed.setFeedText("ABC is having a 60% sale on Summer clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(10);
		feed.setFeedText("ABC is having a 60% sale on Winter clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(11);
		feed.setFeedText("Saturday Shopee added 5 new images in the Men's Wear Collection Album.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(12);
		feed.setFeedText("XYZ added new album ABC.");
		feed.setFeedImageUrl("");
		feeds.add(feed);
		
		feed=new Feed();
		feed.setFeedId(13);
		feed.setFeedText("ABC added new album Gold Jwellery.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(14);
		feed.setFeedText("ABC recommends XYZ jeans at Saturday Shopee.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(15);
		feed.setFeedText("XYZ is having a 50% sale on Winter clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(16);
		feed.setFeedText("ABC recommends XYZ jeans at Saturday Shopee.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(17);
		feed.setFeedText("XYZ is having a 50% sale on Summer clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(18);
		feed.setFeedText("Saturday Shopee added 3 new images in the Summer Collection Album.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(19);
		feed.setFeedText("ABC is having a 50% sale on Winter clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(20);
		feed.setFeedText("ABC recommends XYZ jeans at Saturday Shopee.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		feed=new Feed();
		feed.setFeedId(21);
		feed.setFeedText("XYZ is having a 60% sale on Winter clothings.");
		feed.setFeedImageUrl("");
		feeds.add(feed);

		return feeds;

	}

}
