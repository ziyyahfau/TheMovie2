package fastttrack.android.project.themovie2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Fauziyyah Faturahma on 7/30/2017.
 */

public class Splashscreen extends Activity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent sp = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(sp);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
