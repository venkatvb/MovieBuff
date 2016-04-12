package io.github.venkatvb.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by venkatvb on 26/3/16.
 */
public class MoviesAdapter extends BaseAdapter {

	private String MOVIE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=1a965ade66d50856d3907ee25856058b";
    private Context mContext;
    private String[] movies = {
            "Raja Rani",
            "Motta Boss",
            "Star Wars",
            "Super Man",
            "Bat Man",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen",
            "twenty"
    };

    public void clearAndAddArray(String[] result) {
        movies = result;
        notifyDataSetChanged();
    }

    public MoviesAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView t;
        if(convertView == null) {
            t = new TextView(mContext);
            t.setLayoutParams(new GridView.LayoutParams(85, 85));
            t.setPadding(8, 8, 8, 8);
        } else {
            t = (TextView) convertView;
        }
        t.setText(movies[position]);
        return t;
    }
}
