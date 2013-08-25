package com.ih.model;

import java.util.ArrayList;

import com.ih.demo.R;

public class Product {

	private int productId;
	private int productRes;

	private String productName;
	private int productCoollectionCount;
	private String productImageUrl;

	private ArrayList<Collection> collections;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductCoollectionCount() {
		return productCoollectionCount;
	}

	public void setProductCoollectionCount(int productCoollectionCount) {
		this.productCoollectionCount = productCoollectionCount;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public ArrayList<Collection> getCollections() {
		return collections;
	}

	public void setCollections(ArrayList<Collection> collections) {
		this.collections = collections;
	}

	public static ArrayList<Product> getproducts() {
		ArrayList<Product> products = new ArrayList<Product>();
		ArrayList<Collection> collections = new ArrayList<Collection>();
		Collection collection = new Collection();

		collection.setCollectionId(1);
		collection.setCollectionRes(R.drawable.a);
		collection.setCollectionName("Levi Jeans");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1410);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.b);
		collection.setCollectionId(2);
		collection.setCollectionName("Muffler");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.c);
		collection.setCollectionId(3);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1520);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.d);
		collection.setCollectionId(4);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1480);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.e);
		collection.setCollectionId(5);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1400);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.f);
		collection.setCollectionId(6);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1500);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.g);
		collection.setCollectionId(7);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.h);
		collection.setCollectionId(8);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1600);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.i);
		collection.setCollectionId(9);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1220);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.j);
		collection.setCollectionId(10);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.k);
		collection.setCollectionId(11);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1520);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.l);
		collection.setCollectionId(12);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.m);
		collection.setCollectionId(13);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1640);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.n);
		collection.setCollectionId(14);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.o);
		collection.setCollectionId(15);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.p);
		collection.setCollectionId(16);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		collection = new Collection();
		collection.setCollectionRes(R.drawable.u);
		collection.setCollectionId(17);
		collection.setCollectionName("ABC");
		collection.setCollectionDescription("Dummy Description");
		collection.setCollectionMaterial("Dummy Material");
		collection.setCollectionPrice(1420);
		collection.setCollectionSizeDimensions("Dummy Size/Dimension");
		collection.setCollectiontFollowed(false);
		collection.setCollectiontImageUrl("");
		collection.setCollectiontInStock(true);
		collections.add(collection);

		Product product = new Product();

		product.setProductId(1);
		product.setProductRes(R.drawable.g);
		product.setProductName("Mens Winter Collection");
		product.setProductCoollectionCount(34);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		product = new Product();
		product.setProductRes(R.drawable.f);
		product.setProductId(2);
		product.setProductName("Kids Winter Collection");
		product.setProductCoollectionCount(64);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		product = new Product();
		product.setProductRes(R.drawable.k);
		product.setProductId(3);
		product.setProductName("Pullovers");
		product.setProductCoollectionCount(20);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		product = new Product();
		product.setProductRes(R.drawable.b);
		product.setProductId(4);
		product.setProductName("Levi Strauss Jeans");
		product.setProductCoollectionCount(24);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		product = new Product();
		product.setProductRes(R.drawable.r);
		product.setProductId(5);
		product.setProductName("Women Winter Collection");
		product.setProductCoollectionCount(34);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		product = new Product();
		product.setProductRes(R.drawable.q);
		product.setProductId(6);
		product.setProductName("Mens Summer Collection");
		product.setProductCoollectionCount(34);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		product = new Product();
		product.setProductRes(R.drawable.e);
		product.setProductId(7);
		product.setProductName("Women Summer Collection");
		product.setProductCoollectionCount(34);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		product = new Product();
		product.setProductRes(R.drawable.a);
		product.setProductId(8);
		product.setProductName("Mens Jeans");
		product.setProductCoollectionCount(15);
		product.setProductImageUrl("");
		product.setCollections(collections);
		products.add(product);

		return products;

	}

	/**
	 * @return the productRes
	 */
	public int getProductRes() {
		return productRes;
	}

	/**
	 * @param productRes
	 *            the productRes to set
	 */
	public void setProductRes(int productRes) {
		this.productRes = productRes;
	}

}
