package nanodegree.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.models.ReviewsResults;

/**
 * Created by AbdullahAtta on 3/11/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.reviewsViewHolder> {
    private List<ReviewsResults> mReviewsResult;
    private Context mContext;

    public ReviewsAdapter(Context context, List<ReviewsResults> reviewsList) {
        mContext = context;
        mReviewsResult = reviewsList;
    }

    @Override
    public reviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reviewsView = LayoutInflater.from(mContext).inflate(R.layout.list_item_reviews, parent, false);
        return new reviewsViewHolder(reviewsView);
    }

    @Override
    public void onBindViewHolder(reviewsViewHolder holder, int position) {
        String reviewerName = mReviewsResult.get(position).getAuthor();
        String reviewContent = mReviewsResult.get(position).getContent();
        holder.mReviewerNameTextView.setText(reviewerName);
        holder.mReviewTextView.setText(reviewContent);
    }

    @Override
    public int getItemCount() {
        return mReviewsResult.size();
    }

    public void updateReviews(List<ReviewsResults> results) {
        if (results != null) {
            mReviewsResult = results;
            notifyDataSetChanged();
        }
    }

    public class reviewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reviewerNameTextView)
        TextView mReviewerNameTextView;
        @BindView(R.id.reviewContentTextView)
        TextView mReviewTextView;

        public reviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
