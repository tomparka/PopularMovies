package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    public RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private MovieAdapter myAdapter;
    private ProgressBar mProgressBar;
    private GridLayoutManager movieLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_posters);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        int postersPerRow = 3;
        movieLayoutManager = new GridLayoutManager(this, postersPerRow);
        mRecyclerView.setLayoutManager(movieLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        myAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(myAdapter);

        loadMovieData();
    }

    public void loadMovieData() {
        showMovieDataView();
        new MovieDataTask().execute("");
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessageView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String[] movieInfo) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieInfo);
        startActivity(intentToStartDetailActivity);
    }

    public class MovieDataTask extends AsyncTask<String, Void, String[][]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[][] doInBackground(String... sortBy) {
            if (sortBy.length == 0) {
                return null;
            }

            URL movieRequestUrl = NetworkUtils.buildUrl(sortBy[0]);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                String[][] simpleJsonMovieData = OpenMovieJsonUtils
                        .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[][] movieData) {
            super.onPostExecute(movieData);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                Log.d("ONPOSTEXECUTE CALLED", "movieData length = " + movieData.length);
                myAdapter.setMovieData(movieData);

            } else {
                showErrorMessageView();
                Log.d("onPostExecute", "errorMessageTriggered");
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_by_type) {
            if (item.getTitle().equals("MOST POPULAR")) {
                item.setTitle("HIGHEST RATED");
                new MovieDataTask().execute("HIGHEST RATED");
            } else {
                item.setTitle("MOST POPULAR");
                new MovieDataTask().execute("MOST POPULAR");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
