package nanodegree.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.GlideApp;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.models.MoviesResponse;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieListVHolder> {

    private final Context mContext;
    private List<MoviesResponse> mMoviesList;
    private onMoviePosterClicked mPosterClickListener;

    public interface onMoviePosterClicked {
        void onMovieClickListener(MoviesResponse movie);
    }

    public MoviesAdapter(Context mContexts, List<MoviesResponse> moviesLists,
                         onMoviePosterClicked listener) {
        mPosterClickListener = listener;
        mContext = mContexts;
        mMoviesList = moviesLists;
    }

    @Override
    public MovieListVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View sortingListView = LayoutInflater.from(mContext).inflate(R.layout.sorting_list, parent, false);
        final MovieListVHolder holder = new MovieListVHolder(sortingListView);
        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosterClickListener.onMovieClickListener(mMoviesList.get(holder.getAdapterPosition()));
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(MovieListVHolder holder, int position) {
        // available sizes for the posters are "w320" , "w500" , "w780"
        String POSTER_SIZE = "t/p/w780";
        String posterPath = BuildConfig.POSTER_BASE_URL + POSTER_SIZE +
                mMoviesList.get(position).getPosterPath();

        holder.bindView(posterPath);
    }

    public void updatePosters(List<MoviesResponse> newList) {
        if (newList != null) {
            mMoviesList = newList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class MovieListVHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.posterMovieImageView)
        ImageView posterImage;

        public MovieListVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(String posterPath) {
            GlideApp.with(mContext)
                    .load(posterPath)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(posterImage);
        }
    }


}