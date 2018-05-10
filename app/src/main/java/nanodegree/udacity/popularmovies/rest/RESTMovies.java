package nanodegree.udacity.popularmovies.rest;

import nanodegree.udacity.popularmovies.models.MovieImages;
import nanodegree.udacity.popularmovies.models.MoviesResults;
import nanodegree.udacity.popularmovies.models.MoviesReviews;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RESTMovies {
    @GET("3/movie/popular")
    Call<MoviesResults> getPopularMovies(@Query("api_key") String apiKey);

    @GET("3/movie/top_rated")
    Call<MoviesResults> getHighRatedMovies(@Query("api_key") String apiKey);

    @GET("3/movie/{movie_id}/images")
    Call<MovieImages> getMovieImages(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/reviews")
    Call<MoviesReviews> getMovieReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
