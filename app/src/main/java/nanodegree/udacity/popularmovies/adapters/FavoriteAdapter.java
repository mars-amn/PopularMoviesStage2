package nanodegree.udacity.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.database.MoviesContract;
import nanodegree.udacity.popularmovies.databinding.SortingListBinding;

/**
 * Created by AbdullahAtta on 3/12/2018.
 */

// the adapter used in Favorite Movies approach.
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{

    private final Cursor mCursor;
    private final Context mContext;
    private final onFavoriteMovieClick mListener;
    private final onFavoriteMovieLongClick mLongListener;


    public FavoriteAdapter(Context context, Cursor cursor,
                           onFavoriteMovieClick listener,
                           onFavoriteMovieLongClick longListener){
        mCursor = cursor;
        mContext = context;
        mListener = listener;
        mLongListener = longListener;
    }
    public interface onFavoriteMovieClick {
        void onFavoriteMovieClickListener(int Id);
    }

    public interface onFavoriteMovieLongClick{
        void onFavoriteMovieLongClickListener(int Id); // to delete the movie from the list
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        SortingListBinding binding = SortingListBinding.inflate(inflater,parent,false);

        final FavoriteViewHolder holder = new FavoriteViewHolder(binding);
        binding.posterMovieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFavoriteMovieClickListener((int)holder.itemView.getTag());
            }
        });
        binding.posterMovieImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mLongListener.onFavoriteMovieLongClickListener((int)holder.itemView.getTag());
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {

    if (mCursor.moveToPosition(position)){

        if (mCursor.isClosed()) return;

        int id = mCursor.getInt(mCursor.getColumnIndex(MoviesContract.MoviesEntry.ID_COLUMN));
        holder.itemView.setTag(id);

        String POSTER_SIZE = "w780";
        String posterPath = BuildConfig.POSTER_BASE_URL + POSTER_SIZE +
                mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.POSTER_COLUMN));

        holder.bindView(posterPath);


    }

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{

        final SortingListBinding sortingListBinding;
        public FavoriteViewHolder(SortingListBinding binding) {
            super(binding.getRoot());
            sortingListBinding = binding;
        }

        public void bindView(String posterImage){
            Picasso.with(mContext).load(posterImage)
                    .into(sortingListBinding.posterMovieImageView);

            sortingListBinding.executePendingBindings();
        }
    }
}
