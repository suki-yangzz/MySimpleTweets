package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet>tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    private String getDate(String time) {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date mDate = null;
        try {
            mDate = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeInMilliseconds = mDate.getTime();
        String timeDiff = (String) DateUtils.getRelativeTimeSpanString(timeInMilliseconds, now, 0);
        return timeDiff;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTimestamp.setText(getDate(tweet.getCreatedAt()));
        return convertView;
    }
}
