package fastttrack.android.project.themovie2.UI;

import fastttrack.android.project.themovie2.Model_Movie.Movie;
import fastttrack.android.project.themovie2.Model_Reviews.Reviews;
import fastttrack.android.project.themovie2.Model_Trailer.Trailer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Fauziyyah Faturahma on 7/30/2017.
 */

public interface RetrofitInterface {

    public static final String BASE_IMAGE = "https://image.tmdb.org/t/p/w185";

    @GET("movie/popular")
    Call<Movie> getMoviePopular(@Query("api_key") String apikey);

    @GET("movie/top_rated")
    Call<Movie> getMovieTopRated(@Query("api_key") String apikey);

    @GET("movie/{movie_id}/reviews")
    Call<Reviews> getMovieReview(@Path("movie_id") String moview_id, @Query("api_key") String apikey);

    @GET("movie/{movie_id}/videos")
    Call<Trailer> getMovieTrailer(@Path("movie_id") String moview_id, @Query("api_key") String apikey);

}
