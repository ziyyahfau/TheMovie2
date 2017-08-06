package fastttrack.android.project.themovie2.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fastttrack.android.project.themovie2.Model_Trailer.Result;
import fastttrack.android.project.themovie2.R;

/**
 * Created by Fauziyyah Faturahma on 7/29/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {


    List<Result> trailerList;

    public TrailerAdapter(List<Result> trailerList) {
        this.trailerList = trailerList;
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_trailer, parent, false);
        TrailerAdapter.TrailerViewHolder viewHolder = new TrailerAdapter.TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {

        final Result result = trailerList.get(position);
        holder.imageView.setVisibility(View.VISIBLE);
        holder.buttonTrailer.setVisibility(View.VISIBLE);

        holder.buttonTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoKey = result.getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoKey));
                (view.getContext()).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void setData(List<Result> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        Button buttonTrailer;
        ImageView imageView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            buttonTrailer = (Button) itemView.findViewById(R.id.trailerButton);
            imageView = (ImageView)itemView.findViewById(R.id.imageViewTrailer);
        }
    }

}
