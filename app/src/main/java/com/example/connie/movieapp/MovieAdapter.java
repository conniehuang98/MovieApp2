package com.example.connie.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.id.message;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 *
 * MovieAdapter.java handles the view for the app.
 *
 * @author  cbhuang2
 * @version 1.0
 * @since   2017-03-28
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String MOVIE = "MOVIE";

    ArrayList<MovieData> movies;
    public MovieAdapter(ArrayList<MovieData> movies) {
        this.movies=movies;
    }
    /**
     * This function is called only enough times to cover the screen with views.  After
     * that point, it recycles the views when scrolling is done.
     * @param parent the intended parent object (our RecyclerView)
     * @param viewType unused in our function (enables having different kinds of views in the same RecyclerView)
     * @return the new ViewHolder we allocate
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // a LayoutInflater turns a layout XML resource into a View object.
        final View movieListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movielayout, parent, false);
        return new ViewHolder(movieListItem);
    }
    /**
     * This function gets called each time a ViewHolder needs to hold data for a different
     * position in the list.  We don't need to create any views (because we're recycling), but
     * we do need to update the contents in the views.
     * @param holder the ViewHolder that knows about the Views we need to update
     * @param position the index into the array of movies
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieData movie = movies.get(position);
        holder.titleView.setText(movie.getTitle());
        holder.authorView.setText("Vote average: " + movie.getVoteAverage());
        Picasso.with(holder.imageView.getContext())
                .load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(holder.imageView);
        // Attach a click listener that launches a new Activity
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for launching an Explicit Intent to go to another Activity in
                // the same App.
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                /* Pass data as a Parcelable Plain-Old Java Object (POJO) */
                intent.putExtra(MOVIE, movie);
                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * RecyclerView wants to know how many list items there are, so it knows when it gets to the
     * end of the list and should stop scrolling.
     * @return the number of movies in the array.
     */
    @Override
    public int getItemCount() {
        return movies.size();
    }
    /**
     * A ViewHolder class for our adapter that 'caches' the references to the
     * subviews, so we don't have to look them up each time.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView authorView;
        public ImageView imageView;
        public View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            titleView = (TextView) itemView.findViewById(R.id.titlemainTextView);
            authorView = (TextView) itemView.findViewById(R.id.authormainTextView);
            imageView = (ImageView) itemView.findViewById(R.id.imagemainView);
        }
    }
}
