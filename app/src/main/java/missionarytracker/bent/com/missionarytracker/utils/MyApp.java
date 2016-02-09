package missionarytracker.bent.com.missionarytracker.utils;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Logan on 1/6/2016.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
        ParseFacebookUtils.initialize(this);
    }
}
