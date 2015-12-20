package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;

public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private long maxId = 0;
    private long prevMaxId = 0;
    private User user;
    private ImageView ivProfileImage;
    private TextView tvName;
    private TextView tvTagline;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeline(0, 0);
    }

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screenName) {
        //Log.d("DEBUG ", "UserTimelineFragment " + screenName);
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("srceen_name", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    public void populateTimeline(long sinceId, long maxId) {
        String screenName = getArguments().getString("srceen_name");
        //Log.d("DEBUG ", screenName);
        client.getUserTimeline(sinceId, maxId, screenName, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG ", errorResponse.toString());
            }
        });
    }

    public void refreshTimeline() {
        Tweet firstTweet = getItem(0);
        long sinceId = firstTweet.getUid();
        populateTimeline(sinceId, 0);
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
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
