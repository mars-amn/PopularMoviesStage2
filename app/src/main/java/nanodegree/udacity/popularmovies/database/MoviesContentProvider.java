package nanodegree.udacity.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

// the content provider to insert, read and delete movies.

public class MoviesContentProvider extends ContentProvider {

    private MoviesOpenHelper mMovieHelper;

    private static final int ALL_MOVIES = 100;
    private static final int MOVIES_WITH_ID = 101;

    private static UriMatcher sUriMatched = matchUris();

    private static UriMatcher matchUris(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MoviesContract.PROVIDER_AUTHORITY,MoviesContract.PATH_MOVIES,ALL_MOVIES);
        uriMatcher.addURI(MoviesContract.PROVIDER_AUTHORITY,MoviesContract.PATH_MOVIES +
                "/#",MOVIES_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mMovieHelper = new MoviesOpenHelper(context);
        return true;
    }

    @Override
    public Cursor query( @Nullable Uri uri,  String[] projection,  String selection,  String[] selectionArgs,
                         String sortOrder) {
        SQLiteDatabase database = mMovieHelper.getReadableDatabase();

        Cursor returnedCursor;

        switch (sUriMatched.match(uri)){
            case ALL_MOVIES:{
                returnedCursor = database.query(MoviesContract.MoviesEntry.TABLE_NAME,projection,
                        selection,
                        selectionArgs,null,null,sortOrder);
                break;
            }
            case MOVIES_WITH_ID :{
                String id = uri.getPathSegments().get(1);
                String mySelection = MoviesContract.MoviesEntry.ID_COLUMN + "=?";
                String [] mySelectionArgs = new String[]{id};

                returnedCursor = database.query(MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        mySelection,
                        mySelectionArgs,
                        null,null,sortOrder);
                break;
            }
            default:{
                throw new UnsupportedOperationException("Not Available URI : " + uri);
            }
        }
        returnedCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnedCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("We are not implementing this method!");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase database = mMovieHelper.getWritableDatabase();
        Uri returnedUri;

        switch (sUriMatched.match(uri)){
            case ALL_MOVIES :{

                long idCreated = database.insert(MoviesContract.MoviesEntry.TABLE_NAME,
                        null,contentValues);

                if (idCreated > 0){

                    returnedUri = ContentUris.withAppendedId(uri,idCreated);

                }else{
                    throw new SQLException("Something bad occurred while inserting");
                }
                break;
            }

            default :{
                throw new UnsupportedOperationException("Not Available URI : " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase database = mMovieHelper.getWritableDatabase();
        int rowsDeleted;

        switch (sUriMatched.match(uri)){
            case ALL_MOVIES :{
                rowsDeleted = database.delete(MoviesContract.MoviesEntry.TABLE_NAME,s,strings);
            break;
            }
            case MOVIES_WITH_ID :{
                String id = uri.getPathSegments().get(1);
                String mySelection = MoviesContract.MoviesEntry.ID_COLUMN + "=?";
                String [] mySelectionArgs = new String []{id};
                rowsDeleted = database.delete(MoviesContract.MoviesEntry.TABLE_NAME,
                        mySelection,
                        mySelectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not Available URI : " + uri);
            }
        }

       if (rowsDeleted > 0) {
         getContext().getContentResolver().notifyChange(uri,null);
       }
       return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("We are not implementing this method!");
    }
}
