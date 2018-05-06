package nanodegree.udacity.popularmovies.services;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;


public class FirebaseJobDispatcherNotificationServices  {

    private static final int NOTIFICATION_TO_NOTIFICATION_HOURS = 24;
    private static final int NOTIFICATION_TO_NOTIFICATION_SECONDS =
            (int)(TimeUnit.HOURS.toSeconds(NOTIFICATION_TO_NOTIFICATION_HOURS));

    private static final String JOB_TAG = "latest-movie-tag";
    private static boolean isInitialized;

        /*
         * basically, this method will schedule a service to
         * fetch the latest movie from TheMovieDB.org every 24 hours,
         * after keeping an eye on the latest movie API it turned out that
         * some movies are posted without posters
         * that causes the notification service not to work because I used the BigPictureStyle
         * anyway, there is no point of creating a notification without a poster.
         * and the main reason is practicing ..
         */

    synchronized public static void scheduleNotification(Context context){
        if (isInitialized)return;

            Driver driver = new GooglePlayDriver(context);

            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            Job scheduledJob = dispatcher.newJobBuilder()
                    .setTag(JOB_TAG)
                    .setService(NotificationServices.class)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setTrigger(Trigger.executionWindow(0,NOTIFICATION_TO_NOTIFICATION_SECONDS))
                    .setRecurring(true)
                    .setLifetime(Lifetime.FOREVER)
                    .setReplaceCurrent(true)
                    .build();

            dispatcher.schedule(scheduledJob);

            isInitialized = true;
    }
}
