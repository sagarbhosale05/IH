package com.ih.model;

import java.util.ArrayList;

import com.ih.demo.R;

public class Review {

	private int reviewId;
	private int reviewRes;

	private int shopId;

	private String reviewText;
	private String reviewUserName;
	private String reviewDuration;
	private String reviewImageUrl;
	private int reviewRating;

	/**
	 * @return the reviewId
	 */
	public int getReviewId() {
		return reviewId;
	}

	/**
	 * @param reviewId
	 *            the reviewId to set
	 */
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	/**
	 * @return the reviewText
	 */
	public String getReviewText() {
		return reviewText;
	}

	/**
	 * @param reviewText
	 *            the reviewText to set
	 */
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	/**
	 * @return the reviewUserName
	 */
	public String getReviewUserName() {
		return reviewUserName;
	}

	/**
	 * @param reviewUserName
	 *            the reviewUserName to set
	 */
	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}

	/**
	 * @return the reviewDuration
	 */
	public String getReviewDuration() {
		return reviewDuration;
	}

	/**
	 * @param reviewDuration
	 *            the reviewDuration to set
	 */
	public void setReviewDuration(String reviewDuration) {
		this.reviewDuration = reviewDuration;
	}

	/**
	 * @return the reviewImageUrl
	 */
	public String getReviewImageUrl() {
		return reviewImageUrl;
	}

	/**
	 * @param reviewImageUrl
	 *            the reviewImageUrl to set
	 */
	public void setReviewImageUrl(String reviewImageUrl) {
		this.reviewImageUrl = reviewImageUrl;
	}

	/**
	 * @return the reviewRating
	 */
	public int getReviewRating() {
		return reviewRating;
	}

	/**
	 * @param reviewRating
	 *            the reviewRating to set
	 */
	public void setReviewRating(int reviewRating) {
		this.reviewRating = reviewRating;
	}

	public static ArrayList<Review> getReviews() {
		ArrayList<Review> reviews = new ArrayList<Review>();
		Review review = new Review();

		review.setReviewId(1);
		review.setReviewText("Nice Place!");
		review.setReviewUserName("XYZ");
		review.setReviewDuration("2 months ago");
		review.setReviewImageUrl("");
		review.setReviewRating(3);
		reviews.add(review);

		review = new Review();
		review.setReviewId(2);
		review.setReviewText("The Winter Collection is very good.");
		review.setReviewUserName("ABC");
		review.setReviewDuration("3 months ago");
		review.setReviewImageUrl("");
		review.setReviewRating(4);
		reviews.add(review);

		review = new Review();
		review.setReviewId(3);
		review.setReviewText("The Summer Collection is very good.");
		review.setReviewUserName("PQR");
		review.setReviewDuration("1 month ago");
		review.setReviewImageUrl("");
		review.setReviewRating(1);
		reviews.add(review);
		
		review = new Review();
		review.setReviewId(4);
		review.setReviewText("One of the best place to buy clothes in the city. Affordable and good quality.");
		review.setReviewUserName("ABCD");
		review.setReviewDuration("5 days ago");
		review.setReviewImageUrl("");
		review.setReviewRating(2);
		reviews.add(review);

		review = new Review();
		review.setReviewId(5);
		review.setReviewText("Nice collection.");
		review.setReviewUserName("XYZ");
		review.setReviewDuration("1 week ago");
		review.setReviewImageUrl("");
		review.setReviewRating(3);
		reviews.add(review);

		review = new Review();
		review.setReviewId(6);
		review.setReviewText("The Winter Collection is very good.");
		review.setReviewUserName("PQR");
		review.setReviewDuration("2 months ago");
		review.setReviewImageUrl("");
		review.setReviewRating(1);
		reviews.add(review);

		review = new Review();
		review.setReviewId(7);
		review.setReviewText("One of the best place to buy clothes in the city. Affordable and good quality.");
		review.setReviewUserName("ABC");
		review.setReviewDuration("1 month ago");
		review.setReviewImageUrl("");
		review.setReviewRating(5);
		reviews.add(review);

		review = new Review();
		review.setReviewId(8);
		review.setReviewText("Good place.");
		review.setReviewUserName("XYZ");
		review.setReviewDuration("2 days ago");
		review.setReviewImageUrl("");
		review.setReviewRating(3);
		reviews.add(review);

		review = new Review();
		review.setReviewId(9);
		review.setReviewText("The Winter Collection is very good.");
		review.setReviewUserName("Abhijeet");
		review.setReviewDuration("1 day ago");
		review.setReviewImageUrl("");
		review.setReviewRating(4);
		reviews.add(review);

		review = new Review();
		review.setReviewId(10);
		review.setReviewText("One of the best place to buy clothes in the city. Affordable and good quality.");
		review.setReviewUserName("Test");
		review.setReviewDuration("3 months ago");
		review.setReviewImageUrl("");
		review.setReviewRating(1);
		reviews.add(review);

		review = new Review();
		review.setReviewId(11);
		review.setReviewText("The Winter Collection is very good.");
		review.setReviewUserName("TestUser");
		review.setReviewDuration("1 month ago");
		review.setReviewImageUrl("");
		review.setReviewRating(2);
		reviews.add(review);

		return reviews;

	}

	/**
	 * @return the reviewRes
	 */
	public int getReviewRes() {
		return reviewRes;
	}

	/**
	 * @param reviewRes
	 *            the reviewRes to set
	 */
	public void setReviewRes(int reviewRes) {
		this.reviewRes = reviewRes;
	}

	/**
	 * @return the shopId
	 */
	public int getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

}
