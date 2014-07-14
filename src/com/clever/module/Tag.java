package com.clever.module;

import java.util.ArrayList;

/*
 * Definition for category.
 */
public class Tag {
	private int tagID;
	private String tagName;
	private int icon;
	private String count = "0";
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	// Every category has many articles.
	private ArrayList<Doc> docs = new ArrayList<Doc>();

	public Tag() {
	}

	public Tag(int id, String name) {
		this.tagID = id;
		this.tagName = name;
	}

	public Tag(String title, int resourceId) {
		// TODO Auto-generated constructor stub
		this.tagName = title;
		this.icon = resourceId;
	}

	public Tag(String title, int icon, boolean isCounterVisible, String count){
		this.tagName = title;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}

	public int getTagID() {
		return tagID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public ArrayList<Doc> getDocs() {
		return docs;
	}

	public void setDocs(ArrayList<Doc> docs) {
		this.docs = docs;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public boolean isCounterVisible() {
		return isCounterVisible;
	}

	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}
	
}
