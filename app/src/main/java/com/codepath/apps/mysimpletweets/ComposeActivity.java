package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.mysimpletweets.fragments.ComposeTweetFragment;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    private ComposeTweetFragment composeTweetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        composeTweetFragment = new ComposeTweetFragment();
        if (savedInstanceState == null) {
            composeTweetFragment = (ComposeTweetFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_compose_tweet);
        }
        client = TwitterApplication.getRestClient(); //singleton client
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_compose, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendTweet(View v) {
        EditText etTweet = (EditText) findViewById(R.id.etTweet);
        String tweetStr = etTweet.getText().toString();
        composeTweetFragment.postTweet(tweetStr);
    }
}
