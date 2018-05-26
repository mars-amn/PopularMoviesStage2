package nanodegree.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.GlideApp;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.models.TrailersResults;

/**
 * Created by AbdullahAtta on 3/8/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.trailerViewHolder> {
    private static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/";
    private static final String YOUTUBE_THUMBNAIL_QUALITY_URL = "/sddefault.jpg";
    private onTrailerClickListener mListener;
    private onTrailerLongClickListener mLongListener;
    private Context mContext;
    private List<TrailersResults> mTrailersResultList;

    public TrailersAdapter(Context context, List<TrailersResults> trailers, onTrailerClickListener listener,
                           onTrailerLongClickListener longListener) {
        mContext = context;
        mTrailersResultList = trailers;
        mListener = listener;
        mLongListener = longListener;
    }

    public void updateTrailers(List<TrailersResults> body) {
        if (body != null) {
            mTrailersResultList = body;
            notifyDataSetChanged();
        }
    }

    @Override
    public trailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View trailersView = LayoutInflater.from(mContext).inflate(R.layout.trailers_list, parent, false);
        final trailerViewHolder holder = new trailerViewHolder(trailersView);
        holder.mTrailerThumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTrailerClick(mTrailersResultList.get(holder.getAdapterPosition()).getKey());
            }
        });

        holder.mTrailerThumbnailImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mLongListener.onTrailerLongClick(mTrailersResultList.get(holder.getAdapterPosition()).getKey());
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull trailerViewHolder holder, int position) {
        String trailerKey = mTrailersResultList.get(position).getKey();
        String trailerThumbnail = YOUTUBE_THUMBNAIL_URL + trailerKey +
                YOUTUBE_THUMBNAIL_QUALITY_URL;
        holder.bindView(trailerThumbnail);

    }

    @Override
    public int getItemCount() {
        return mTrailersResultList.size();
    }

    public interface onTrailerLongClickListener {
        void onTrailerLongClick(String key);
    }

    public interface onTrailerClickListener {
        void onTrailerClick(String key);
    }

    public class trailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.youtubeTrailerThumbnailImageView)
        ImageView mTrailerThumbnailImageView;

        public trailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(String trailerThumbnail) {
            GlideApp.with(mContext)
                    .load(trailerThumbnail)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mTrailerThumbnailImageView);
        }
    }
}
