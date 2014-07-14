package com.clever.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.clever.module.Tag;
import com.clever.module.dao.ObjectDao;
import com.clever.ui.customized.TagArrayAdapter;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView tagDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	// dao utility
	private ObjectDao dao;
	
	// nav drawer title
	private CharSequence mDrawerTitle;
	
	// used to store app title
	private CharSequence mTitle;
	
	// slide menu items, most of them are tags.
	private String[] tags;
	private TypedArray tagIcons;
	
	private ArrayList<Tag> navDrawerItems;
	private TagArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle=mDrawerTitle=getTitle();
		
		// load tags
		tags=getResources().getStringArray(R.array.nav_drawer_items);
		
		//load icons
		tagIcons=getResources().obtainTypedArray(R.array.nav_drawer_icons);
		
		mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
		tagDrawerList=(ListView)findViewById(R.id.list_slidermenu);
		
		navDrawerItems=new ArrayList<Tag>();
		navDrawerItems.add(new Tag(tags[0],tagIcons.getResourceId(0, -1)));
		navDrawerItems.add(new Tag(tags[1],tagIcons.getResourceId(1, -1)));
		navDrawerItems.add(new Tag(tags[2],tagIcons.getResourceId(1, -1)));
		navDrawerItems.add(new Tag(tags[3],tagIcons.getResourceId(1, -1),true,"22"));
		navDrawerItems.add(new Tag(tags[4],tagIcons.getResourceId(1, -1)));
		navDrawerItems.add(new Tag(tags[5],tagIcons.getResourceId(1, -1),true,"50+"));
		
		// Recycle the typed array
		tagIcons.recycle();
		
		tagDrawerList.setOnItemClickListener(new TagClickListener());

		// setting the nav drawer list adapter
		adapter=new TagArrayAdapter(getApplicationContext(),navDrawerItems);
		tagDrawerList.setAdapter(adapter);
	}
	
	/**
	 * Slide menu item click listener
	 * */
	private class TagClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
	
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new ArticleContentFragment();
			break;
		case 2:
			fragment = new ArticleContentFragment();
			break;
		case 3:
			fragment = new ArticleContentFragment();
			break;
		case 4:
			fragment = new ArticleContentFragment();
			break;
		case 5:
			fragment = new ArticleContentFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			tagDrawerList.setItemChecked(position, true);
			tagDrawerList.setSelection(position);
			setTitle(tags[position]);
			mDrawerLayout.closeDrawer(tagDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

}
