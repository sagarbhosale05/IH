/**
 * 
 */
package com.ih.customwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.ih.demo.R;

/**
 * @author abhijeet.bhosale
 * 
 */
public class CustomAutoTextView extends AutoCompleteTextView {

	private String TAG = "CustomAutoTextView";

	public CustomAutoTextView(Context context) {
		super(context);
	}

	public CustomAutoTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public CustomAutoTextView(Context context, AttributeSet attrs, int defStyle) {
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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
