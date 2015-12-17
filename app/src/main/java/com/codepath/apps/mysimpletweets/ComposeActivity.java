package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    private EditText etTweet;
    private Button btnCancel;
    private Button btnNew;
    private ImageView ivProfileImg;
    private TextView tvScreenName;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ivProfileImg = (ImageView) findViewById(R.id.ivProfileImg);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvName = (TextView) findViewById(R.id.tvName);
        etTweet = (EditText) findViewById(R.id.etTweet);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnNew = (Button) findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetBody = etTweet.getText().toString();
                Intent data = new Intent();
                data.putExtra("tweet", tweetBody);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        client = TwitterApplication.getRestClient(); //singleton client
        getMyProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_compose, menu);
        return super.onCreateOptionsMenu(menu);
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
}
