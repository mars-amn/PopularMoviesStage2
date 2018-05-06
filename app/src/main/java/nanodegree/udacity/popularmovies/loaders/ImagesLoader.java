package nanodegree.udacity.popularmovies.loaders;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.util.List;

import nanodegree.udacity.popularmovies.BuildConfig;
import nanodegree.udacity.popularmovies.Utils.JsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ImagesLoader extends android.content.AsyncTaskLoader<List<String>> {

    List<String> posters = null;
    String movieID;

    public ImagesLoader(Context context,String id) {
        super(context);
        movieID = id;
    }

    @Override
    protected void onStartLoading() {
        if (posters!=null){
            deliverResult(posters);
        }else{
            forceLoad();
        }
    }

    @Override
    public List<String> loadInBackground() {
        // to get the posters of the movie.
        Uri moviesUri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath(movieID)
                .appendPath("images")
                .appendQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(moviesUri.toString())
                .build();

        try {
            Response response = client.newCall(request).execute();
            String JsonData = response.body().string();
            posters = JsonUtils.JSONMovieImages(JsonData);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return posters;
    }

    @Override
    public void deliverResult(List<String> data) {
        posters = data;
        super.deliverResult(data);
    }
}
