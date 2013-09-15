package com.ih.customwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.ih.demo.R;

/**
 * @author pragati.deshmukh
 * Custom Edittext class. Setting of custom font becomes easier if we use
 * customized widgets.
 * {@link #setCustomFont(Context, AttributeSet)}
 * {@link #setCustomFont(Context, String)}
 * will set the font specified in the XML 
 */
public class CustomEditText extends EditText
{
	private String TAG = "Custom Edit Text";

	public CustomEditText(Context context) {
		super(context);
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}

	private void setCustomFont(Context ctx, AttributeSet attrs) {
		TypedArray a = ctx.obtainStyledAttributes(attrs,
				R.styleable.CustomTextView);

		String customFont = a.getString(R.styleable.CustomTextView_customFont);

		setCustomFont(ctx, customFont);
		a.recycle();
	}

	public boolean setCustomFont(Context ctx, String asset) {
		Typeface tf = null;
		try {
			tf = Typefaces.get(ctx, asset);

		} catch (Exception e) {

			Log.e(TAG, "Could not get typeface: " + e.getMessage());
			return false;
		}

		setTypeface(tf);
		return true;
	}

}
