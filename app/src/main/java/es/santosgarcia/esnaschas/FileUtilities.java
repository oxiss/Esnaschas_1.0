package es.santosgarcia.esnaschas;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Miguel on 09/02/2016.
 */
public class FileUtilities {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;


    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else return false;

    }

    public static Uri getOutputMediaFileUri(int mediaType) {

        if (isExternalStorageAvailable()) {

        }


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MyCameraApp");
        if (! mediaStorageDir.exists()) {
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;

            }

        }
        return null;


    }

}
