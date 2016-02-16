package es.santosgarcia.esnaschas;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class ViewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ProgressBar mibarra = (ProgressBar)findViewById(R.id.progressBar4);
        mibarra.setVisibility(View.VISIBLE);
        ImageView miImagen = (ImageView)findViewById(R.id.visorimagen);
        Uri imageUri = getIntent().getData();
        Picasso.with(this).load(imageUri.toString()).into(miImagen);
        mibarra.setVisibility(View.INVISIBLE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, ParseConstants.TASK_DELAY);
    }

}
