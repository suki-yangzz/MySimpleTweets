package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

public class TimelineActivity extends AppCompatActivity {
    private HomeTimelineFragment fragmentHomeTimeline;
    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        fragmentHomeTimeline = new HomeTimelineFragment();
        //client = TwitterApplication.getRestClient(); //singleton client
        //fragmentHomeTimeline.populateTimeline(0, 0);
        if (savedInstanceState == null) {
            fragmentHomeTimeline = (HomeTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.new_tweet:
                Intent i = new Intent(this,ComposeActivity.class);
                this.startActivity(i);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            Log.d("DEBUG: ", data.getSerializableExtra("tweet").toString());
            //JSONObject tweetObj = (JSONObject) data.getExtras().getSerializable("tweet");
            //Log.d("DEBUG: ", tweetObj.toString());
            Bundle bundle = data.getBundleExtra("tweet");
            JSONObject tweetObj = null;
            try {
                tweetObj = new JSONObject(bundle.getString("tweet"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Tweet tweet = Tweet.fromJSON(tweetObj);
            fragmentHomeTimeline.insertTweet(tweet);
        }
    }

    public void composeTweet(MenuItem mi){
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

}
