package com.rohan.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.telecom.Call;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.rohan.flixster.DetailActivity;
import com.rohan.flixster.R;
import com.rohan.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //inflates the view
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.d("MovieAdapter","onCreateViewHolder");

        if (viewType == 1) {
            View movieViewPopular = LayoutInflater.from(context).inflate(R.layout.item_movie_popular, parent, false);
            return new ViewHolderPopular(movieViewPopular);
        } else {
            View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        }

    }

    //populates the view
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Log.d("MovieAdapter", "onBindViewHolder" + position);
        Movie movie = movies.get(position);

        if (holder.getItemViewType() == 1) {
            ViewHolderPopular vh = (ViewHolderPopular) holder;
            vh.bind(movie);
        } else {
            ViewHolder vh = (ViewHolder) holder;
            vh.bind(movie);
        }
    }

    //total count of items
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //returns the type of view to choose Popular or non popular
    @Override
    public int getItemViewType(int position) {
        //Log.d("MovieAdapter", "position: " + position + "rating " + movies.get(position).getRating());
        if (movies.get(position).getRating() > 7) {
            return 1;
        } else return 0;
    }

    //regular movie view holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout movieContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            movieContainer = itemView.findViewById(R.id.movieContainer);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                imageURL = movie.getPosterPath();

            } else{
                imageURL = movie.getBackdropPath();
            }

            int w = (int)(342 * context.getResources().getDisplayMetrics().density);
            int h = (int)(192 * context.getResources().getDisplayMetrics().density);

            Glide.with(context)
                    .load(imageURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .override(w,h)
                    .into(ivPoster);

            movieContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));

                    Pair<View, String> p1 = Pair.create((View)tvTitle, "title");
                    Pair<View, String> p2 = Pair.create((View)tvOverview, "overview");
                    Pair<View, String> p3 = Pair.create((View)ivPoster, "image");

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, p1,p2,p3);

                    context.startActivity(i,options.toBundle());
                }
            });
        }
    }

    //popular movie view holder
    public class ViewHolderPopular extends RecyclerView.ViewHolder {

        ImageView ivPosterPopular;
        RelativeLayout movieContainer;

        public ViewHolderPopular(@NonNull View itemViewPopular) {
            super(itemViewPopular);
            ivPosterPopular = itemViewPopular.findViewById(R.id.ivPosterPopular);
            movieContainer = itemViewPopular.findViewById(R.id.movieContainer);
        }

        public void bind(final Movie movie) {
            String imageURL;
            imageURL = movie.getBackdropPath();
            int width = (int)context.getResources().getDisplayMetrics().widthPixels;;

            Glide.with(context)
                    .load(imageURL)
                    .override(width)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivPosterPopular);

            movieContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));

                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity)context, (View)ivPosterPopular, "image");


                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
