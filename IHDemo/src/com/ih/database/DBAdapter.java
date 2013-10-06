package com.ih.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.ih.model.Collection;
import com.ih.model.Product;
import com.ih.model.Shop;
import com.ih.model.User;

public class DBAdapter {

	private static final String TAG = DBAdapter.class.getSimpleName();

	private static String DB_PATH = "/data/data/com.ih.demo/databases/";
	private static final String DATABASE_NAME = "ih.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_NAME_USER = "User";
	private static final String TABLE_NAME_SHOP = "Shop";
	private static final String TABLE_NAME_PRODUCTS = "Products";
	private static final String TABLE_NAME_COLLECTION = "Collection";

	private static Context context;

	private DatabaseHelper mOpenHelper;
	private static SQLiteDatabase db;

	public DBAdapter(Context context) {
		mOpenHelper = new DatabaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		DBAdapter.context = context;
	}

	public Context getContext() {
		return DBAdapter.context;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context cont, String name, CursorFactory factory,
				int version) {
			super(cont, name, factory, version);
		}

		/**
		 * Creates a empty database on the system and rewrites it with your own
		 * database.
		 * */
		public void createDataBase() throws IOException {

			boolean dbExist = checkDataBase();

			if (dbExist) {
				// do nothing - database already exist
			} else {

				// By calling this method and empty database will be created
				// into the default system path
				// of your application so we are gonna be able to overwrite that
				// database with our database.
				this.getReadableDatabase();

				try {

					copyDataBase();

				} catch (IOException e) {

					throw new Error("Error copying database");

				}
			}

		}

		/**
		 * Check if the database already exist to avoid re-copying the file each
		 * time you open the application.
		 * 
		 * @return true if it exists, false if it doesn't
		 */
		private boolean checkDataBase() {

			SQLiteDatabase checkDB = null;

			try {
				String myPath = DB_PATH + DATABASE_NAME;
				checkDB = SQLiteDatabase.openDatabase(myPath, null,
						SQLiteDatabase.OPEN_READONLY);

			} catch (SQLiteException e) {

				// database does't exist yet.

			}

			if (checkDB != null) {

				checkDB.close();

			}

			return checkDB != null ? true : false;
		}

		/**
		 * Copies your database from your local assets-folder to the just
		 * created empty database in the system folder, from where it can be
		 * accessed and handled. This is done by transfering bytestream.
		 * */
		private void copyDataBase() throws IOException {

			// Open your local db as the input stream
			InputStream myInput = context.getAssets().open(DATABASE_NAME);

			// Path to the just created empty db
			String outFileName = DB_PATH + DATABASE_NAME;

			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);

			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();

		}

		@Override
		public synchronized void close() {
			if (db != null)
				db.close();
			super.close();

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("PRAGMA foreign_keys = ON");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

		@Override
		public void onConfigure(SQLiteDatabase db) {
			db.execSQL("PRAGMA foreign_keys = ON");
		}

	}

	public void createDataBase() {
		if (mOpenHelper != null)
			try {
				mOpenHelper.createDataBase();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Opens the database
	 */
	public DBAdapter open() throws SQLiteException {
		// Open the database
		String dbPath = DB_PATH + DATABASE_NAME;
		db = SQLiteDatabase.openDatabase(dbPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		return this;
	}

	/**
	 * Closes the database
	 */
	public void close() {
		if (db.isOpen())
			db.close();
	}

	/**
	 * Drop the specified table
	 */
	public void dropTable(String tableName) {
		db.execSQL("DROP TABLE IF EXISTS " + tableName + ";");
	}

	public long insertNewUser(User user) {
		ContentValues cv = User.getContentValues(user);
		return db.insert(TABLE_NAME_USER, null, cv);

	}

	public long deleteShop() {
		return db.delete(TABLE_NAME_SHOP, null, null);

	}

	public long updateShop(Shop shop) {
		ContentValues cv = Shop.getContentValues(shop);
		return db.updateWithOnConflict(TABLE_NAME_SHOP, cv, "ID=?",
				new String[] { "" + shop.getShopId() },
				SQLiteDatabase.CONFLICT_REPLACE);
	}

	public long insertProduct(Product product) {
		ContentValues cv = Product.getContentValues(product);
		return db.insertWithOnConflict(TABLE_NAME_PRODUCTS, null, cv,
				SQLiteDatabase.CONFLICT_REPLACE);

	}

	public long updateProduct(Product product) {
		ContentValues cv = Product.getContentValues(product);
		return db.updateWithOnConflict(TABLE_NAME_PRODUCTS, cv, "productId=?",
				new String[] { "" + product.getProductId() },
				SQLiteDatabase.CONFLICT_REPLACE);

	}

	public long insertCollection(Collection collection) {
		ContentValues cv = Collection.getContentValues(collection);
		return db.insertWithOnConflict(TABLE_NAME_COLLECTION, null, cv,
				SQLiteDatabase.CONFLICT_REPLACE);

	}

	public User getSingleUser(String emailId) {
		Cursor cursor = db.query(TABLE_NAME_USER, null, "USERNAME" + "=" + "'"
				+ emailId.trim() + "'", null, null, null, null, "1");

		if (cursor.moveToFirst()) {
			try {
				User credential = User.getUserCredential(cursor);
				cursor.close();
				return credential;
			} catch (IllegalArgumentException e) {
			}
		}
		return null;
	}

	public Shop getShop() {
		Cursor cursor = db.query(TABLE_NAME_SHOP, null, null, null, null, null,
				null, "1");

		if (cursor != null && cursor.moveToFirst()) {
			try {

				Shop shop = Shop.getShop(cursor);
				cursor.close();
				return shop != null ? shop : null;
			} catch (IllegalArgumentException e) {
			}
		}
		return null;
	}

	public ArrayList<Product> getProducts() {
		Cursor cursor = db.query(TABLE_NAME_PRODUCTS, null, null, null, null,
				null, null);

		if (cursor != null && cursor.moveToFirst()) {
			try {

				ArrayList<Product> products = Product.getProducts(cursor);
				cursor.close();
				return products != null ? products : null;
			} catch (IllegalArgumentException e) {
			}
		}
		return null;
	}

	public ArrayList<Collection> getCollections(String productId) {
		Cursor cursor = db.rawQuery(
				"select * from Collection where productId=?",
				new String[] { productId });
		if (cursor != null && cursor.moveToFirst()) {
			try {

				ArrayList<Collection> collections = Collection
						.getCollections(cursor);
				cursor.close();
				return collections != null ? collections : null;
			} catch (IllegalArgumentException e) {
			}
		}
		return null;
	}

	public void deleteCollections(String[] collectionId) {
		try {
			db.delete(TABLE_NAME_COLLECTION, "collectionId=?", collectionId);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void deleteProducts(String[] productsId) {
		try {
			db.delete(TABLE_NAME_PRODUCTS, "productId=?", productsId);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public long updateCollection(Collection collection) {
		ContentValues cv = Collection.getContentValues(collection);
		return db.updateWithOnConflict(TABLE_NAME_COLLECTION, cv,
				"collectionId=?",
				new String[] { "" + collection.getCollectionId() },
				SQLiteDatabase.CONFLICT_REPLACE);

	}

}
