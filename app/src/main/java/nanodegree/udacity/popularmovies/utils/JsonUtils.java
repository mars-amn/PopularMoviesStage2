package nanodegree.udacity.popularmovies.utils;

import org.json.JSONException;
import org.json.JSONObject;

import nanodegree.udacity.popularmovies.models.Movies;

public class JsonUtils {
    public static Movies JSONMoviesLatestForNotification(String incomingJson) {
        if (incomingJson.length() == 0) return null;

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
