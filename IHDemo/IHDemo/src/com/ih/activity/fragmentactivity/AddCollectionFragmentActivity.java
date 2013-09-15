package com.ih.activity.fragmentactivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ih.BaseActivity;
import com.ih.adapters.CustomSpinnerAdapter;
import com.ih.database.DBAdapter;
import com.ih.demo.R;
import com.ih.model.Collection;
import com.ih.model.Product;

public class AddCollectionFragmentActivity extends BaseActivity {

	private DBAdapter dbAdapter;
	private Collection collection;
	private EditText collectionNameEditText, collectionDescriptionEditText,
			collectionBrandEditText, collectionPriceEditText,
			collectionMaterialEditText, collectionSizeDimensionEditText;
	private AlertDialog dialog;
	private String imageUrl;
	private ImageView collectionImageView;
	private Spinner spinner;
	private CustomSpinnerAdapter customSpinnerAdapter;
	private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_addcollection);
		setActionBarHomeAsUpEnabled(true);
		initUI();

	}

	private void initUI() {
		spinner = (Spinner) findViewById(R.id.album_spinner);
		radioGroup = (RadioGroup) findViewById(R.id.instockRG);
		collection = new Collection();
		dbAdapter = new DBAdapter(this);
		try {
			dbAdapter.open();
			ArrayList<Product> products = dbAdapter.getProducts();
			customSpinnerAdapter = new CustomSpinnerAdapter(this,
					android.R.layout.simple_spinner_item, products);
			spinner.setAdapter(customSpinnerAdapter);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (dbAdapter != null)
				dbAdapter.close();
		}
		collectionNameEditText = (EditText) findViewById(R.id.name_edittext);
		collectionDescriptionEditText = (EditText) findViewById(R.id.description_edittext);
		collectionBrandEditText = (EditText) findViewById(R.id.brand_edittext);
		collectionPriceEditText = (EditText) findViewById(R.id.price_edittext);
		collectionImageView = (ImageView) findViewById(R.id.collection_picture);

	}

	public void onClick(View onClick) {
		switch (onClick.getId()) {
		case R.id.add_collection:
			if (checkNoFieldEmpty()) {
				insertCollection();

			} else {
				dialog.setMessage(getString(R.string.mandatory_fields));
				dialog.show();
			}
			break;

		case R.id.collection_picture:
			openImageIntent();
			break;
		}
	}

	private void insertCollection() {
		Product product = (Product) customSpinnerAdapter.getItem(spinner
				.getSelectedItemPosition());
		collection.setCollectionName(collectionNameEditText.getText()
				.toString().trim());
		collection.setProductId(product.getProductId());
		collection.setCollectionBrand(collectionBrandEditText.getText()
				.toString().trim());
		collection.setCollectionDescription(collectionDescriptionEditText
				.getText().toString().trim());
		collection.setCollectionPrice(collectionPriceEditText.getText()
				.toString().trim());
		collection.setCollectiontImageUrl(imageUrl);
		int inStock = 0;
		switch (radioGroup.getCheckedRadioButtonId()) {
		case R.id.yesRb:
			inStock = 0;
			break;
		case R.id.noRb:
			inStock = 1;
			break;
		case R.id.soonRb:
			inStock = 2;
			break;
		}
		collection.setCollectiontInStock(inStock);

		try {
			if (dbAdapter != null) {
				dbAdapter.open();
				dbAdapter.insertCollection(collection);
				Toast.makeText(getBaseContext(),
						"Collection saved succusfully", Toast.LENGTH_SHORT)
						.show();
				clear();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbAdapter != null)
				dbAdapter.close();
		}

	}

	private void clear() {

		spinner.setSelection(0);
		collectionBrandEditText.setText("");
		collectionDescriptionEditText.setText("");
		collectionPriceEditText.setText("");
		collectionNameEditText.setText("");
		imageUrl = null;
		((RadioButton) findViewById(R.id.yesRb)).setChecked(true);
		collectionImageView.setImageResource(R.drawable.ic_launcher);
	}

	private boolean checkNoFieldEmpty() {
		if (collectionNameEditText.getText().toString().trim().length() == 0
				|| collectionBrandEditText.getText().toString().trim().length() == 0
				|| collectionPriceEditText.getText().toString().trim().length() == 0
				|| collectionDescriptionEditText.getText().toString().trim()
						.length() == 0) {
			if (collectionNameEditText.getText().toString().trim().length() == 0)
				collectionNameEditText.setText("");
			if (collectionBrandEditText.getText().toString().trim().length() == 0)
				collectionBrandEditText.setText("");
			if (collectionDescriptionEditText.getText().toString().trim()
					.length() == 0)
				collectionDescriptionEditText.setText("");
			if (collectionPriceEditText.getText().toString().trim().length() == 0)
				collectionPriceEditText.setText("");
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
			imageUrl = outputFileUri.toString();
			collectionImageView.setImageURI(outputFileUri);
		} else
			imageUrl = null;
	}

}
