package nanodegree.udacity.popularmovies.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RESTClient {
    private static Retrofit retrofitClient = null;

    public static Retrofit getMoviesClient(String baseUrl) {
        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofitClient;
    }
}
