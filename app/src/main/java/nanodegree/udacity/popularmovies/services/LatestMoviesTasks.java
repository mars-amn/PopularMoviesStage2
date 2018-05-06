package nanodegree.udacity.popularmovies.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by AbdullahAtta on 3/14/2018.
 */

public class LatestMoviesTasks extends IntentService {

    public static final String ACTION_DISMISS_NOTIFICATION = "clear";
    public LatestMoviesTasks() {
        super("LatestMoviesTasks");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_DISMISS_NOTIFICATION)){
            dismissTheNotification(this);
        }
    }

    private void dismissTheNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
