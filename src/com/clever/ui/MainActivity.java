package com.clever.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.clever.model.Tag;
import com.clever.module.dao.ObjectDao;
import com.clever.ui.customized.TagArrayAdapter;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView tagDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// dao utility
	public ObjectDao dao;

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
		// initialize dao object
		dao = new ObjectDao(this);

		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load tags
		tags = getResources().getStringArray(R.array.nav_drawer_items);

		// load icons
		tagIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		tagDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<Tag>();
		navDrawerItems.add(new Tag(tags[0], tagIcons.getResourceId(0, -1)));
		navDrawerItems.add(new Tag(tags[1], tagIcons.getResourceId(1, -1)));
		navDrawerItems.add(new Tag(tags[2], tagIcons.getResourceId(1, -1)));
		navDrawerItems.add(new Tag(tags[3], tagIcons.getResourceId(1, -1),
				true, "22"));
		navDrawerItems.add(new Tag(tags[4], tagIcons.getResourceId(1, -1)));
		navDrawerItems.add(new Tag(tags[5], tagIcons.getResourceId(1, -1),
				true, "50+"));

		// Recycle the typed array
		tagIcons.recycle();

		tagDrawerList.setOnItemClickListener(new TagClickListener());

		// setting the nav drawer list adapter
		adapter = new TagArrayAdapter(getApplicationContext(), navDrawerItems);
		tagDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class TagClickListener implements ListView.OnItemClickListener {
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
			fragment = HomeFragment.newInstance(dao, R.color.Health_T, 0);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(MainActivity.this, ComposeActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(tagDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public ObjectDao getDao() {
		return dao;
	}

}
