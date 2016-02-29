package es.santosgarcia.esnaschas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();
    protected Uri mMediaUri; // permite identificar ficheros
    static final int FILE_SIZE_LIMIT = 10485760;
    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int TAKE_VIDEO_REQUEST = 1;
    public static final int PICK_PHOTO_REQUEST = 2;
    public static final int PICK_VIDEO_REQUEST = 3;

    /**
     * The {@link ViewPager} that will host the section contents.
     *
     *
     *
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {

        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_sms_inc_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id) {
            case  R.id.action_logout:
                ParseUser.logOut();
                Intent intentLogin = new Intent(MainActivity.this,LoginActivity.class);
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLogin);
                break;

            case R.id.action_edit_friends:
                Intent intentFriends = new Intent(MainActivity.this,EditFriendsActivity.class);
                startActivity(intentFriends);
                break;

            case R.id.action_camera:
                dialogCameraChoices();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogCameraChoices() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(R.array.camera_choices, mDialogListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private DialogInterface.OnClickListener mDialogListener() {

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mMediaUri = FileUtilities.getOutputMediaFileUri(FileUtilities.MEDIA_TYPE_IMAGE);
                       // Si no existe identificador
                        if (mMediaUri == null) {
                            Toast.makeText(MainActivity.this, R.string.error_external_storage, Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Error en el almacenamiento externo");
                        } else {
                            // añadiremos informacion extra al intent
                            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                            startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                            Log.i(TAG, "Take Photo Option is selected");
                       }
                        break;

                    case 1:
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        mMediaUri = FileUtilities.getOutputMediaFileUri(FileUtilities.MEDIA_TYPE_VIDEO);

                        if (mMediaUri == null) {
                            Toast.makeText(MainActivity.this, R.string.error_external_storage, Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Error en el almacenamiento externo");
                        }
                        else{
                            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                            startActivityForResult(takeVideoIntent, TAKE_VIDEO_REQUEST); Log.i(TAG, "Take Video Option is selected");
                        }

                        break;

                    case 2:
                        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        choosePhotoIntent.setType("image/*");
                        startActivityForResult(choosePhotoIntent,PICK_PHOTO_REQUEST);
                        Log.i(TAG, "Choice Photo Option is selected");
                        break;

                    case 3:
                        Intent chooseVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        chooseVideoIntent.setType("video/*");
                        startActivityForResult(chooseVideoIntent,PICK_VIDEO_REQUEST);
                        Log.i(TAG, "Choice Video Option is selected");
                        break;

                }



            }
        };
        return dialogListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

    if(data!=null) {
        mMediaUri = data.getData();
    }
    else{
        Log.d(TAG, "error with getData()");
    }
        if (mMediaUri!=null) {
            try {
                InputStream miImput = getContentResolver().openInputStream(mMediaUri);
                int fileSize = miImput.available();
                if (mMediaUri != null && fileSize < FILE_SIZE_LIMIT) {
                    Toast.makeText(MainActivity.this, "todo OK", Toast.LENGTH_LONG).show();
                } else if (mMediaUri != null && fileSize > FILE_SIZE_LIMIT) {
                    Toast.makeText(MainActivity.this, "archivo muy grande", Toast.LENGTH_LONG).show();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (resultCode==RESULT_OK){
            String fileType ="";

            if (requestCode == PICK_PHOTO_REQUEST || requestCode == TAKE_PHOTO_REQUEST){
                fileType="imagen";
            }
            else{
                fileType="video";
            }

            //añadimos imagen a la galeria
            Intent mediaScantIntent;
            mediaScantIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScantIntent.setData(mMediaUri);
            sendBroadcast(mediaScantIntent);

            Intent recipientes;
            recipientes = new Intent(MainActivity.this, Recipients_activity.class);
            recipientes.setData(mMediaUri);
            recipientes.putExtra(ParseConstants.KEY_FILE_TYPE, fileType);
            startActivity(recipientes);

        }
        else{
            Log.d(TAG, "failed taking photo");

        }
    }

}
