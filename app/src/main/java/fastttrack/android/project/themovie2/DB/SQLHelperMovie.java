package fastttrack.android.project.themovie2.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fauziyyah Faturahma on 7/29/2017.
 */

public class SQLHelperMovie extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieFav.db";

    public SQLHelperMovie(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        addMovieFavList(sqLiteDatabase);

    }

    private void addMovieFavList(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + Database.FavEntry.TABLE_NAME + " (" +
                        Database.FavEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Database.FavEntry.COUMN_ID + " TEXT NOT NULL," +
                        Database.FavEntry.COLUMN_TITLE + " TEXT NOT NULL,"+
                        Database.FavEntry.COLUMN_POSTER + " TEXT NOT NULL,"+
                        Database.FavEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL,"+
                        Database.FavEntry.COLUMN_RATING + " TEXT NOT NULL,"+
                        Database.FavEntry.COLUMN_DATERELEASE + " TEXT NOT NULL,"+
                        Database.FavEntry.COLUMN_ISFAVORITE + " TEXT ,"+
                        Database.FavEntry.COLUMN_ISPOPULAR + " TEXT ,"+
                        Database.FavEntry.COLUMN_ISTOPRATED + " TEXT );"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        //nanti aja

    }
}
