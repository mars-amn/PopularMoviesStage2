package nanodegree.udacity.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.GlideApp;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.database.MoviesContract;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final Cursor mCursor;
    private final Context mContext;
    private final onFavoriteMovieClick mListener;
    private final onFavoriteMovieLongClick mLongListener;


    public FavoriteAdapter(Context context, Cursor cursor,
                           onFavoriteMovieClick listener,
                           onFavoriteMovieLongClick longListener) {
        mCursor = cursor;
        mContext = context;
        mListener = listener;
        mLongListener = longListener;
    }

    public interface onFavoriteMovieClick {
        void onFavoriteMovieClickListener(int Id);
    }

    public interface onFavoriteMovieLongClick {
        void onFavoriteMovieLongClickListener(int Id); // to delete the movie from the list
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View sortingListView = LayoutInflater.from(mContext).inflate(R.layout.sorting_list, parent, false);

        final FavoriteViewHolder holder = new FavoriteViewHolder(sortingListView);
        holder.mPosterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFavoriteMovieClickListener((int) holder.itemView.getTag());
            }
        });
        holder.mPosterImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mLongListener.onFavoriteMovieLongClickListener((int) holder.itemView.getTag());
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            if (mCursor.isClosed()) return;
            int id = mCursor.getInt(mCursor.getColumnIndex(MoviesContract.MoviesEntry.ID_COLUMN));
            holder.itemView.setTag(id);

            String POSTER_SIZE = "t/p/w780";
            String posterPath = BuildConfig.POSTER_BASE_URL + POSTER_SIZE +
                    mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.POSTER_COLUMN));

            holder.bindView(posterPath);


        }

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.posterMovieImageView)
        ImageView mPosterImage;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(String posterImage) {
            GlideApp.with(mContext)
                    .load(posterImage)
                    .into(mPosterImage);
        }
    }
}
