package com.clever.ui.customized;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.clever.module.Doc;

public class ArticleViewAdapter extends ArrayAdapter<Doc> {

	private LayoutInflater inflater;
	private Context context = null;

	public ArticleViewAdapter(Context context, List<Doc> list) {
		super(context, 0, list);
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {return null;}
}
