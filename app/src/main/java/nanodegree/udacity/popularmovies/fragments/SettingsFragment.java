package nanodegree.udacity.popularmovies.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import nanodegree.udacity.popularmovies.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.movies_settings);
    }
}
