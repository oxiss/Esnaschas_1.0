package es.santosgarcia.esnaschas;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Santos on 14/01/2016.
 */
public class EsnaschasApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}