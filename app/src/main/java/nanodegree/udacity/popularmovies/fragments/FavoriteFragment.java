package nanodegree.udacity.popularmovies.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.GlideApp;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.database.MoviesContract;

/**
 * Created by AbdullahAtta on 5/11/2018.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private int mMovieId;

    public int getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    @BindView(R.id.rateChart)
    FitChart mFavoriteRateChar;
    @BindView(R.id.posterFavoriteImageView)
    ImageView mFavoritePosterImageView;
    @BindView(R.id.overviewValueTextView)
    TextView mFavoriteOverviewTextView;
    @BindView(R.id.releaseDateLabelTextView)
    TextView mFavoriteReleaseDateTextView;
    @BindView(R.id.averageVoteLabelTextView)
    TextView mFavoriteAverageVoteTextView;
    @BindView(R.id.collapsingToolBarFavorite)
    CollapsingToolbarLayout mFavoriteCollapsingBarLayout;
    @BindView(R.id.toolbarFavorite)
    Toolbar mFavoriteToolbar;
    private Context mContext;

    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favoriteFragmentView = inflater.inflate(R.layout.favorite_fragment, container, false);
        ButterKnife.bind(this, favoriteFragmentView);
        mContext = getContext();
        initActivityBars();
        queryForMovie();
        return favoriteFragmentView;
    }

    private void queryForMovie() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    private void initActivityBars() {
        mFavoriteCollapsingBarLayout.setExpandedTitleColor(getResources().
                getColor(android.R.color.transparent));
        ((AppCompatActivity) getActivity()).setSupportActionBar(mFavoriteToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(mContext) {
            Cursor movieCursor = null;

            @Override
            protected void onStartLoading() {
                if (movieCursor != null) {
                    deliverResult(movieCursor);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                movieCursor = data;
                super.deliverResult(movieCursor);
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                Uri idUri = MoviesContract.MoviesEntry.CONTENT_URI.buildUpon()
                        .appendPath(Integer.toString(getmMovieId()))
                        .build();
                return mContext.getContentResolver().query(idUri,
                        null,
                        null,
                        null,
                        null);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToNext()) {
            deployFavoriteMovieDetails(data);
        }
    }

    private void deployFavoriteMovieDetails(Cursor data) {
        int titleIndex = data.getColumnIndex(MoviesContract.MoviesEntry.TITLE_COLUMN);
        int posterIndex = data.getColumnIndex(MoviesContract.MoviesEntry.POSTER_COLUMN);
        int overviewIndex = data.getColumnIndex(MoviesContract.MoviesEntry.OVERVIEW_COLUMN);
        int averageVoteIndex = data.getColumnIndex(MoviesContract.MoviesEntry.AVERAGE_COLUMN);
        int releaseDateIndex = data.getColumnIndex(MoviesContract.MoviesEntry.RELEASE_DATE_COLUMN);

        String title = data.getString(titleIndex);
        String poster = data.getString(posterIndex);
        String overview = data.getString(overviewIndex);
        double averageVote = data.getDouble(averageVoteIndex);
        String releaseDate = data.getString(releaseDateIndex);

        mFavoriteCollapsingBarLayout.setTitle(title);
        mFavoriteOverviewTextView.setText(overview);
        mFavoriteReleaseDateTextView.setText(releaseDate);

        String POSTER_SIZE = "t/p/w780";
        String posterPath = BuildConfig.POSTER_BASE_URL + POSTER_SIZE + poster;
        GlideApp.with(mContext)
                .load(posterPath)
                .placeholder(R.mipmap.ic_launcher)
                .into(mFavoritePosterImageView);
        mFavoriteRateChar.setMaxValue(10f);
        float averageVoteInFloat = (float) averageVote;
        mFavoriteRateChar.setValue(averageVoteInFloat);
        mFavoriteAverageVoteTextView.setText(String.valueOf(averageVoteInFloat));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
