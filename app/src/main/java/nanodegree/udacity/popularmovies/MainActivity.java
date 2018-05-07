package nanodegree.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.fragments.FavoriteMoviesFragment;
import nanodegree.udacity.popularmovies.fragments.HighRatedMoviesFragment;
import nanodegree.udacity.popularmovies.fragments.PopularMoviesFragment;

public class MainActivity extends AppCompatActivity
        implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private final Context mContext = this;
    @BindView(R.id.toolbarMainActivity)
    Toolbar mMainActivityToolbar;
    @BindView(R.id.bottomNavigationMenu)
    BottomNavigationView mMoviesBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            launchPopularMoviesFragment();
        }
        mMainActivityToolbar.setTitle(R.string.app_name);
        mMoviesBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    private void launchPopularMoviesFragment() {
        PopularMoviesFragment mPopularMoviesFragment = new PopularMoviesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainMoviesContainer, mPopularMoviesFragment)
                .commit();
    }

    private void launchHighRatedMoviesFragment() {
        HighRatedMoviesFragment highRatedMoviesFragment = new HighRatedMoviesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainMoviesContainer, highRatedMoviesFragment)
                .commit();
    }

    private void launchFavoriteMovies() {
        FavoriteMoviesFragment favoriteMoviesFragment = new FavoriteMoviesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainMoviesContainer, favoriteMoviesFragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemPressed = item.getItemId();

        switch (itemPressed) {
            case R.id.sortByPopularMenu: {
                launchPopularMoviesFragment();
                break;
            }
            case R.id.sortByHighRatedMenu: {
                launchHighRatedMoviesFragment();
                break;
            }
            case R.id.sortByFavoriteMenu: {
                launchFavoriteMovies();
                break;
            }
            case R.id.action_settings: {
                Intent toSettings = new Intent(mContext, SettingsActivity.class);
                startActivity(toSettings);
            }
            default: {
            }

        }
        return true;
    }

}







