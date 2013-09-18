package com.ih.activity.fragmentactivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ih.BaseActivity;
import com.ih.database.DBAdapter;
import com.ih.demo.R;
import com.ih.model.Shop;
import com.ih.utility.Utility;

public class EditProfileFragmentActivity extends BaseActivity {

	private DBAdapter dbAdapter;
	private Shop shop;
	private EditText shopNameEditText, shopAddressEditText, shopWebsiteEditText,
			shopEmailEditText, shopHoursEditText, shopFBLikeEditText,
			shopCategoryEditText, shopPhoneEditText, shopLatLngEditText;
	private AlertDialog dialog;
	private String imageUrl;
	private ImageView shopImageView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit_profile);
		setActionBarHomeAsUpEnabled(true);
		initUI();

	}

	private void initUI() {

		shopNameEditText = (EditText) findViewById(R.id.name_edittext);
		shopAddressEditText = (EditText) findViewById(R.id.address_edittext);
		shopWebsiteEditText = (EditText) findViewById(R.id.website_edittext);
		shopEmailEditText = (EditText) findViewById(R.id.email_edittext);
		shopCategoryEditText = (EditText) findViewById(R.id.category_edittext);
		shopFBLikeEditText = (EditText) findViewById(R.id.fblike_edittext);
		shopPhoneEditText = (EditText) findViewById(R.id.phone_edittext);
		shopHoursEditText = (EditText) findViewById(R.id.hours_edittext);
		shopWebsiteEditText = (EditText) findViewById(R.id.website_edittext);
		shopLatLngEditText = (EditText) findViewById(R.id.latlong_edittext);
		shopImageView = (ImageView) findViewById(R.id.shop_picture);
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		shop = dbAdapter.getShop();
		if (shop != null)

		{
			shopNameEditText.setText("" + shop.getShopName());
			shopAddressEditText.setText("" + shop.getShopAddress());
			shopWebsiteEditText.setText("" + shop.getShopWebSite());
			shopCategoryEditText.setText("" + shop.getShopCategory());
			shopFBLikeEditText.setText("" + shop.getShopFBLike());
			shopHoursEditText.setText("" + shop.getShopHrs());
			shopLatLngEditText.setText("" + shop.getShopLat() + ","	+ shop.getShopLong());
			if (!TextUtils.isEmpty(shop.getShopImageUrl()))
				shopImageView.setImageURI(Uri.parse(shop.getShopImageUrl()));
			shopEmailEditText.setText("" + shop.getShopEmail());
			shopPhoneEditText.setText("" + shop.getShopPhone());
		} else
			shop = new Shop();
		dialog = new AlertDialog.Builder(this).create();
		dialog.setButton(AlertDialog.BUTTON_NEUTRAL,
				getString(R.string.ok_btn_text),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

	}

	public void onClick(View onClick) {
		switch (onClick.getId()) {
		case R.id.btn_save:
			if (checkNoFieldEmpty()) {
				if (Utility.checkEmailCorrect(shopEmailEditText.getText()
						.toString())) {
					insertShop();

				} else {
					dialog.setMessage(getString(R.string.valid_email));
					dialog.show();
				}
			} else {
				dialog.setMessage(getString(R.string.mandatory_fields));
				dialog.show();
			}
			break;
			
		case R.id.shop_picture:
			openImageIntent();
			break;
		}
	}

	private void insertShop() {

		shop.setShopAddress(shopAddressEditText.getText().toString().trim());
		shop.setShopName(shopNameEditText.getText().toString().trim());
		shop.setShopCategory(shopCategoryEditText.getText().toString().trim());
		shop.setShopPhone(shopPhoneEditText.getText().toString().trim());
		shop.setShopEmail(shopEmailEditText.getText().toString().trim());
		shop.setShopFBLike(shopFBLikeEditText.getText().toString().trim());
		shop.setShopHrs(shopHoursEditText.getText().toString().trim());
		shop.setShopImageUrl(imageUrl);
		shop.setShopLat(shopLatLngEditText.getText().toString().trim()
				.split(",")[0]);
		shop.setShopLong(shopLatLngEditText.getText().toString().trim()
				.split(",")[1]);
		shop.setShopWebSite(shopWebsiteEditText.getText().toString().trim());
		try {
			dbAdapter.deleteShop();
			dbAdapter.insertShop(shop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utility.showToast(getBaseContext(), "Shop details saved successfully");
		onBackPressed();
	}

	private boolean checkNoFieldEmpty() {
		if (shopNameEditText.getText().toString().trim().length() == 0
				|| shopAddressEditText.getText().toString().trim().length() == 0
				|| shopPhoneEditText.getText().toString().trim().length() == 0
				|| shopCategoryEditText.getText().toString().trim().length() == 0) {
			if (shopNameEditText.getText().toString().trim().length() == 0)
				shopNameEditText.setText("");
			if (shopAddressEditText.getText().toString().trim().length() == 0)
				shopAddressEditText.setText("");
			if (shopPhoneEditText.getText().toString().trim().length() == 0)
				shopPhoneEditText.setText("");
			if (shopCategoryEditText.getText().toString().trim().length() == 0)
				shopCategoryEditText.setText("");
			return false;
		}
		return true;
	}

	private Uri outputFileUri;

	private void openImageIntent() {

		// Determine Uri of camera image to save.
		final File root = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "IH" + File.separator);
		root.mkdirs();
		final File sdImageMainDirectory = new File(root, ""
				+ System.currentTimeMillis());
		outputFileUri = Uri.fromFile(sdImageMainDirectory);

		// Camera.
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(
				captureIntent, 0);
		for (ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent intent = new Intent(captureIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName,
					res.activityInfo.name));
			intent.setPackage(packageName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			cameraIntents.add(intent);
		}

		// Filesystem.
		final Intent galleryIntent = new Intent();
		galleryIntent.setType("image/*");
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

		// Chooser of filesystem options.
		final Intent chooserIntent = Intent.createChooser(galleryIntent,
				"Select Source");

		// Add the camera options.
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
				cameraIntents.toArray(new Parcelable[] {}));

		startActivityForResult(chooserIntent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				final boolean isCamera;
				if (data == null) {
					isCamera = true;
				} else {
					final String action = data.getAction();
					if (action == null) {
						isCamera = false;
					} else {
						isCamera = action
								.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					}
				}

				if (!isCamera) {
					outputFileUri = data == null ? null : data.getData();
				}
			}
			imageUrl=outputFileUri.toString();
			shopImageView.setImageURI(outputFileUri);
		}
		else
			imageUrl=null;
	}

}
