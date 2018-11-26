package nanodegree.udacity.popularmovies.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.R;
import nanodegree.udacity.popularmovies.activities.DetailsActivity;
import nanodegree.udacity.popularmovies.models.Movies;
import nanodegree.udacity.popularmovies.services.LatestMoviesTasks;


public class NotificationUtils {

    public static final String INTENT_EXTRA_KEY = "movie";
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "latest-movies";
    private static final String NOTIFICATION_CHANNEL_NAME = "Latest Movies";
    private static final int PENDING_INTENT_REQUEST_CODE_ACTION_DISMISS = 1;
    private static final int PENDING_INTENT_REQUEST_CODE_ACTION_OPEN = 1;
    private static final String POSTER_SIZE = "w780";


    public static void launchNotification(Context context, Movies latestMovie) {

        if (!PreferenceUtils.isUserEnableNotification(context)) return;

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel oreoChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(oreoChannel);
        }

        try {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(context.getString(R.string.notification_title_label) + latestMovie.getTitle())
                    .setContentText(latestMovie.getOverView())
                    .setSmallIcon(R.drawable.ic_movie_24dp)
                    .setAutoCancel(true)
                    .addAction(dismissAction(context))
                    .addAction(openMovieAction(context, latestMovie))
                    .setContentIntent(contentPendingIntent(context, latestMovie))
                    .setLargeIcon(Picasso.get().load(BuildConfig.POSTER_BASE_URL + POSTER_SIZE +
                            latestMovie.getPosterPath()).get())
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigLargeIcon(Picasso.get().load(BuildConfig.POSTER_BASE_URL + POSTER_SIZE +
                                    latestMovie.getPosterPath()).get())
                            .bigPicture(Picasso.get().load(BuildConfig.POSTER_BASE_URL + POSTER_SIZE +
                                    latestMovie.getPosterPath()).get())
                            .setSummaryText(latestMovie.getTitle())
                            .setBigContentTitle(latestMovie.getOverView()))
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            notificationManager.notify(NOTIFICATION_ID, notification.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static PendingIntent contentPendingIntent(Context context, Movies movie) {
        Intent iMovie = new Intent(context, DetailsActivity.class);
        iMovie.putExtra(INTENT_EXTRA_KEY, movie);

        return PendingIntent.getActivity(context,
                PENDING_INTENT_REQUEST_CODE_ACTION_OPEN,
                iMovie,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static NotificationCompat.Action dismissAction(Context context) {
        Intent iDismiss = new Intent(context, LatestMoviesTasks.class);
        iDismiss.setAction(LatestMoviesTasks.ACTION_DISMISS_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getService(context,
                PENDING_INTENT_REQUEST_CODE_ACTION_DISMISS,
                iDismiss,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action(R.drawable.ic_cancel_24dp,
                context.getString(R.string.notification_action_dismiss_label),
                pendingIntent);

    }

    private static NotificationCompat.Action openMovieAction(Context context, Movies movie) {
        Intent iMovie = new Intent(context, DetailsActivity.class);
        iMovie.putExtra(INTENT_EXTRA_KEY, movie);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                PENDING_INTENT_REQUEST_CODE_ACTION_OPEN,
                iMovie,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action(R.drawable.ic_movie_24dp,
                context.getString(R.string.notification_action_open_movie_label),
                pendingIntent);
    }
}
