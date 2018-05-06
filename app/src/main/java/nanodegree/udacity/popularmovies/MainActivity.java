package nanodegree.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popularmovies.fragments.HighRatedMoviesFragment;
import nanodegree.udacity.popularmovies.fragments.PopularMoviesFragment;


public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener {

    private final Context mContext = this;
    @BindView(R.id.drawerLayoutMainActivity)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbarMainActivity)
    Toolbar mMainActivityToolbar;
    @BindView(R.id.navigationViewMainActivity)
    NavigationView mMainActivityNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMainActivityViews();

        if (savedInstanceState == null) {
            launchPopularMoviesFragment();
        }
    }

    private void initMainActivityViews() {
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,
                mMainActivityToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mMainActivityNavigationView.setNavigationItemSelectedListener(this);

        mMainActivityToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mMainActivityToolbar.setTitle(R.string.app_name);
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
            case R.id.action_settings: {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent toSettings = new Intent(mContext, SettingsActivity.class);
                        startActivity(toSettings);
                    }
                }, 180);
            }
            default: {
            }

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}







