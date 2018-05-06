package nanodegree.udacity.popularmovies.rest;

import nanodegree.udacity.popularmovies.BuildConfig;

public class MoviesAPIUtils {
    public static RESTMovies getRESTMovies() {
        return RESTClient.getMoviesClient(BuildConfig.BASE_URL).create(RESTMovies.class);
    }
}
