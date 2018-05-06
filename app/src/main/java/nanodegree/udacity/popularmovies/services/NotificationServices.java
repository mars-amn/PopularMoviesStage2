package nanodegree.udacity.popularmovies.services;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.io.IOException;

import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.Movies;
import nanodegree.udacity.popularmovies.Utils.JsonUtils;
import nanodegree.udacity.popularmovies.Utils.NotificationUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by AbdullahAtta on 3/14/2018.
 */
/*
* the service class that responsible for fetching the latest movie exists
* on TheMovieDb.org
*/
public class NotificationServices extends JobService {

    private AsyncTask<Void,Void,Movies> task;

    @Override
    public boolean onStartJob(final JobParameters job) {

        task = new AsyncTask<Void, Void, Movies>() {
            @Override
            protected Movies doInBackground(Void... voids) {
                Context context = NotificationServices.this;
                Uri moviesUri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                        .appendPath("latest")
                        .appendQueryParameter("api_key",BuildConfig.TMDB_API_KEY)
                        .build();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(moviesUri.toString())
                        .build();
                Movies movie = new Movies();
                try {
                    Response response = client.newCall(request).execute();
                    String JsonData = response.body().string();
                    movie = JsonUtils.JSONMoviesLatestForNotification(JsonData);
                    NotificationUtils.launchNotification(context,movie);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return movie;
            }

            @Override
            protected void onPostExecute(Movies movies) {
                jobFinished(job,false);
            }
        };

        task.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (task!=null)
            task.cancel(true);

        return true;
    }
}
