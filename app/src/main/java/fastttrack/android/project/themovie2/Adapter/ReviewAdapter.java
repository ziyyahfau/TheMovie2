package fastttrack.android.project.themovie2.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fastttrack.android.project.themovie2.Model_Reviews.Result;
import fastttrack.android.project.themovie2.R;


/**
 * Created by Fauziyyah Faturahma on 7/29/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    List<Result> reviewList;

    public ReviewAdapter(List<Result> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_review, parent, false);
        ReviewAdapter.ReviewViewHolder viewHolder = new ReviewAdapter.ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {

        Result result = reviewList.get(position);
        holder.textReview.setText(result.getContent());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void setData(List<Result> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView textReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            textReview = (TextView) itemView.findViewById(R.id.textReview);
        }
    }
}
