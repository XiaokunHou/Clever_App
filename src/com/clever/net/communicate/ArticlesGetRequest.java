package com.clever.net.communicate;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/*
 * Process all requests between server and client.
 */
public class ArticlesGetRequest extends NetworkRequest {

	private boolean needRefresh = true;

	// tag id to indicate which articles should be listed
	private int tagID;

	public ArticlesGetRequest(Handler hal, int tagID) {
		this.handler = hal;
		this.tagID = tagID;
	}

	public void sendGet(String target) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpRequest = new HttpGet(target);
		HttpResponse httpResponse;

		try {
			httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
			} else {
				result = "failed to request";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "network exception";
		} catch (IOException e) {
			e.printStackTrace();
			result = "network exception";
		}
	}

	@Override
	public void run() {
		if (needRefresh == true) {
			sendGet("http://10.197.32.102:5000/doc/recent?begin=1&end=3&format=json");
			Message m = handler.obtainMessage();
			Bundle bundle = new Bundle();
			m.what = 0X101;
			bundle.putString("recentDoc", result);
			m.setData(bundle);
			handler.sendMessage(m);
		}
	}

}
