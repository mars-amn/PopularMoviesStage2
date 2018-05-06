package nanodegree.udacity.popularmovies.loaders;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.Movies;
import nanodegree.udacity.popularmovies.Utils.JsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


// the loader class the responsible for fetching the movies.

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {
    private List<Movies> movies;
    private String urlPassed;

    public MoviesLoader(Context context,String url) {
        super(context);
        urlPassed = url;
    }

    @Override
    protected void onStartLoading() {
        if (movies!=null){
            deliverResult(movies);
        }else{
            forceLoad();
        }

    }

    @Override
    public void deliverResult(@Nullable List<Movies> data) {
        movies = data;
        super.deliverResult(data);

    }

    @Override
    public List<Movies> loadInBackground() {
        Uri moviesUri = Uri.parse(urlPassed).buildUpon()
                .appendQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(moviesUri.toString())
                .build();

        try {
            Response response = client.newCall(request).execute();
            String JsonData = response.body().string();
            movies = JsonUtils.JSONMovieParsingForAllMovies(JsonData);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

}
