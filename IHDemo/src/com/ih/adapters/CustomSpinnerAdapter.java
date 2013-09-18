package com.ih.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ih.model.Product;

public class CustomSpinnerAdapter extends ArrayAdapter {

	private ArrayList<Product> products;

	public CustomSpinnerAdapter(Context context, int textViewResourceId,
			ArrayList<Product> products) {
		super(context, textViewResourceId);
		this.products = products;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView textView = (TextView) super.getView(position, convertView,
				parent);
		textView.setText(products.get(position).getProductName());
		return textView;
	}
	@Override
	public int getCount() {
		return products!=null?products.size():0;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView textView = (TextView) super.getView(position, convertView,
				parent);
		textView.setText(products.get(position).getProductName());
		return textView;
	}

	@Override
	public Object getItem(int position) {
		return products.get(position);
	}
	public ArrayList<Product> getProducts() {
		return products;
	}

}
