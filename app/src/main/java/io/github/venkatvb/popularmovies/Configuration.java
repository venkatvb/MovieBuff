package io.github.venkatvb.popularmovies;

import android.util.Log;

/**
 * Created by venkatvb on 26/3/16.
 */
public class Configuration {
    public static final String THE_MOVIE_DB_API_KEY = "1a965ade66d50856d3907ee25856058b";
    public static final Boolean DEBUG = true;

    public static void debug(String tag, String message) {
        if(DEBUG) {
            Log.d(tag, message);
        }
    }
}
