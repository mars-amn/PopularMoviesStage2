package nanodegree.udacity.popularmovies.fragments;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

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
    private static MoviesResults mMoviesResponse;
    @BindView(R.id.movieRecyclerView)
    RecyclerView mMoviesRecyclerView;
    Context mContext;
    RecyclerView.LayoutManager mMoviesLayoutManager;
    MoviesAdapter mAdapter;
    private SkeletonScreen mSkeletonScreen;

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
            deployMovies(mMoviesResponse);
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
                                deployMovies(mMoviesResponse);
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

    private void deployMovies(MoviesResults mMoviesResponse) {
        mAdapter.updatePosters(mMoviesResponse.getMovieDetails());
        mSkeletonScreen.hide();
        LayoutAnimationController slideUp = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_slide_up);
        mMoviesRecyclerView.setLayoutAnimation(slideUp);
    }

    private void setupViews() {
        mMoviesLayoutManager = new GridLayoutManager(mContext, 2);
        mMoviesRecyclerView.setLayoutManager(mMoviesLayoutManager);
        mAdapter = new MoviesAdapter(mContext, new ArrayList<MoviesResponse>(0), this);
        mMoviesRecyclerView.setAdapter(mAdapter);
        mSkeletonScreen = Skeleton.bind(mMoviesRecyclerView)
                .adapter(mAdapter)
                .load(R.layout.layout_default_item_skeleton)
                .duration(1500)
                .show();
    }

    @Override
    public void onMovieClickListener(View view, MoviesResponse movie) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            makePosterTransition(view, movie);
        } else {
            startMovieDetails(movie);
        }

    }

    private void startMovieDetails(MoviesResponse movie) {
        Intent selectedMovieIntent = new Intent(mContext, DetailsActivity.class);
        selectedMovieIntent.putExtra(PopularMoviesFragment.MOVIE_INTENT_KEY, movie);
        startActivity(selectedMovieIntent);
    }

    @SuppressLint("NewApi")
    private void makePosterTransition(View view, MoviesResponse movie) {
        ImageView posterImage = (ImageView) view;
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                posterImage,
                posterImage.getTransitionName()).toBundle();
        Intent selectedMovieIntent = new Intent(mContext, DetailsActivity.class);
        selectedMovieIntent.putExtra(PopularMoviesFragment.MOVIE_INTENT_KEY, movie);
        startActivity(selectedMovieIntent, bundle);
    }
}
