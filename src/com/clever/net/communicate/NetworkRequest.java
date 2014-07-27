package com.clever.net.communicate;

import android.os.Handler;

public abstract class NetworkRequest implements Runnable {
	protected String result = "";
	protected Handler handler;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
