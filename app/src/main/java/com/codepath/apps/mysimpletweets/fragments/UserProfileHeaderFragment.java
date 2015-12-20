package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class UserProfileHeaderFragment extends Fragment {
    private TwitterClient client;
    private User user;
    private ImageView ivProfileImage;
    private TextView tvName;
    private TextView tvTagline;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private static String requestedScreenName;

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserProfileHeaderFragment newInstance(String screenName) {
        requestedScreenName = screenName;
        UserProfileHeaderFragment userProfileHeaderFragment = new UserProfileHeaderFragment();
        Bundle args = new Bundle();
        args.putString("srceen_name", screenName);
        userProfileHeaderFragment.setArguments(args);
        return userProfileHeaderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_header, container, false);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvTagline = (TextView) v.findViewById(R.id.tvTagline);
        tvFollowers = (TextView) v.findViewById(R.id.tvFollowers);
        tvFollowing = (TextView) v.findViewById(R.id.tvFollowing);
        ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        client = TwitterApplication.getRestClient();
        //String screenName = getArguments().getString("screen_name");
        populateProfileHeader(requestedScreenName);
        return v;
    }

    public void populateProfileHeader(String screenName) {
        if (screenName == null) {
            client.getCredentials(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    setProfileHeader(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        } else {
            client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    setProfileHeader(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

    public void setProfileHeader(JSONObject response) {
        //getActivity().getSupportActionBar().setTitle("@" + user.getScreenName());
        user = User.fromJSON(response);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");
        ivProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfileImage);
        return;
    }
}
