package es.santosgarcia.esnaschas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView text = (TextView)findViewById(R.id.SignUpText);

        text.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, SingUpActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
