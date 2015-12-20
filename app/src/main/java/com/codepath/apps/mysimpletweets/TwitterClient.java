package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "NhOTbdIdKjO0fvtdBgZG7kXxK";       // Change this
	public static final String REST_CONSUMER_SECRET = "nfR5u4kylMl7JpK8BfeMpXLluLWgi9BGOF6HbmoXmUSrT4Ra2A"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)
	public static final Integer COUNT = 25;
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(long sinceId, long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", COUNT);
		if (sinceId > 0) {
			params.put("since_id", String.valueOf(sinceId));
		} else if (maxId > 0) {
			params.put("max_id", String.valueOf(maxId));
		} else {
			params.put("since_id", 1);
		}
		getClient().get(apiUrl, params, handler);
	}

	public void postTweet(String tweet, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		client.post(apiUrl, params, handler);
	}

	public void getCredentials(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		RequestParams params = new RequestParams();
		getClient().get(apiUrl, params, handler);
	}

	public void getMentionsTimeline(long sinceId, long maxId, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", COUNT);
		if (sinceId > 0) {
			params.put("since_id", String.valueOf(sinceId));
		} else if (maxId > 0) {
			params.put("max_id", String.valueOf(maxId));
		} else {
			params.put("since_id", 1);
		}
		getClient().get(apiUrl, params, handler);
	}
}