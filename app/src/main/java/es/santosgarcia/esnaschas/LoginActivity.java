package es.santosgarcia.esnaschas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


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
        Button butonlogin = (Button)findViewById(R.id.loginButton);
        butonlogin.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        LoginButton();
                    }
                }
        );

    }
    public void LoginButton(){
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

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
            builder.setMessage(message);
            builder.setTitle(errortittle);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            Login(tUsr.trim(), tPass.trim());
        }
    }

    public void Login(String user, String pass){
        ParseUser.logInInBackground(user, pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Login Fail", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
    }
}
