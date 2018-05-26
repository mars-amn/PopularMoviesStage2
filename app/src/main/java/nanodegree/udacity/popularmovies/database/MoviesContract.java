package nanodegree.udacity.popularmovies.database;

import android.net.Uri;

/**
 * Created by AbdullahAtta on 3/5/2018.
 */

public final class MoviesContract {

    public static final String PROVIDER_AUTHORITY = "nanodegree.udacity.popularmovies.database";
    public static final String PATH_MOVIES = "favoriteMovies";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + PROVIDER_AUTHORITY);

    private MoviesContract() {
    }

    public static class MoviesEntry {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "favoriteMovies";


        public static final String ID_COLUMN = "_movieId";
        public static final String TITLE_COLUMN = "title";
        public static final String POSTER_COLUMN = "poster";
        public static final String OVERVIEW_COLUMN = "overview";
        public static final String RELEASE_DATE_COLUMN = "releaseDate";
        public static final String AVERAGE_COLUMN = "averageVote";

        public static final int DEFAULT_ID_VALUE = -1;
    }
}
