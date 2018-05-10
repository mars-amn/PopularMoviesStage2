package nanodegree.udacity.popularmovies.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.GlideApp;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.adapters.ReviewsAdapter;
import nanodegree.udacity.popularmovies.adapters.TrailersAdapter;
import nanodegree.udacity.popularmovies.database.MoviesContract;
import nanodegree.udacity.popularmovies.models.MovieImages;
import nanodegree.udacity.popularmovies.models.MoviesResponse;
import nanodegree.udacity.popularmovies.models.MoviesReviews;
import nanodegree.udacity.popularmovies.models.MoviesTrailers;
import nanodegree.udacity.popularmovies.models.Poster;
import nanodegree.udacity.popularmovies.models.ReviewsResults;
import nanodegree.udacity.popularmovies.models.TrailersResults;
import nanodegree.udacity.popularmovies.rest.MoviesAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailsFragment extends Fragment implements TrailersAdapter.onTrailerClickListener, TrailersAdapter.onTrailerLongClickListener{
    private static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private static final String MOVIE_KEY = "nanodegree.udacity.popularmovies.fragments.movie";
    @BindView(R.id.addOrRemoveFab)
    FloatingActionButton mAddOrRemoveFab;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapseToolbar;
    @BindView(R.id.toolbarDetailActivity)
    Toolbar mToolbar;
    @BindView(R.id.posterImage)
    ImageView mPosterImage;
    @BindView(R.id.overviewValueTextView)
    TextView mOverviewTextView;
    @BindView(R.id.releaseDateLabelTextView)
    TextView mReleaseDateTextView;
    @BindView(R.id.rateChart)
    FitChart mRateChartFitChart;
    @BindView(R.id.averageVoteLabelTextView)
    TextView mAverageVoteTextView;
    @BindView(R.id.recyclerViewReviews)
    RecyclerView mReviewsRecyclerView;
    @BindView(R.id.recyclerViewTrailers)
    RecyclerView mTrailersRecyclerView;
    private Context mContext;
    private MoviesResponse mMovie;
    private List<Poster> mMoviePosters;
    private Animation mZoomInAnimation;
    private Animation mFadeOutAnimation;
    private ReviewsAdapter mReviewsAdapter;
    private TrailersAdapter mTrailersAdapter;
    public MovieDetailsFragment() {
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            return !activity.isDestroyed() && !activity.isFinishing();
        }
        return true;
    }

    public void setMMovie(MoviesResponse movieId) {
        this.mMovie = movieId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View movieDetailsView = inflater.inflate(R.layout.movie_details_fragment, container, false);
        ButterKnife.bind(this, movieDetailsView);
        mContext = getContext();
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(MOVIE_KEY);
        }
        initDetailsBars();
        setupFavoriteFab();
        deployMovieDetails();
        initRecyclerViews();
        loadMoviePosters();
        loadMovieReviews();
        loadMovieTrailers();
        return movieDetailsView;
    }

    private void loadMovieTrailers() {
        MoviesAPIUtils.getRESTMovies().getMovieTrailers(mMovie.getId(),BuildConfig.TMDB_API_KEY)
                .enqueue(new Callback<MoviesTrailers>() {
                    @Override
                    public void onResponse(Call<MoviesTrailers> call, Response<MoviesTrailers> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                deployMovieTrailers(response.body());
                            }
                        }else {
                            Log.d(TAG,"trailer response code = " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesTrailers> call, Throwable t) {

                    }
                });
    }

    private void deployMovieTrailers(MoviesTrailers body) {
        mTrailersAdapter.updateTrailers(body);
        LayoutAnimationController slideToLeft = AnimationUtils.loadLayoutAnimation(mContext,R.anim.layout_animation_slide_to_left);
        mTrailersRecyclerView.setLayoutAnimation(slideToLeft);
    }

    private void initRecyclerViews() {
        mReviewsAdapter = new ReviewsAdapter(mContext, new ArrayList<ReviewsResults>(0));
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);
        mTrailersAdapter = new TrailersAdapter(mContext,new ArrayList<TrailersResults>(0),
                this,this);
        mTrailersRecyclerView.setAdapter(mTrailersAdapter);
    }

    private void loadMovieReviews() {
        MoviesAPIUtils.getRESTMovies().getMovieReviews(mMovie.getId(), BuildConfig.TMDB_API_KEY)
                .enqueue(new Callback<MoviesReviews>() {
                    @Override
                    public void onResponse(Call<MoviesReviews> call, Response<MoviesReviews> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                deployMovieReviews(response.body());
                            }
                        } else {
                            Log.d(TAG, "response code = " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesReviews> call, Throwable t) {

                    }
                });
    }

    private void deployMovieReviews(MoviesReviews mMovieReviewResponse) {
        mReviewsAdapter.updateReviews(mMovieReviewResponse.getResults());
        LayoutAnimationController slideUp = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_slide_up);
        mReviewsRecyclerView.setLayoutAnimation(slideUp);
    }

    private void setupFavoriteFab() {
        if (isInDatabase(mMovie.getId())) {
            mAddOrRemoveFab.setImageResource(R.drawable.ic_full_star);
        } else {
            mAddOrRemoveFab.setImageResource(R.drawable.ic_star_border);
        }
    }

    @OnClick(R.id.addOrRemoveFab)
    public void addOrRemoveToDatabase() {
        String movieId = Integer.toString(mMovie.getId());
        if (!isInDatabase(mMovie.getId())) {
            insetIntoDatabase();
        } else {
            deleteOfDatabase(movieId);
        }
    }

    private void deleteOfDatabase(String movieId) {
        Uri MOVIE_WITH_ID = MoviesContract.MoviesEntry.CONTENT_URI.buildUpon()
                .appendEncodedPath(movieId)
                .build();
        mContext.getContentResolver().delete(MOVIE_WITH_ID, null, null);
        mAddOrRemoveFab.setImageResource(R.drawable.ic_star_border);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_KEY, mMovie);
    }

    private void insetIntoDatabase() {
        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.MoviesEntry.ID_COLUMN, mMovie.getId());
        cv.put(MoviesContract.MoviesEntry.TITLE_COLUMN, mMovie.getTitle());
        cv.put(MoviesContract.MoviesEntry.RELEASE_DATE_COLUMN, mMovie.getReleaseDate());
        cv.put(MoviesContract.MoviesEntry.POSTER_COLUMN, mMovie.getPosterPath());
        cv.put(MoviesContract.MoviesEntry.OVERVIEW_COLUMN, mMovie.getOverview());
        cv.put(MoviesContract.MoviesEntry.AVERAGE_COLUMN, mMovie.getVoteAverage());
        mContext.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, cv);
        mAddOrRemoveFab.setImageResource(R.drawable.ic_full_star);
    }


    private void deployMovieDetails() {
        mCollapseToolbar.setTitle(mMovie.getTitle());
        mOverviewTextView.setText(mMovie.getOverview());
        mReleaseDateTextView.setText(mMovie.getReleaseDate());
        mAverageVoteTextView.setText(String.valueOf(mMovie.getVoteAverage()));
        double averageVote = mMovie.getVoteAverage();
        setFitChartValue((float) averageVote);
    }

    private void setFitChartValue(float averageVote) {
        mRateChartFitChart.setMaxValue(10f);
        mRateChartFitChart.setValue(averageVote);
    }

    private void loadMoviePosters() {
        int movieId = mMovie.getId();
        MoviesAPIUtils.getRESTMovies().getMovieImages(movieId, BuildConfig.TMDB_API_KEY)
                .enqueue(new Callback<MovieImages>() {
                    @Override
                    public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mMoviePosters = response.body().getPosters();
                                runMoviePostersAnimation(mMoviePosters);
                            }
                            Log.d(TAG, "response code = " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieImages> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    private void runMoviePostersAnimation(final List<Poster> moviePosters) {
        if (moviePosters.size() <= 0) {
            return;
        }

        mFadeOutAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int counter = 1;

            @Override
            public void run() {
                String posterUrl = BuildConfig.POSTER_BASE_URL + "t/p/w300" +
                        moviePosters.get(counter).getFilePath();
                if (isValidContextForGlide(mContext)) {
                    GlideApp.with(mContext)
                            .load(posterUrl)
                            //.placeholder(R.mipmap.ic_launcher)
                            //.error(R.mipmap.ic_launcher)
                            .into(mPosterImage);

                    mPosterImage.startAnimation(mZoomInAnimation);
                }
                counter++;
                if (counter > moviePosters.size() - 1) {
                    counter = 0;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPosterImage.startAnimation(mFadeOutAnimation);
                    }
                }, 1500);
                handler.postDelayed(this, 2500);
            }
        };
        handler.postDelayed(runnable, 2500);
    }


    private void initDetailsBars() {
        mCollapseToolbar.setExpandedTitleColor(getResources().
                getColor(android.R.color.transparent));
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        String moviePosterUrl = BuildConfig.POSTER_BASE_URL + "t/p/w780" + mMovie.getPosterPath();

        GlideApp.with(mContext)
                .load(moviePosterUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(mPosterImage);
        mZoomInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.zoom_in);
        mPosterImage.startAnimation(mZoomInAnimation);

    }

    private boolean isInDatabase(int id) {
        ContentResolver contentResolver = mContext.getContentResolver();
        String idQueried = Integer.toString(id);
        Uri MOVIE_WITH_ID = MoviesContract.MoviesEntry.CONTENT_URI.buildUpon()
                .appendEncodedPath(idQueried)
                .build();

        Cursor cursor = contentResolver.query(MOVIE_WITH_ID,
                null,
                null,
                null,
                null);

        return cursor.getCount() > 0;
    }

    @Override
    public void onTrailerLongClick(String key) {

    }

    @Override
    public void onTrailerClick(String key) {

    }
}
