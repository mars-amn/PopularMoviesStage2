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
import android.widget.LinearLayout;

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


public class PopularMoviesFragment extends Fragment implements MoviesAdapter.onMoviePosterClicked {
    public static final String MOVIE_INTENT_KEY = "nanodegree.udacity.popularmovies.fragments.movie_key";
    private static final String TAG = PopularMoviesFragment.class.getSimpleName();
    private static MoviesResults mMoviesResponse;
    @BindView(R.id.movieRecyclerView)
    RecyclerView mMoviesRecyclerView;
    @BindView(R.id.offlineState)
    LinearLayout offlineState;
    RecyclerView.LayoutManager mMoviesLayoutManager;
    MoviesAdapter mMoviesAdapter;
    Context mContext;

    public PopularMoviesFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainMoviesView = inflater.inflate(R.layout.main_movies_fragment, container, false);
        ButterKnife.bind(this, mainMoviesView);
        mContext = getContext();
        setUpViews();
        if (mMoviesResponse != null) {
            mMoviesAdapter.updatePosters(mMoviesResponse.getMovieDetails());
        } else {
            loadMovies();
        }
        return mainMoviesView;
    }

    private void setUpViews() {
        mMoviesAdapter = new MoviesAdapter(mContext, new ArrayList<MoviesResponse>(0), this);
        mMoviesLayoutManager = new GridLayoutManager(mContext, 2);
        mMoviesRecyclerView.setLayoutManager(mMoviesLayoutManager);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    private void loadMovies() {
        MoviesAPIUtils.getRESTMovies().getPopularMovies(BuildConfig.TMDB_API_KEY)
                .enqueue(new Callback<MoviesResults>() {
                    @Override
                    public void onResponse(Call<MoviesResults> call, Response<MoviesResults> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mMoviesResponse = response.body();
                                mMoviesAdapter.updatePosters(response.body().getMovieDetails());
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

    @Override
    public void onMovieClickListener(MoviesResponse movie) {
        Intent selectedMovieIntent = new Intent(mContext, DetailsActivity.class);
        selectedMovieIntent.putExtra(MOVIE_INTENT_KEY,movie);
        startActivity(selectedMovieIntent);
    }

}
