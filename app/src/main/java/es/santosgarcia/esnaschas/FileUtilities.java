package es.santosgarcia.esnaschas;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Miguel on 09/02/2016.
 */
public class FileUtilities {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    final String APP_NAME="LemonChat";

    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else return false;

    }

    public static Uri getOutputMediaFileUri(int mediaType) {

        if (isExternalStorageAvailable()) {
            File mediaStorageDir = null;
            switch (mediaType) {
                case MEDIA_TYPE_IMAGE:
                    mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    break;
                case MEDIA_TYPE_VIDEO:
                    mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                    break;
            }

            String appName = "LemonChat";

            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), appName);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(appName, "failed to create directory");
                    return null;

                }

            }
            File mediaFile;
            Date now = new Date();
            String fileName = "";
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", new Locale("es","ES")).format(now);

            switch (mediaType) {
                case MEDIA_TYPE_IMAGE:
                    fileName = "IMG_"+timestamp+".jpg";
                    break;
                case MEDIA_TYPE_VIDEO:
                    fileName = "VID_"+timestamp+".mp4";
                    break;

            }

            String pathFile = mediaStorageDir.getAbsolutePath()+File.separator + fileName;
            mediaFile= new File(pathFile);
            Log.d(TAG, "File : "+mediaFile.getAbsolutePath());
            return Uri.fromFile(mediaFile);

        }

        return null;
    }
    }
