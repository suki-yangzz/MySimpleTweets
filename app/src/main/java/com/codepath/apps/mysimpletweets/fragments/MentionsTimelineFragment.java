package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private long maxId = 0;
    private long prevMaxId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeline(0, 0);
    }

    public void populateTimeline(long sinceId, long maxId) {
        client.getMentionsTimeline(sinceId, maxId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            }
        });
    }

    protected void refreshTimeline() {
        Tweet firstTweet = getItem(0);
        long sinceId = firstTweet.getUid();
        populateTimeline(sinceId, 0);
    }

    // Append more data into the adapter
    protected void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        Tweet lastTweet = getItem(getCount() - 1);
        maxId = lastTweet.getUid();
        if (maxId == prevMaxId){
            return;
        }
        prevMaxId = maxId;
        populateTimeline(0, maxId - 1);
    }
}
