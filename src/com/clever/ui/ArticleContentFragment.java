package com.clever.ui;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.clever.model.Doc;
import com.clever.module.dao.ObjectDao;
import com.clever.ui.customized.ArticleViewAdapter;

//Color panels on right side
public class ArticleContentFragment extends Fragment implements
		SwipeRefreshLayout.OnRefreshListener {
	private int mColorRes = -1;
	private SwipeRefreshLayout swipeLayout;
	private Handler handler;
	private ObjectDao docDao;

	// private String result="";

	// ListView to contain articles.
	private ListView listView;
	// ListView adapter
	private ArticleViewAdapter adapter;
	// All articles
	private List<Doc> contentList;

	public ArticleContentFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	// Refresh right panel.
	public void onRefresh() {
	}
}
