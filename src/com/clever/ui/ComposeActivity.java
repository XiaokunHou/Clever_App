package com.clever.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clever.model.Doc;
import com.clever.net.communicate.ArticlePostRequest;

public class ComposeActivity extends Activity implements Serializable {
	//
	private Handler handler;
	Toast toast;

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
			handler = new Handler() {
				public void handleMessage(Message msg) {
					int msgCode = msg.what;
					String result = msg.getData().getString("result");
					if (msgCode == 0x102) {
						if (result.equals("success")) {

							toast.cancel();

							finish();
						}
					}
				}
			};

			// instantiate Doc object from GUI
			EditText et = (EditText) findViewById(R.id.article_body);
			String articleBody = et.getText().toString();
			Doc doc = new Doc();
			doc.setContent(articleBody);
			doc.setTitle("");
			// doc.setTagIDs(new ArrayList<Integer>() {{ add(1); }});
			doc.setTagIDs(new ArrayList(Arrays.asList(1)));

			// post doc to server in new thread
			ArticlePostRequest post = new ArticlePostRequest(handler, doc);
			Thread thread = new Thread(post);
			thread.start();

			// initialize toast
			initToast();
			toast.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void initToast() {
		Context context = getApplicationContext();
		CharSequence text = "正在发布呦";

		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.refresh_toast_layout,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		TextView textView = (TextView) layout.findViewById(R.id.text);
		textView.setText(text);

		toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		// toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
	}
}
