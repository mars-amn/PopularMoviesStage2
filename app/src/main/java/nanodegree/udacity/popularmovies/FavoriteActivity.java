package nanodegree.udacity.popularmovies;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;



import nanodegree.udacity.popularmovies.Utils.NotificationUtils;
import nanodegree.udacity.popularmovies.database.MoviesContract;


public class FavoriteActivity extends AppCompatActivity implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final String BUNDLE_ID_KEY = "movie_id";
  //  private ActivityFavoriteBinding activityFavoriteBinding;
    private final Context mContext = this;
    private int theMovieId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ViewGroup parent = findViewById(R.id.coordinatorFavorite);
//        activityFavoriteBinding = DataBindingUtil.inflate(getLayoutInflater(),
//                R.layout.activity_favorite, parent, true);
//        activityFavoriteBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        initFavoriteBars();

        Intent iGotMovieId = getIntent();
        if (iGotMovieId.hasExtra(NotificationUtils.INTENT_EXTRA_KEY)) {
            theMovieId = iGotMovieId.getIntExtra(NotificationUtils.INTENT_EXTRA_KEY, -1);
            Bundle bundle = new Bundle();
            bundle.putInt(BUNDLE_ID_KEY, theMovieId);
            getSupportLoaderManager().initLoader(1, bundle, this);
        } else {
            errorUponLaunch();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFavoriteBars() {
//        activityFavoriteBinding.collapsingToolBarFavorite.setExpandedTitleColor(getResources().
//                getColor(android.R.color.transparent));
//
//        setSupportActionBar(activityFavoriteBinding.toolbarFavorite);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    // to remove the movie of the database, the method will finish the activity if the user wanted to remove the movie.
    public void removeMovie(View view) {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(mContext);
        aBuilder.setTitle(R.string.delete_movie_alert_dialog_title);
        aBuilder.setMessage(R.string.delete_movie_alert_dialog_message);
        aBuilder.setPositiveButton(R.string.delete_movie_alert_dialog_positive_button_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContentResolver contentResolver = getContentResolver();
                String idToDelete = Integer.toString(theMovieId);
                Uri MOVIE_WITH_ID = MoviesContract.MoviesEntry.CONTENT_URI.buildUpon()
                        .appendEncodedPath(idToDelete)
                        .build();

                contentResolver.delete(MOVIE_WITH_ID,null,null);

                finish();
            }
        });
        aBuilder.setNegativeButton(R.string.delete_movie_alert_dialog_negative_button_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = aBuilder.create();
        dialog.show();
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(mContext) {
            Cursor cursorMovie = null;

            @Override
            protected void onStartLoading() {
                if (cursorMovie != null) {
                    deliverResult(cursorMovie);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                cursorMovie = data;
                super.deliverResult(cursorMovie);
            }

            @Override
            public Cursor loadInBackground() {
                // query for movie details through the content provider.
                String idOfMovie = Integer.toString(bundle.getInt(BUNDLE_ID_KEY));
                Uri movieUri = MoviesContract.MoviesEntry.CONTENT_URI.buildUpon()
                        .appendPath(idOfMovie)
                        .build();

                return getContentResolver().query(movieUri,
                        null,
                        null,
                        null,
                        null);
            }
        };

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        if( cursor != null && cursor.moveToFirst() ){

            Animation fadingAnim = AnimationUtils.loadAnimation(mContext,R.anim.zoom_in);

            String posterPath = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.POSTER_COLUMN));
            String POSTER_SIZE = "w780";
//            GlideApp.with(mContext).load(BuildConfig.POSTER_BASE_URL + POSTER_SIZE + posterPath)
//                    .into(activityFavoriteBinding.posterFavorite);
//
//            activityFavoriteBinding.posterFavorite.startAnimation(fadingAnim);
//
//            String movieTitle = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.TITLE_COLUMN));
//
//            activityFavoriteBinding.collapsingToolBarFavorite.setTitle(movieTitle);

            double voteAverage = cursor.getDouble(cursor.getColumnIndex(MoviesContract.MoviesEntry.AVERAGE_COLUMN));
            float averageVoteInDouble = (float) voteAverage ;
//            activityFavoriteBinding.includedDetailsLayoutInFavorite.rateChart.setMinValue(0f);
//            activityFavoriteBinding.includedDetailsLayoutInFavorite.rateChart.setMaxValue(10f);
//            activityFavoriteBinding.includedDetailsLayoutInFavorite.rateChart.setValue(averageVoteInDouble);
//            activityFavoriteBinding.includedDetailsLayoutInFavorite.averageVoteLabelTextView.setText(
//                    String.valueOf(averageVoteInDouble));
//
//            String overview = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.OVERVIEW_COLUMN));
//            activityFavoriteBinding.includedDetailsLayoutInFavorite.overviewValueTextView.setText(overview);
//
//            String releaseDate = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.RELEASE_DATE_COLUMN));
//            activityFavoriteBinding.includedDetailsLayoutInFavorite.releaseDateLabelTextView.setText(releaseDate);

        }else {
            errorUponLaunch();
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    private void errorUponLaunch() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
        finish();
    }
}

