package es.santosgarcia.esnaschas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


// Punto 14


public class EditFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);
    }
    @Override
    protected void onResume() {
        super.onResume();
        ParseQuery query = ParseUser.getQuery();
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(ParseConstants.MAX_USERS);

        query.findInBackground (new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    //succes
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"EEEEEEERROR", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
    }
}



