package nanodegree.udacity.popularmovies.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nanodegree.udacity.popularmovies.Movies;


/*
 The class contains five JSON Parsing methods
 1- For the movies in popular, high rated API.
 2- Movie's TrailersLoader.
 3- Movie's Reviews.
 4- Movie's Images.
 5- Latest movie's information for the notifications approach.
 */
public class JsonUtils
{
    public static List<Movies> JSONMovieParsingForAllMovies(String incomingJson){
        if (incomingJson.length() == 0) return null;

        List<Movies> moviesData = new ArrayList<>();

        try
        {
            JSONObject rootObject = new JSONObject(incomingJson);
            JSONArray JsonResultsArray = rootObject.getJSONArray("results");

            for (int i = 0; i < JsonResultsArray.length(); i++) {
                Movies movie = new Movies();
                JSONObject singleMovie = JsonResultsArray.getJSONObject(i);

                String posterValue = singleMovie.getString("poster_path");

                double voteAverageValue = singleMovie.optDouble("vote_average");

                String overViewValue = singleMovie.optString("overview");

                String releaseDateValue = singleMovie.optString("release_date");

                String titleValue = singleMovie.optString("title");

                int id = singleMovie.optInt("id");

                movie.setId(id);
                movie.setPosterPath(posterValue);
                movie.setTitle(titleValue);
                movie.setReleaseDate(releaseDateValue);
                movie.setOverView(overViewValue);
                movie.setAverageVote(voteAverageValue);

                moviesData.add(movie);
            }
        }
        catch (JSONException e)
        {
            Log.d(JsonUtils.class.getName()," " + e);
        }
        return moviesData;
    }

    public static Movies JSONMoviesTrailers(String incomingJson){
        if (incomingJson.length() == 0)return null;

        Movies movie = new Movies();

        List<String> trailerKeys = new ArrayList<>();


        try {
            JSONObject rootObject = new JSONObject(incomingJson);
            JSONArray resultsArray = rootObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject trailer = resultsArray.getJSONObject(i);

                String trailerKeyVal = trailer.optString("key");

                trailerKeys.add(trailerKeyVal);

            }

            movie.setTrailerKey(trailerKeys);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public static Movies JSONMoviesReviews(String incomingJson){
        if (incomingJson.length() ==0 )return null;

        Movies movie = new Movies();

        List<String> reviewsAuthor = new ArrayList<>();
        List<String> reviewsContent = new ArrayList<>();
        String reviewerName ;
        String reviewContent;

        try {
            JSONObject rootObject = new JSONObject(incomingJson);
            JSONArray resultsArray =rootObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject reviews = resultsArray.getJSONObject(i);

                 reviewerName = reviews.optString("author");

                 reviewContent = reviews.optString("content");

                reviewsAuthor.add(reviewerName);
                reviewsContent.add(reviewContent);
            }

            movie.setReviewAuthor(reviewsAuthor);
            movie.setContentReview(reviewsContent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public static List<String> JSONMovieImages(String incomingJson){
        if (incomingJson.length() == 0) return null;

        List<String> posters = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(incomingJson);
            JSONArray backDropsArray = rootObject.getJSONArray("backdrops");

            for (int i = 0; i < backDropsArray.length(); i++) {

                JSONObject filePath = backDropsArray.getJSONObject(i);

                String poster = filePath.optString("file_path");

                posters.add(poster);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posters;
    }

    public static Movies JSONMoviesLatestForNotification(String incomingJson){
        if (incomingJson.length()==0)return null;

        Movies latestMovie = new Movies();

        try {
            JSONObject rootObject = new JSONObject(incomingJson);

            int movieId = rootObject.optInt("id");

            String posterPath = rootObject.optString("poster_path");

            String overView = rootObject.optString("overview");

            double averageVote = rootObject.optDouble("vote_average");

            String title = rootObject.optString("title");

            latestMovie.setId(movieId);
            latestMovie.setAverageVote(averageVote);
            latestMovie.setTitle(title);
            latestMovie.setPosterPath(posterPath);
            latestMovie.setOverView(overView);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return latestMovie;
    }
}
