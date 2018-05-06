package nanodegree.udacity.popularmovies.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import nanodegree.udacity.popularmovies.R;


public class PreferenceUtils {
    // to check if the user wants to get informed with latest movie or not.

    public static boolean isUserEnableNotification(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.pref_check_box_notification_key),
                context.getResources().getBoolean(R.bool.pref_notification_default_value));
    }
}
