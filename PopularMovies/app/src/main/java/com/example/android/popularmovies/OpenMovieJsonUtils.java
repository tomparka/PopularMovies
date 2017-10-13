package com.example.android.popularmovies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by tp293 on 10/10/2017.
 */

public final class OpenMovieJsonUtils {
    /*
     * @param forecastJsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[][] getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        /* Movie info found in Array called "results" */
        final String BASEPOSTERURL = "http://image.tmdb.org/t/p/w185";

        final String OMJ_RESULTS = "results";

        //final String OWM_MESSAGE_CODE = "cod";

        /* String array to hold each movie's info String */
        String[][] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        /* Is there an error?
        if (movieJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid *
                    return null;
                default:
                    /* Server probably down *
                    return null;
            }
        } */

        JSONArray movieArray = movieJson.getJSONArray(OMJ_RESULTS);

        parsedMovieData = new String[movieArray.length()][5];

        for (int i = 0; i < movieArray.length(); i++) {

            String title;
            String releaseDate;
            String rating;
            String posterPath;
            String plot;

            /* Get the JSON object representing a movie */
            JSONObject movieInfo = movieArray.getJSONObject(i);

            title = movieInfo.getString("original_title");
            releaseDate = movieInfo.getString("release_date");
            rating = movieInfo.getString("vote_average");

            posterPath = movieInfo.getString("poster_path");
            String posterFullPath = BASEPOSTERURL + posterPath;

            plot = movieInfo.getString("overview");

            String[] movieInfoArray = {title, releaseDate, rating, posterFullPath, plot};
            parsedMovieData[i] = movieInfoArray;
        }

        return parsedMovieData;
    }

}
