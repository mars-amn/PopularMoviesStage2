package nanodegree.udacity.popularmovies.rest;

import nanodegree.udacity.popularmovies.BuildConfig;

public class APIUtils {
    public static MoviesAPI getMoviesAPI() {
        return MoviesClient.getMoviesClient(BuildConfig.BASE_URL).create(MoviesAPI.class);
    }
}
