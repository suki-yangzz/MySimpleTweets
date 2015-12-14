package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComposeActivity extends Activity {
    private Menu menu;
    private EditText etTweet;
    private Button btnCancel;
    private Button btnNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }
}
