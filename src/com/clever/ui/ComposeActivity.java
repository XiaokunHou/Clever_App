package com.clever.ui;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends Activity implements Serializable {

	private static final long serialVersionUID = 2757626533040825654L;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.compose_article);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// // toggle nav drawer on selecting action bar app icon/title
		// if (mDrawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.compose:
			showToast();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showToast() {
		Context context = getApplicationContext();
		CharSequence text = "正在发布呦";
		int duration = Toast.LENGTH_LONG;

		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.refresh_toast_layout,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		TextView textView = (TextView) layout.findViewById(R.id.text);
		textView.setText(text);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
}
