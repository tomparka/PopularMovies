package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    String[] mMovieArray;

    ImageView mPosterImage;
    TextView mMovieTitle;
    TextView mMovieReleaseDate;
    TextView mMovieRating;
    TextView mMoviePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mPosterImage = (ImageView) findViewById(R.id.detail_poster);
        mMovieTitle = (TextView) findViewById(R.id.detail_title);
        mMovieReleaseDate = (TextView) findViewById(R.id.detail_release_date);
        mMovieRating = (TextView) findViewById(R.id.detail_rating);
        mMoviePlot = (TextView) findViewById(R.id.detail_plot);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieArray = intentThatStartedThisActivity.getStringArrayExtra(Intent.EXTRA_TEXT);

                mMovieTitle.setText("Title: " + mMovieArray[0]);
                mMovieReleaseDate.setText("Release Date: " +mMovieArray[1]);
                mMovieRating.setText("Rating: " + mMovieArray[2]);
                Picasso.with(this).load(mMovieArray[3]).into(mPosterImage);
                mMoviePlot.setText(mMovieArray[4]);
            }
        }
    }
}
