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


public class TrailersLoader extends AsyncTaskLoader<Movies> {
    Movies movieTrailers;
    String movieID;

    public TrailersLoader(Context context, String id) {
        super(context);
        movieID =id;
    }

    @Override
    protected void onStartLoading() {
        if (movieTrailers != null){
            deliverResult(movieTrailers);
        }else{
            forceLoad();
        }
    }

    @Override
    public void deliverResult(Movies data) {
        movieTrailers = data;
        super.deliverResult(data);
    }

    @Override
    public Movies loadInBackground() {
        // fetch the trailers keys.

        Uri moviesUri = Uri.parse(BuildConfig.TRAILER_URL).buildUpon()
                .appendPath(movieID)
                .appendPath("videos")
                .appendQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build();
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(moviesUri.toString())
                .build();

        try {
            Response response = client.newCall(request).execute();
            String JsonData = response.body().string();
            movieTrailers = JsonUtils.JSONMoviesTrailers(JsonData);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieTrailers;
    }
}
