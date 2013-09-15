package com.ih.model;

import android.content.ContentValues;
import android.database.Cursor;

// TODO: Auto-generated Javadoc
/**
 * The Class UserData.
 */
public class User {

	/** The password. */
	private String email, first_name, last_name, password;

	/** The user_token. */
	private String user_token;

	/**
	 * Gets the email id.
	 * 
	 * @return the email id
	 */
	public String getEmailId() {
		return email;
	}

	/**
	 * Sets the email id.
	 * 
	 * @param emailId
	 *            the new email id
	 */
	public void setEmailId(String emailId) {
		this.email = emailId;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return first_name;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return last_name;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the user_token.
	 * 
	 * @return the user_token
	 */
	public String getUser_token() {
		return user_token;
	}

	/**
	 * Sets the user_token.
	 * 
	 * @param user_token
	 *            the new user_token
	 */
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}

	public static ContentValues getContentValues(User user) {

		ContentValues contentValues = new ContentValues();
		contentValues.put("USERNAME", user.getEmailId());
		contentValues.put("PASSWORD", user.getPassword());
		contentValues.put("FIRST_NAME", user.getFirstName());
		contentValues.put("LAST_NAME", user.getLastName());

		return contentValues;
	}

	public static User getUserCredential(Cursor cursor) {

		User user = new User();
		user.setEmailId(cursor.getString(cursor
				.getColumnIndexOrThrow("USERNAME")));
		user.setFirstName(cursor.getString(cursor
				.getColumnIndexOrThrow("FIRST_NAME")));
		user.setLastName(cursor.getString(cursor
				.getColumnIndexOrThrow("LAST_NAME")));
		user.setPassword(cursor.getString(cursor
				.getColumnIndexOrThrow("PASSWORD")));

		return user;
	}

}
