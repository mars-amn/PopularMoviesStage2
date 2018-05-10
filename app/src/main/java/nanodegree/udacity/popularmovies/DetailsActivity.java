package nanodegree.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.fragments.MovieDetailsFragment;
import nanodegree.udacity.popularmovies.fragments.PopularMoviesFragment;
import nanodegree.udacity.popularmovies.models.MoviesResponse;

public class DetailsActivity extends AppCompatActivity {

    MovieDetailsFragment mMovieFragment;
    private MoviesResponse mMovie;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent movieIntent = getIntent();
        if (movieIntent.hasExtra(PopularMoviesFragment.MOVIE_INTENT_KEY)) {
            mMovie = movieIntent.getParcelableExtra(PopularMoviesFragment.MOVIE_INTENT_KEY);

        } else {
            errorUponLaunch();
        }


        if (savedInstanceState == null) {
            mMovieFragment = new MovieDetailsFragment();
            mMovieFragment.setMMovie(mMovie);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movieDetailsContainer, mMovieFragment)
                    .commit();
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
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
        finish();
    }
}

