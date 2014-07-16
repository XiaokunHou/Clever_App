package com.clever.ui;

import java.util.List;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.clever.module.Doc;
import com.clever.module.dao.ObjectDao;
import com.clever.net.communicate.ArticlesGetRequest;
import com.clever.net.communicate.JsonUtils;
import com.clever.ui.customized.ArticleViewAdapter;

public class HomeFragment extends Fragment implements
		SwipeRefreshLayout.OnRefreshListener {
	private SwipeRefreshLayout swipeLayout;
	private ObjectDao dao;
	private int colorRes = -1;
	private Handler handler;

	// all articles
	private List<Doc> articleList;
	// listView to contain articles
	private ListView articleView;
	// listView adapter
	private ArticleViewAdapter adapter;
	// tag ID has been clicked
	private int tagID;

	// use bundle to pass object from activity to fragment
	public static HomeFragment newInstance(ObjectDao dao, int color, int tagID) {
		HomeFragment fragment = new HomeFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("dao", dao);
		bundle.putInt("color", color);
		bundle.putInt("tagID", tagID);
		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// get data from activity
		dao = (ObjectDao) getArguments().getSerializable("dao");
		colorRes = getArguments().getInt("color");
		tagID=getArguments().getInt("tagID");
		
		View rootView = inflater.inflate(R.layout.articles, container, false);
		swipeLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.swipe_refresh);
		swipeLayout.setOnRefreshListener(this);

		// top refresh style
		swipeLayout.setColorScheme(android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light);

		// get dao and query data from server
		articleList = dao.getAllDoc();
		articleView = (ListView) rootView.findViewById(R.id.listview);
		adapter = new ArticleViewAdapter(getActivity(), articleList);
		articleView.setAdapter(adapter);

		// set background color
		int color = getResources().getColor(colorRes);
		rootView.setBackgroundColor(color);

		return rootView;
	}

	// refresh articles
	@Override
	public void onRefresh() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				articleList.clear();
				int msgCode = msg.what;
				String recentDoc = msg.getData().getString("recentDoc");
				if (msgCode == 0x101 && !(recentDoc.equals(""))) {
					List<Doc> docs = JsonUtils.parseDocs(recentDoc);
					// save fetched data
					for (Doc doc : docs) {
						dao.saveDoc(doc);
					}
					// read data from sqlite and display them
					Cursor cursor = dao.getAllDocCur();
					while (cursor.moveToNext()) {
						if (!("".equals(cursor.getString(1)))) {
							Doc tem = new Doc();
							tem.setDocID(cursor.getInt(0));
							tem.setContent(cursor.getString(1));
							articleList.add(tem);
						}
					}
					adapter.notifyDataSetChanged();
					swipeLayout.setRefreshing(false);
				}
				super.handleMessage(msg);
			}
		};

		// get request to fetch articles from server
		ArticlesGetRequest get = new ArticlesGetRequest(handler, tagID);
		Thread thread = new Thread(get);
		thread.start();

	}
}
