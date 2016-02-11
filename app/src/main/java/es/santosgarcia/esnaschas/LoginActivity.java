package es.santosgarcia.esnaschas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;



public class LoginActivity extends AppCompatActivity {



    MenuItem miActionProgressItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView text = (TextView)findViewById(R.id.SignUpText);
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.btnWithText);

        text.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, SingUpActivity.class);
                        startActivity(intent);
                    }
                }
        );

        circularButton1.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        LoginButton();
                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        miActionProgressItem=menu.findItem(R.id.miActionProgress);
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        return super.onPrepareOptionsMenu(menu);
    }

    public void LoginButton(){
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.btnWithText);
        circularButton1.setProgress(50);

        circularButton1.setIndeterminateProgressMode(true);

        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        EditText Usernamefield = (EditText)findViewById(R.id.Usernamefield);
        EditText Passwordfield = (EditText)findViewById(R.id.Passwordfield);

        String errortittle = getString(R.string.error_tittle_message);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        String tUsr="";
        String tPass="";

        String sUserField = getString(R.string.field_User);
        tUsr = Usernamefield.getText().toString();

        String sPassField = getString(R.string.field_Pass);
        tPass = Passwordfield.getText().toString();

        String message = null;

        if (tPass.isEmpty()){
            message = String.format(getString(R.string.empty_field_message),sPassField);

        }
        else
            tPass = String.valueOf(Passwordfield.getText());

        if (tUsr.isEmpty()){
            message = String.format(getString(R.string.empty_field_message),sUserField);

        }
        else
            tUsr = String.valueOf(Usernamefield.getText());


        if (tUsr.trim().isEmpty()|tPass.trim().isEmpty()) {
            circularButton1.setProgress(-1);
            esperar(circularButton1);
            builder.setMessage(message);
            builder.setTitle(errortittle);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            AlertDialog dialog = builder.create();
            dialog.show();


        }
        else{
            showProgressBar();
            Login(tUsr.trim(), tPass.trim());
        }
    }

    public void Login(String user, String pass){
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.btnWithText);
        ParseUser.logInInBackground(user, pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {



                if (user != null) {
                    circularButton1.setProgress(100);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    circularButton1.setProgress(-1);
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Login Fail", Toast.LENGTH_SHORT);
                    toast1.show();
                    esperar(circularButton1);
                }
                hideProgressBar();
            }
        });
    }

    public void showProgressBar(){
        miActionProgressItem.setVisible(true);
    }
    public void hideProgressBar(){
        miActionProgressItem.setVisible(false);

    }

    public void esperar(CircularProgressButton c){

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                        sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        c.setProgress(0);
    }

}
