package com.clever.ui.customized;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clever.module.Tag;
import com.clever.ui.R;

public class TagArrayAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Tag> tagItems;


	public TagArrayAdapter(Context applicationContext,
			ArrayList<Tag> navDrawerItems) {
		// TODO Auto-generated constructor stub
	this.context=applicationContext;
	this.tagItems=navDrawerItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			LayoutInflater mInflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView=mInflater.inflate(R.layout.tag_item, null);
		}
		
		ImageView imgIcon=(ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle=(TextView) convertView.findViewById(R.id.title);
		TextView txtCount=(TextView) convertView.findViewById(R.id.counter);
		
		imgIcon.setImageResource(tagItems.get(position).getIcon());
		txtTitle.setText(tagItems.get(position).getTagName());
		
		// displaying count
        // check whether it set visible or not
        if(tagItems.get(position).isCounterVisible()){
        	txtCount.setText(tagItems.get(position).getCount());
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        }
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tagItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tagItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
