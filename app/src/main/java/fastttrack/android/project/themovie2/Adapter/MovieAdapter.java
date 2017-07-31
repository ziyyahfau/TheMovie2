package fastttrack.android.project.themovie2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.GsonBuilder;

import java.util.List;

import fastttrack.android.project.themovie2.Model_Movie.Result;
import fastttrack.android.project.themovie2.R;
import fastttrack.android.project.themovie2.UI.DetailMovie;
import fastttrack.android.project.themovie2.UI.RetrofitInterface;

/**
 * Created by Fauziyyah Faturahma on 7/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    List<Result> movieList;

    public MovieAdapter(List<Result> movieList) {
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
      // View Holder
        Glide.with(holder.itemView.getContext())
                .load(RetrofitInterface.BASE_IMAGE + movieList.get(position).getPosterPath())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(holder.movieimage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setData(List<Result> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieimage;
        CardView moviecardview;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieimage = (ImageView) itemView.findViewById(R.id.imageMovie);
            moviecardview = (CardView) itemView.findViewById(R.id.cardMovie);

            moviecardview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Result data = movieList.get(position);
            final Context context = v.getContext();
            Intent intent = new Intent(context, DetailMovie.class);
            intent.putExtra("movie", new GsonBuilder().create().toJson(data));
            context.startActivity(intent);
        }
    }
}
