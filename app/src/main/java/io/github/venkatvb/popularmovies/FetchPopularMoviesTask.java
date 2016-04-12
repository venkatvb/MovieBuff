package io.github.venkatvb.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by venkatvb on 26/3/16.
 */
public class FetchPopularMoviesTask extends AsyncTask<MoviesAdapter, Void, String[]> {

    private final String LOG_TAG = FetchPopularMoviesTask.class.getSimpleName();
    MoviesAdapter moviesAdapter;

    private String[] getMoviesDataFromJson(String moviesJsonString) throws JSONException{

        final String RESULTS = "results";
        final String MOVIE_NAME = "title";

        JSONObject movieJson = new JSONObject(moviesJsonString);
        JSONArray movieList = movieJson.getJSONArray(RESULTS);

        String movies[] = new String[movieList.length()];

        for(int i=0; i<movieList.length(); i++ ) {
            JSONObject currnetMovie = movieList.getJSONObject(i);
            movies[i] = currnetMovie.getString(MOVIE_NAME);
        }

        return movies;
    }

    @Override
    protected String[] doInBackground(MoviesAdapter... params) {

        if (params.length == 0) {
            return null;
        }

        moviesAdapter = params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String popularMoviesString = null;
        try {
            final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
            final String APIKEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(APIKEY_PARAM, Configuration.THE_MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Configuration.debug(LOG_TAG, url.toString());

            // creating a request to the movie db and opening the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Reading the input stream into a string
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if(buffer.length() == 0) {
                return null;
            }
            popularMoviesString = buffer.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error fetching movies", e);
            return null;
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error closing reader", e);
                }
            }
        }
        try {
            return getMoviesDataFromJson(popularMoviesString);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null) {
            moviesAdapter.clearAndAddArray(result);
        }
    }
}
