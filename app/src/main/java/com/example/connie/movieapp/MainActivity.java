package com.example.connie.movieapp;

import com.example.connie.movieapp.TheMovieDB.API_KEY;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * MovieApp2 is an app that displays a list of the top movies pulled from theMovieDB.org.
 *
 * @author  cbhuang2
 * @version 1.0
 * @since   2017-03-28
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<MovieData> listmovies = new ArrayList<>();
    private MovieAdapter movieAdapter;

    /**
     * This method renders the layout for the list of movies.
     * @param savedInstanceState the current state of the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView); //lets scroll
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // Initially, listmovies is an empty ArrayList.  We populate it with MovieAsyncTask.
        movieAdapter = new MovieAdapter(listmovies);
        mRecyclerView.setAdapter(movieAdapter);
        // Construct URL and request data...
        try {
            URL url = new URL(API_KEY.MOVIES_URL);
            // Fetch the movies on a background thread; it will populate listmovies.
            new MovieAsyncTask(this).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class (1) takes a URL, (2) makes an HTTP request, (3) parses the resulting JSON
     * into a MovieCollection, and (4) returns the array of movies.
     *
     * This class requires a Context in its constructor so that we can make Toasts.
     */
    public class MovieAsyncTask extends AsyncTask<URL, Void, MovieData[]> {
        Context context;
        public MovieAsyncTask(Context context) {
            this.context = context;
        }
        // This function is done on the background thread.
        @Override
        protected MovieData[] doInBackground(URL... params) {
            try {
                URL url = params[0];
                MovieCollection movieCollection = null;
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
                Gson gson = new Gson();
                movieCollection = gson.fromJson(inStreamReader, MovieCollection.class);
                return movieCollection.getResults();
            } catch (IOException e) {
                Log.d("MovieAsyncTask", "failed to get data from network", e);
                return null;
            }
        }
        // This code is run on the UI thread
        protected void onPostExecute(MovieData[] listofmovies) {
            // Pop up a Toast if we failed to get data.
            if (listofmovies == null) {
                Toast.makeText(context, "Failed to get network data", Toast.LENGTH_LONG).show();
                return;
            }
            // Empty the ArrayList of articles (listmovies) and fill it with the ones we loaded.
            // (In this code, the emptying isn't necessary, because this function is only called
            // once, but it is good practice.)
            listmovies.clear();
            for (MovieData movie : listofmovies) {
                Log.d("Movie", movie.getTitle());
                listmovies.add(movie);
            }
            // Poke movieAdapter to let it know that its data changed.
            movieAdapter.notifyDataSetChanged();
        }
    }
}
