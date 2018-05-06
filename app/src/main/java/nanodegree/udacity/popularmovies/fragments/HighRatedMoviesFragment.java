package nanodegree.udacity.popularmovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.DetailsActivity;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.adapters.MoviesAdapter;
import nanodegree.udacity.popularmovies.models.MoviesResponse;
import nanodegree.udacity.popularmovies.models.MoviesResults;
import nanodegree.udacity.popularmovies.rest.MoviesAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HighRatedMoviesFragment extends Fragment implements MoviesAdapter.onMoviePosterClicked {
    private static final String TAG = HighRatedMoviesFragment.class.getSimpleName();

    @BindView(R.id.movieRecyclerView)
    RecyclerView mMoviesRecyclerView;
    private static MoviesResults mMoviesResponse;
    Context mContext;
    RecyclerView.LayoutManager mMoviesLayoutManager;
    MoviesAdapter mAdapter;

    public HighRatedMoviesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View highRatedMoviesView = inflater.inflate(R.layout.main_movies_fragment, container, false);
        ButterKnife.bind(this, highRatedMoviesView);
        mContext = getContext();
        setupViews();

        if (mMoviesResponse != null) {
            mAdapter.updatePosters(mMoviesResponse.getMovieDetails());
        } else {
            loadHighRatedMovies();
        }
        return highRatedMoviesView;
    }

    private void loadHighRatedMovies() {
        MoviesAPIUtils.getRESTMovies().getHighRatedMovies(BuildConfig.TMDB_API_KEY)
                .enqueue(new Callback<MoviesResults>() {
                    @Override
                    public void onResponse(Call<MoviesResults> call, Response<MoviesResults> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mMoviesResponse = response.body();
                                mAdapter.updatePosters(mMoviesResponse.getMovieDetails());
                            }
                        } else {
                            Log.d(TAG, "response code = " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResults> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    private void setupViews() {
        mMoviesLayoutManager = new GridLayoutManager(mContext, 2);
        mAdapter = new MoviesAdapter(mContext, new ArrayList<MoviesResponse>(0), this);
        mMoviesRecyclerView.setLayoutManager(mMoviesLayoutManager);
        mMoviesRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onMovieClickListener(MoviesResponse movie) {
        Intent selectedMovieIntent = new Intent(mContext, DetailsActivity.class);
        selectedMovieIntent.putExtra(PopularMoviesFragment.MOVIE_INTENT_KEY,movie);
        startActivity(selectedMovieIntent);
    }

}
