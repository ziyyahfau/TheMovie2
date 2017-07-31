package fastttrack.android.project.themovie2.DB;

import android.net.Uri;

/**
 * Created by Fauziyyah Faturahma on 7/29/2017.
 */

public class FavContract {


    /**
     * from https://guides.codepath.com/android/Creating-Content-Providers
     */


    /**
     * The Content Authority is a name for the entire content provider, similar to the relationship
     * between a domain name and its website. A convenient string to use for content authority is
     * the package name for the app, since it is guaranteed to be unique on the device.
     */
    public static final String CONTENT_AUTHORITY = "fastttrack.android.project.themovie2";

    /**
     * The content authority is used to create the base of all URIs which apps will use to
     * contact this content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * A list of possible paths that will be appended to the base URI for each of the different
     * tables.
     */
    public static final String PATH_FAV = "movieFavorite";


}
