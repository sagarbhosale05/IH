package com.ih.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

public class Collection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int collectionId;
	private int productId;

	private String collectionRes;

	private String collectionName;
	private String collectionBrand;

	private String collectionPrice;
	private String collectiontImageUrl;
	private int isCollectiontInStock;
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
	public String getCollectionPrice() {
		return collectionPrice;
	}

	/**
	 * @param collectionPrice
	 *            the collectionPrice to set
	 */
	public void setCollectionPrice(String collectionPrice) {
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
	public int isCollectiontInStock() {
		return isCollectiontInStock;
	}

	/**
	 * @param isCollectiontInStock
	 *            the isCollectiontInStock to set
	 */
	public void setCollectiontInStock(int isCollectiontInStock) {
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
	public String getCollectionRes() {
		return collectionRes;
	}

	/**
	 * @param collectionRes
	 *            the collectionRes to set
	 */
	public void setCollectionRes(String collectionRes) {
		this.collectionRes = collectionRes;
	}

	public static ArrayList<Collection> getCollections(Cursor cursor) {
		ArrayList<Collection> collections = new ArrayList<Collection>();
		do {
			Collection collection = new Collection();
			collection.setCollectionId(cursor.getInt(cursor
					.getColumnIndex("collectionId")));
			collection.setProductId(cursor.getInt(cursor
					.getColumnIndex("productId")));
			collection.setCollectionName(cursor.getString(cursor
					.getColumnIndex("name")));
			collection.setCollectionDescription(cursor.getString(cursor
					.getColumnIndex("description")));
			collection.setCollectionMaterial(cursor.getString(cursor
					.getColumnIndex("material")));
			collection.setCollectionPrice(cursor.getString(cursor
					.getColumnIndex("price")));
			collection.setCollectionBrand(cursor.getString(cursor
					.getColumnIndex("brand")));
			collection.setCollectiontImageUrl(cursor.getString(cursor
					.getColumnIndex("imageUrl")));
			collection.setCollectiontInStock(cursor.getInt(cursor
					.getColumnIndex("inStock")));
			collection.setCollectionSizeDimensions(cursor.getString(cursor
					.getColumnIndex("sizedimension")));
			collection.setCollectionRes(cursor.getString(cursor
					.getColumnIndex("collectionLocalImageRes")));
			
			collections.add(collection);

		} while (cursor.moveToNext());
		return collections;

	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the collectionBrand
	 */
	public String getCollectionBrand() {
		return collectionBrand;
	}

	/**
	 * @param collectionBrand
	 *            the collectionBrand to set
	 */
	public void setCollectionBrand(String collectionBrand) {
		this.collectionBrand = collectionBrand;
	}

	public static ContentValues getContentValues(Collection collection) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("productId", collection.getProductId());
		contentValues.put("name", collection.getCollectionName());
		contentValues.put("description", collection.getCollectionDescription());
		contentValues.put("brand", collection.getCollectionBrand());
		contentValues.put("material", collection.getCollectionMaterial());
		contentValues.put("price", collection.getCollectionPrice());
		contentValues.put("imageUrl", collection.getCollectiontImageUrl());
		contentValues.put("collectionLocalImageRes", collection.getCollectionRes());
		contentValues.put("sizedimension", collection.getCollectionSizeDimensions());
		
		return contentValues;

	}

}
