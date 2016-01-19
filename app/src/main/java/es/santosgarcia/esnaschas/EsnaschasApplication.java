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
        Parse.initialize(this);
    }
}
