package com.ih.customwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.ih.demo.R;

/**
 * @author pragati.deshmukh
 * Custom button class. Setting of custom font becomes easier if we use
 * customized widgets.
 * {@link #setCustomFont(Context, AttributeSet)}
 * {@link #setCustomFont(Context, String)}
 * will set the font specified in the XML 
 */
public class CustomButton extends Button {
	private String TAG = "Custom Button";

	public CustomButton(Context context) {
		super(context);
	}

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
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
