package com.example.android.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by tp293 on 10/9/2017.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";

    /*
     * NOTE: These values only effect responses from OpenWeatherMap, NOT from the fake weather
     * server. They are simply here to allow us to teach you how to build a URL if you were to use
     * a real API.If you want to connect your app to OpenWeatherMap's API, feel free to! However,
     * we are not going to show you how to do so in this course.
     */
    /* The format we want our API to return */
    private static final String sortByRatTrigger = "HIGHEST RATED";

    private static final String sortByPop = "popular";
    private static final String sortByRat = "top_rated";
    private static final String API_KEY = ""; //TODO: ENTER MOVIEDB APIKEY HERE

    //final static String SORTBY_PARAM = "sort_by";
    final static String APIKEY_PARAM = "api_key";

    public static URL buildUrl(String sortOrder) {

        String sortByType;

        if (sortOrder.equals(sortByRatTrigger)) {
            sortByType = sortByRat;
        } else {
            sortByType = sortByPop;
        }

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendEncodedPath(sortByType)
                .appendQueryParameter(APIKEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

}
