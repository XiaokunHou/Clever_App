package com.clever.net.communicate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.clever.model.Doc;

public class ArticlePostRequest extends NetworkRequest {

	// tag id to indicate which articles should be listed
	private int tagID;
	private Doc doc;

	public ArticlePostRequest(Handler hal, Doc doc) {
		this.handler = hal;
		this.doc = doc;
	}

	public void sendPost(String target, Doc doc) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpRequest = new HttpPost(target);

		JSONObject docJson = JsonUtils.DocToJson(doc);
		try {
			// set entity, use utf-8 charset
			StringEntity se = new StringEntity(docJson.toString(), HTTP.UTF_8);
			httpRequest.setEntity(se);

			// set header, user json type
			httpRequest.setHeader("Accept", "application/json");
			httpRequest.setHeader("Content-type", "application/json");

			// set response result
			// ResponseHandler responseHandler=new BasicResponseHandler();
			HttpResponse response = httpclient.execute(httpRequest);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = "success";
			} else {
				result = "fail";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "UnsupportedEncodingException";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "ClientProtocolException";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "IOException";
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendPost("http://10.197.32.102:5000/docs/add?key=iamsmart", doc);

		// send out message and then handler can handle it. And message contain
		// the data
		Message m = handler.obtainMessage();
		Bundle bundle = new Bundle();
		m.what = 0X102;
		bundle.putString("result", result);
		m.setData(bundle);
		handler.sendMessage(m);
	}

}
