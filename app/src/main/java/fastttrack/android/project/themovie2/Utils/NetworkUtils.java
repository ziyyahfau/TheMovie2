package fastttrack.android.project.themovie2.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fauziyyah Faturahma on 7/11/2017.
 */

public class NetworkUtils {
    public static final String URL = "https://api.themoviedb.org/3/";

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
