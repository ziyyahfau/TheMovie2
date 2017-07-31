package fastttrack.android.project.themovie2.DB;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Fauziyyah Faturahma on 7/29/2017.
 */

public class Database {

    /**
     * from https://guides.codepath.com/android/Creating-Content-Providers
     */


    /**
     * Create one class for each table that handles all information regarding the table schema and
     * the URIs related to it.
     */
    public static final class FavEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI = FavContract.BASE_CONTENT_URI.buildUpon().appendPath(FavContract.PATH_FAV).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + FavContract.PATH_FAV;

        // Define the table schema
        public static final String TABLE_NAME = "movieFavorite";
        public static final String COUMN_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_SYNOPSIS = "overview";
        public static final String COLUMN_RATING = "vote_average";
        public static final String COLUMN_DATERELEASE = "release_date";
        public static final String COLUMN_ISFAVORITE = "isFavorite";
        public static final String COLUMN_ISPOPULAR = "isPopular";
        public static final String COLUMN_ISTOPRATED = "isTopRated";



        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String[] PROJECTION = {

                COUMN_ID, COLUMN_TITLE, COLUMN_POSTER, COLUMN_SYNOPSIS, COLUMN_RATING, COLUMN_DATERELEASE, COLUMN_ISFAVORITE

        };
    }

}
