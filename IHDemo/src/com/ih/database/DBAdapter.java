package com.ih.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ih.model.Shop;
import com.ih.model.User;

public class DBAdapter {

	private static final String TAG = DBAdapter.class.getSimpleName();

	private static final String DATABASE_NAME = "ih.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_NAME_USER = "User";
	private static final String TABLE_NAME_SHOP = "Shop";
	

	private final Context context;

	private DatabaseHelper mOpenHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context context) {
		mOpenHelper = new DatabaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		this.context = context;
	}

	public Context getContext() {
		return this.context;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context cont, String name, CursorFactory factory,
				int version) {
			super(cont, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createTables(db);
		}

		private void createTables(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + " ("
					+ "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "USERNAME " + " TEXT, " + "PASSWORD " + " TEXT, "
					+ "FIRST_NAME" + " TEXT, " + "LAST_NAME " + " TEXT);");

			db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SHOP + " ("
					+ "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "IMAGE_URL " + " TEXT, "
					+ "NAME " + " TEXT, " + "ADDRESS " + " TEXT, "
					+ "CATEGORY" + " TEXT, " + "PHONE " + " TEXT, "+ "WEBSITE" + " TEXT, " + "EMAIL" + " TEXT, " + "FBLIKE" + " INTEGER, " + "HOURS" + " TEXT, " +"SHOP_LATLONG " + " TEXT);");

			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SHOP);

			onCreate(db);
		}

	}

	/**
	 * Opens the database
	 */
	public DBAdapter open() throws SQLiteException {
		try {
			db = mOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = mOpenHelper.getReadableDatabase();
		}
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
	public long insertShop(Shop shop) {
		ContentValues cv = Shop.getContentValues(shop);
		return db.insertWithOnConflict(TABLE_NAME_SHOP, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

	}

	public User getSingleUser(String emailId) {
		Cursor cursor = db.query(TABLE_NAME_USER, null, "USERNAME" + "="
				+ "'"+emailId.trim()+"'", null, null, null, null, "1");

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
		Cursor cursor = db.query(TABLE_NAME_SHOP, null, null, null, null, null, null, "1");

		if (cursor.moveToFirst()) {
			try {
				
				Shop shop = Shop.getShop(cursor);
				cursor.close();
				return shop!=null?shop:null;
			} catch (IllegalArgumentException e) {
			}
		}
		return null;
	}

}
