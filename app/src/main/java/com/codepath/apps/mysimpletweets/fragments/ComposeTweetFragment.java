package com.codepath.apps.mysimpletweets.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ComposeTweetFragment extends Fragment {
    private TwitterClient client;
    private EditText etTweet;
    private Button btnCancel;
    private Button btnNew;
    private ImageView ivProfileImg;
    private TextView tvScreenName;
    private TextView tvName;
    private JSONObject tweet;
    private static final int REQUEST_CODE = 10;

    public ComposeTweetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); //singleton client
        getMyProfile();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compose_tweet, container, false);
        ivProfileImg = (ImageView) v.findViewById(R.id.ivProfileImg);
        tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
        tvName = (TextView) v.findViewById(R.id.tvName);
        etTweet = (EditText) v.findViewById(R.id.etTweet);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnNew = (Button) v.findViewById(R.id.btnNew);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;
    }

    private void getMyProfile() {
        client.getCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("DEBUG", response.toString());
                try {
                    tvScreenName.setText('@' + response.getString("screen_name"));
                    tvName.setText(response.getString("name"));
                    ivProfileImg.setImageResource(0); //clear out imageview
                    Picasso.with(ivProfileImg.getContext()).load(response.getString("profile_image_url")).into(ivProfileImg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void postTweet(String tweetStr) {
        client.postTweet(tweetStr, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    Intent data = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("tweet", response.toString());
                    data.putExtras(bundle);
                    getActivity().setResult(Activity.RESULT_OK, data);
                    //setResult(Activity.RESULT_OK, data);
                    getActivity().finish();
                    //finish();
                } else {
                    Log.d("DEBUG", "Empty Response");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
