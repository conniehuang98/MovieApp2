package com.example.connie.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/**
 *
 * DetailActivity is a second activity that displays more information about a movie.
 *
 * @author  cbhuang2
 * @version 1.0
 * @since   2017-04-03
 */
public class DetailActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mAuthorTextView;
    private TextView mBodyTextView;
    private ImageView mImageView;
    private TextView mReleaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // get references to the views.
        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        mAuthorTextView = (TextView) findViewById(R.id.authorTextView);
        mReleaseTextView = (TextView) findViewById(R.id.releaseTextView);
        mBodyTextView = (TextView) findViewById(R.id.bodyTextView);
        mImageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        /* Code for receiving the Parcelable NewsArticle */
        MovieData movie = intent.getParcelableExtra(MovieAdapter.MOVIE);
        mTitleTextView.setText(movie.getTitle());
        mAuthorTextView.setText("Vote average: " + movie.getVoteAverage());
        mReleaseTextView.setText("Release date: " + movie.getReleaseDate());
        mBodyTextView.setText(movie.getOverview());
        Picasso.with(mImageView.getContext()).load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(mImageView);

    }
}