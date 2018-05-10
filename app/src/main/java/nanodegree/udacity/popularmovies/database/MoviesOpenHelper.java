package nanodegree.udacity.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AbdullahAtta on 3/5/2018.
 */

class MoviesOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Movies.db";
    private static final int DATABASE_VERSION = 3;

    public MoviesOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_QUERY = "CREATE TABLE " +
                MoviesContract.MoviesEntry.TABLE_NAME + " (" +
                MoviesContract.MoviesEntry.ID_COLUMN + " INTEGER PRIMARY KEY," +
                MoviesContract.MoviesEntry.TITLE_COLUMN + " TEXT ," +
                MoviesContract.MoviesEntry.RELEASE_DATE_COLUMN + " TEXT ," +
                MoviesContract.MoviesEntry.POSTER_COLUMN + " TEXT ," +
                MoviesContract.MoviesEntry.OVERVIEW_COLUMN + " TEXT ," +
                MoviesContract.MoviesEntry.AVERAGE_COLUMN + " REAL " +
                ");";

        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
