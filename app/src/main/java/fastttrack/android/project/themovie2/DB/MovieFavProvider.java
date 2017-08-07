package fastttrack.android.project.themovie2.DB;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static fastttrack.android.project.themovie2.DB.Database.FavEntry.CONTENT_TYPE;

/**
 * Created by Fauziyyah Faturahma on 7/29/2017.
 */

public class MovieFavProvider extends ContentProvider {

    /**
     * from https://guides.codepath.com/android/Creating-Content-Providers
     */

    // Use an int for each URI we will run, this represents the different queries
    private static final int FAV = 100;
    private static final int FAV_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SQLHelperMovie sqlHelperMovie;

    @Override
    public boolean onCreate() {
        sqlHelperMovie = new SQLHelperMovie(getContext());
        return true;
    }

    /**
     * Builds a UriMatcher that is used to determine witch database request is being made.
     */

    private static UriMatcher buildUriMatcher() {
        String content = FavContract.CONTENT_AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, FavContract.PATH_FAV, FAV);
        matcher.addURI(content, FavContract.PATH_FAV + "/#", FAV_ID);

        return matcher;

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = sqlHelperMovie.getWritableDatabase();
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            case FAV:
                retCursor = db.query(
                        Database.FavEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case FAV_ID:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        Database.FavEntry.TABLE_NAME,
                        projection,
                        Database.FavEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set the notification URI for the cursor to the one passed into the function. This
        // causes the cursor to register a content observer to watch for changes that happen to
        // this URI and any of it's descendants. By descendants, we mean any URI that begins
        // with this path.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return CONTENT_TYPE;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = sqlHelperMovie.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch(sUriMatcher.match(uri)){
            case FAV:
                _id = db.insert(Database.FavEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  Database.FavEntry.buildMovieUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {


        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = sqlHelperMovie.getWritableDatabase();
        int rows;

        switch(sUriMatcher.match(uri)){
            case FAV:
                rows = db.update(Database.FavEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;

    }
}
