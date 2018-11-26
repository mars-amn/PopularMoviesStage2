package nanodegree.udacity.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.database.MoviesContract;
import nanodegree.udacity.popularmovies.fragments.FavoriteFragment;
import nanodegree.udacity.popularmovies.fragments.FavoriteMoviesFragment;


public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Intent favoriteMovieIntent = getIntent();
        if (favoriteMovieIntent.hasExtra(FavoriteMoviesFragment.FAVORITE_MOVIE_INTENT)) {
            int movieId = favoriteMovieIntent.getIntExtra(FavoriteMoviesFragment.FAVORITE_MOVIE_INTENT, MoviesContract.MoviesEntry.DEFAULT_ID_VALUE);
            if (savedInstanceState == null) {
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                favoriteFragment.setmMovieId(movieId);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.favoriteFragmentContainer, favoriteFragment)
                        .commit();
            }


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


    private void errorUponLaunch() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
        finish();
    }
}

