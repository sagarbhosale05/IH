package com.ih.model;

import java.io.Serializable;

public class Collection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int collectionId;
	private int collectionRes;

	private String collectionName;
	private int collectionPrice;
	private String collectiontImageUrl;
	private boolean isCollectiontInStock;
	private String collectionDescription;
	private String collectionMaterial;
	private String collectionSizeDimensions;
	private boolean isCollectiontFollowed;

	/**
	 * @return the collectionId
	 */
	public int getCollectionId() {
		return collectionId;
	}

	/**
	 * @param collectionId
	 *            the collectionId to set
	 */
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return the collectionName
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * @param collectionName
	 *            the collectionName to set
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * @return the collectionPrice
	 */
	public int getCollectionPrice() {
		return collectionPrice;
	}

	/**
	 * @param collectionPrice
	 *            the collectionPrice to set
	 */
	public void setCollectionPrice(int collectionPrice) {
		this.collectionPrice = collectionPrice;
	}

	/**
	 * @return the collectiontImageUrl
	 */
	public String getCollectiontImageUrl() {
		return collectiontImageUrl;
	}

	/**
	 * @param collectiontImageUrl
	 *            the collectiontImageUrl to set
	 */
	public void setCollectiontImageUrl(String collectiontImageUrl) {
		this.collectiontImageUrl = collectiontImageUrl;
	}

	/**
	 * @return the isCollectiontInStock
	 */
	public boolean isCollectiontInStock() {
		return isCollectiontInStock;
	}

	/**
	 * @param isCollectiontInStock
	 *            the isCollectiontInStock to set
	 */
	public void setCollectiontInStock(boolean isCollectiontInStock) {
		this.isCollectiontInStock = isCollectiontInStock;
	}

	/**
	 * @return the collectionDescription
	 */
	public String getCollectionDescription() {
		return collectionDescription;
	}

	/**
	 * @param collectionDescription
	 *            the collectionDescription to set
	 */
	public void setCollectionDescription(String collectionDescription) {
		this.collectionDescription = collectionDescription;
	}

	/**
	 * @return the collectionMaterial
	 */
	public String getCollectionMaterial() {
		return collectionMaterial;
	}

	/**
	 * @param collectionMaterial
	 *            the collectionMaterial to set
	 */
	public void setCollectionMaterial(String collectionMaterial) {
		this.collectionMaterial = collectionMaterial;
	}

	/**
	 * @return the collectionSizeDimensions
	 */
	public String getCollectionSizeDimensions() {
		return collectionSizeDimensions;
	}

	/**
	 * @param collectionSizeDimensions
	 *            the collectionSizeDimensions to set
	 */
	public void setCollectionSizeDimensions(String collectionSizeDimensions) {
		this.collectionSizeDimensions = collectionSizeDimensions;
	}

	/**
	 * @return the isCollectiontFollowed
	 */
	public boolean isCollectiontFollowed() {
		return isCollectiontFollowed;
	}

	/**
	 * @param isCollectiontFollowed
	 *            the isCollectiontFollowed to set
	 */
	public void setCollectiontFollowed(boolean isCollectiontFollowed) {
		this.isCollectiontFollowed = isCollectiontFollowed;
	}

	/**
	 * @return the collectionRes
	 */
	public int getCollectionRes() {
		return collectionRes;
	}

	/**
	 * @param collectionRes
	 *            the collectionRes to set
	 */
	public void setCollectionRes(int collectionRes) {
		this.collectionRes = collectionRes;
	}

}
