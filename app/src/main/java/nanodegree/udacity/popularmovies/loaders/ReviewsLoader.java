package nanodegree.udacity.popularmovies.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;

import java.io.IOException;

import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.Movies;
import nanodegree.udacity.popularmovies.Utils.JsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class ReviewsLoader extends AsyncTaskLoader<Movies>{
    Movies movieReviews;
    String movieID ;
    public ReviewsLoader(Context context,String id) {
        super(context);
        movieID = id;
    }

    @Override
    protected void onStartLoading() {
        if (movieReviews != null){
            deliverResult(movieReviews);
        }else{
            forceLoad();
        }
    }

    @Override
    public void deliverResult(Movies data) {
        movieReviews = data;
        super.deliverResult(data);
    }

    @Override
    public Movies loadInBackground() {
        // fetching the reviewers names and their reviews.
        Uri moviesUri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath(movieID)
                .appendPath("reviews")
                .appendQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(moviesUri.toString())
                .build();

        try {
            Response response = client.newCall(request).execute();
            String JsonData = response.body().string();
            movieReviews = JsonUtils.JSONMoviesReviews(JsonData);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieReviews;
    }
}
