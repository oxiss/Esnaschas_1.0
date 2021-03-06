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
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SingUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        Button send = (Button)findViewById(R.id.Btnsend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend();
            }
        });
        Button cancel = (Button)findViewById(R.id.Btncancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
    }

 protected void onSend(){
     // for hide keyboard when push send button
     InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
     inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        EditText Usernamefield = (EditText)findViewById(R.id.Usernamefield);
        EditText Emailfield = (EditText)findViewById(R.id.Emailfield);
        EditText Passwordfield = (EditText)findViewById(R.id.Passwordfield);

        String errortittle = getString(R.string.error_tittle_message);
        AlertDialog.Builder builder = new AlertDialog.Builder(SingUpActivity.this);

        String tUsr="";
        String tEmail="";
        String tPass="";

        String sUserField = getString(R.string.field_User);
         tUsr = Usernamefield.getText().toString();

        String sEmailField = getString(R.string.field_Email);
         tEmail = Emailfield.getText().toString();

        String sPassField = getString(R.string.field_Pass);
         tPass = Passwordfield.getText().toString();

        String message = null;

        if (tPass.isEmpty()){
         message = String.format(getString(R.string.empty_field_message),sPassField);
        }
        else
            tPass = String.valueOf(Passwordfield.getText());


        if (tEmail.isEmpty()){
            message = String.format(getString(R.string.empty_field_message),sEmailField);
        }
        else
            tEmail = String.valueOf(Emailfield.getText());

         if (tUsr.isEmpty()){
         message = String.format(getString(R.string.empty_field_message),sUserField);
         }
         else
            tUsr = String.valueOf(Usernamefield.getText());


        if (tUsr.trim().isEmpty()|tEmail.trim().isEmpty()|tPass.trim().isEmpty()) {
            builder.setMessage(message);
            builder.setTitle(errortittle);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            SignUp(tUsr.trim(), tEmail.trim(), tPass.trim());
        }
    }

    public void onCancel(){
        Intent intent = new Intent(SingUpActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void SignUp(String name, String email, String pass) {
        ParseUser user = new ParseUser();
        user.setUsername(name);
        user.setPassword(pass);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(SingUpActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    if (e.getCode() ==  203){
                        Toast toast1 = Toast.makeText(getApplicationContext(),"El usuario o email ya existen", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                    else if (e.getCode()==125){
                        Toast toast1 = Toast.makeText(getApplicationContext(),"El email no es correcto", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }
            }
        });
    }


}
