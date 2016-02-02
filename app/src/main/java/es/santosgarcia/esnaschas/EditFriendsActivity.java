package es.santosgarcia.esnaschas;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


// Punto 14


public class EditFriendsActivity extends ListActivity {

    List<ParseUser> mUsers;
    ArrayList<String> usernames;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar mibarra = (ProgressBar) findViewById(R.id.progressBar2);
        //mibarra.setVisibility(View.VISIBLE);
        setContentView(R.layout.activity_edit_friends);
    }
    @Override
    protected void onResume() {
        super.onResume();

        usernames=new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,usernames);
        setListAdapter(adapter);
        ParseQuery query = ParseUser.getQuery();
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(ParseConstants.MAX_USERS);

        query.findInBackground (new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    //succes
                    mUsers=users;
                    for(ParseUser user:mUsers){
                        adapter.add(user.getUsername());
                    }
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"EEEEEEERROR", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
    }
}



