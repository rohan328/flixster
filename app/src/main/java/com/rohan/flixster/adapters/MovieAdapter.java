package com.rohan.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.rohan.flixster.R;
import com.rohan.flixster.models.Movie;

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

        if(viewType==1){
            View movieViewPopular = LayoutInflater.from(context).inflate(R.layout.item_movie_popular,parent,false);
            return new ViewHolderPopular(movieViewPopular);
        }
        else{
            View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
            return new ViewHolder(movieView);
        }

    }

    //populates the view
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder" + position);
        Movie movie= movies.get(position);

        if(holder.getItemViewType()==1){
            ViewHolderPopular vh = (ViewHolderPopular)holder;
            vh.bind(movie);
        }
        else{
            ViewHolder vh = (ViewHolder)holder;
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
        Log.d("MovieAdapter","position: "+ position + "rating " + movies.get(position).getRating());
        if(movies.get(position).getRating()>7){
            return 1;
        }
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvOverview=itemView.findViewById(R.id.tvOverview);
            ivPoster=itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                imageURL=movie.getPosterPath();
            }
            else imageURL=movie.getBackdropPath();

            Glide.with(context).load(imageURL).into(ivPoster);
        }
    }

    public class ViewHolderPopular extends RecyclerView.ViewHolder{

        ImageView ivPosterPopular;

        public ViewHolderPopular(@NonNull View itemViewPopular){
            super(itemViewPopular);
            ivPosterPopular=itemView.findViewById(R.id.ivPosterPopular);

        }

        public void bind(Movie movie){
            String imageURL;
            imageURL=movie.getBackdropPath();

            Glide.with(context).load(imageURL).into(ivPosterPopular);
        }
    }
}
