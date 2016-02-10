package es.santosgarcia.esnaschas;

import android.net.Uri;
import android.os.Environment;

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
        return false;

    }

    public static Uri getOutputMediaFileUri(int mediaType) {

        if (isExternalStorageAvailable()) {


        }
        return null;


    }

}
