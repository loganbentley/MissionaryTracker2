package missionarytracker.bent.com.missionarytracker;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by Logan on 1/6/2016.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
        ParseFacebookUtils.initialize(this);
    }
}
