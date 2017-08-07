package fastttrack.android.project.themovie2;

import android.content.ContentValues;
import android.content.Entity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fastttrack.android.project.themovie2.Adapter.MovieAdapter;
import fastttrack.android.project.themovie2.Model_Movie.Movie;
import fastttrack.android.project.themovie2.Model_Movie.Result;
import fastttrack.android.project.themovie2.UI.RetrofitInterface;
import fastttrack.android.project.themovie2.Utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_DATERELEASE;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_ISFAVORITE;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_ISPOPULAR;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_ISTOPRATED;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_POSTER;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_RATING;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_SYNOPSIS;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COLUMN_TITLE;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.CONTENT_URI;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.COUMN_ID;
import static fastttrack.android.project.themovie2.DB.Database.FavEntry.PROJECTION;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.movieList)
    RecyclerView MovieList;
    private List<Result> movieList = new ArrayList<Result>();
    private MovieAdapter movieAdapter;
    int selected = 1;
    //private GridLayoutManager gridLayoutManager;
    String KEY_STATE="scroll";
    String LIST="list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MovieList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        movieAdapter = new MovieAdapter(movieList);
        MovieList.setAdapter(movieAdapter);
        MovieList.setHasFixedSize(true);
        //actionMoviePopular();
        getAllMovie();

    }


    //for save data before destroy
    @Override
    protected void onSaveInstanceState(Bundle outState)  {
        //outState.putParcelable(KEY_STATE, gridLayoutManager.onSaveInstanceState());
        outState.putParcelable(KEY_STATE, MovieList.getLayoutManager().onSaveInstanceState());
        outState.putString(LIST, new GsonBuilder().create().toJson(movieList));
        super.onSaveInstanceState(outState);
    }

    //restore data from savedInstance, data doesn't dessepear
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){
            Parcelable savedRecycleLayoutState = savedInstanceState.getParcelable(KEY_STATE);
            MovieList.getLayoutManager().onRestoreInstanceState(savedRecycleLayoutState);
            String strSavedMovieJson = savedInstanceState.getString(LIST);
            Result[] arrSavedMovieList = new GsonBuilder().create().fromJson(strSavedMovieJson, Result[].class);
            List<Result> savedMoviveList = Arrays.asList(arrSavedMovieList);

            movieList.clear();
            movieList.addAll(savedMoviveList);

        }
    }

    //get All Movie
    public  void getAllMovie (){

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                RetrofitInterface retrofitInterface = NetworkUtils.getRetrofit().create(RetrofitInterface.class);

                //for movie popular
                Call<Movie> moviePopular = retrofitInterface.getMoviePopular(BuildConfig.MOVIE_API_KEY);
                try {
                    Movie moviePop = moviePopular.execute().body();
                    for (Result data:moviePop.getResults()){

                        Cursor cursor = getContentResolver().query(CONTENT_URI, new String[]{String.valueOf(data.getId())}, "movie_id=?", null, null, null);
                        if(!cursor.moveToFirst()){

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COUMN_ID, data.getId());
                            contentValues.put(COLUMN_TITLE, data.getTitle());
                            contentValues.put(COLUMN_POSTER, data.getPosterPath());
                            contentValues.put(COLUMN_SYNOPSIS, data.getOverview());
                            contentValues.put(COLUMN_RATING, data.getVoteAverage());
                            contentValues.put(COLUMN_DATERELEASE, data.getReleaseDate());
                            contentValues.put(COLUMN_ISPOPULAR, 1);
                            contentValues.put(COLUMN_ISTOPRATED, 0);

                            //untuk akses konten provider
                            getContentResolver().insert(CONTENT_URI, contentValues);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //for movie top rated
                Call<Movie> movieTopRated = retrofitInterface.getMovieTopRated(BuildConfig.MOVIE_API_KEY);
                try {
                    Movie movieTop = movieTopRated.execute().body();
                    for (Result data:movieTop.getResults()){

                        Cursor cursor = getContentResolver().query(CONTENT_URI, new String[]{String.valueOf(data.getId())}, "movie_id=?", null, null, null);
                        if(!cursor.moveToFirst()){

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COUMN_ID, data.getId());
                            contentValues.put(COLUMN_TITLE, data.getTitle());
                            contentValues.put(COLUMN_POSTER, data.getPosterPath());
                            contentValues.put(COLUMN_SYNOPSIS, data.getOverview());
                            contentValues.put(COLUMN_RATING, data.getVoteAverage());
                            contentValues.put(COLUMN_DATERELEASE, data.getReleaseDate());
                            contentValues.put(COLUMN_ISPOPULAR, 0);
                            contentValues.put(COLUMN_ISTOPRATED, 1);

                            //untuk akses konten provider
                            getContentResolver().insert(CONTENT_URI, contentValues);

                        }
                    };
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Bundle bundle = new Bundle();
                bundle.putString("selected", String.valueOf(selected));
                getSupportLoaderManager().restartLoader(1, bundle, MainActivity.this);


            }
        }.execute();
    }

    //showing top rated movies
    private void actionMovieTopRated() {
        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                RetrofitInterface retrofitInterface = NetworkUtils.getRetrofit().create(RetrofitInterface.class);
                Call<Movie> movie = retrofitInterface.getMovieTopRated(BuildConfig.MOVIE_API_KEY);
                movie.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Log.d(MainActivity.class.getSimpleName(), "onResponse: ");
                        movieList.clear();
                        movieList.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Log.e(MainActivity.class.getSimpleName(), "onFailure: ");
                    }
                });
                return "";
            }
        }.execute();
    }

    //showing most popular movies
    private void actionMoviePopular() {
        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                RetrofitInterface retrofitInterface = NetworkUtils.getRetrofit().create(RetrofitInterface.class);
                Call<Movie> movie = retrofitInterface.getMoviePopular(BuildConfig.MOVIE_API_KEY);
                movie.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Log.d(MainActivity.class.getSimpleName(), "onResponse: ");
                        movieList.clear();
                        movieList.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Log.e(MainActivity.class.getSimpleName(), "onFailure: ");
                    }
                });
                return "";
            }
        }.execute();
    }

    //showing most favorite movies
    private void actionMovieFavorite() {

        getSupportLoaderManager().initLoader(1, null, this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_most_movies:
            {
                selected = 1;
                Bundle bundle = new Bundle();
                bundle.putString("selected", String.valueOf(selected));
                getSupportLoaderManager().restartLoader(1, bundle, MainActivity.this);
            }
            break;
            case R.id.action_sort_by_top_rated:
            {
                selected = 2;
                Bundle bundle = new Bundle();
                bundle.putString("selected", String.valueOf(selected));
                getSupportLoaderManager().restartLoader(1, bundle, MainActivity.this);
            }
            break;
            case R.id.action_favorite:
            {
                selected = 3;
                Bundle bundle = new Bundle();
                bundle.putString("selected", String.valueOf(selected));
                getSupportLoaderManager().restartLoader(1, bundle, MainActivity.this);
            }
            break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int selectedfrombundle = Integer.parseInt(args.getString("selected"));
        if(selectedfrombundle == 1){
            return new CursorLoader(this, CONTENT_URI, PROJECTION, "isPopular=?", new String[]{"1"}, null);
        }
        else  if (selectedfrombundle == 2){
            return new CursorLoader(this, CONTENT_URI, PROJECTION, "isTopRated=?", new String[]{"1"}, null);
        }
        else  if(selectedfrombundle == 3){
            return new CursorLoader(this, CONTENT_URI, PROJECTION, "isFavorite=?", new String[]{"1"}, null);
        }
        else return null;

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        movieList.clear();

        if(data!=null){
            while (data.moveToNext()){

                String id = data.getString(data.getColumnIndex(COUMN_ID));
                String title = data.getString(data.getColumnIndex(COLUMN_TITLE));
                String poster = data.getString(data.getColumnIndex(COLUMN_POSTER));
                String synopsis = data.getString(data.getColumnIndex(COLUMN_SYNOPSIS));
                String rating = data.getString(data.getColumnIndex(COLUMN_RATING));
                String daterelease = data.getString(data.getColumnIndex(COLUMN_DATERELEASE));
                String isfav = data.getString(data.getColumnIndex(COLUMN_ISFAVORITE));

                Result result = new Result();
                result.setId(Integer.parseInt(id));
                result.setTitle(title);
                result.setOverview(synopsis);
                result.setVoteAverage(Double.parseDouble(rating));
                result.setPosterPath(poster);
                result.setReleaseDate(daterelease);

                movieList.add(result);
            }
        }

        movieAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }
}
