package es.santosgarcia.esnaschas;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


// Punto 14


public class EditFriendsActivity extends ListActivity {

    List<ParseUser> mUsers;
    ArrayList<String> usernames;
    ArrayList<String> objectIds;
    ArrayAdapter<String> adapter;
    ParseRelation <ParseUser> mFriendsRelation;
    ParseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar mibarra = (ProgressBar) findViewById(R.id.progressBar2);
        setContentView(R.layout.activity_edit_friends);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    @Override
    protected void onResume() {
        super.onResume();

        usernames=new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,usernames);
        objectIds = new ArrayList<String>();

        setListAdapter(adapter);
        ParseQuery query = ParseUser.getQuery();
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(ParseConstants.MAX_USERS);

        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    //succes
                    mUsers = users;
                    for (ParseUser user : mUsers) {
                        objectIds.add(user.getObjectId());
                        adapter.add(user.getUsername());
                    }
                    addFriendCheckmarks();
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "EEEEEEERROR", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
        mCurrentUser=ParseUser.getCurrentUser();
        mFriendsRelation=mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);


    }

    protected void addFriendCheckmarks(){
        final ProgressBar mibarra = (ProgressBar) findViewById(R.id.progressBar2);
        mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if(e==null){
                    //success
                    for(ParseUser user:parseUsers){
                        Toast toast1 = Toast.makeText(getApplicationContext(), "sin errores", Toast.LENGTH_SHORT);
                        toast1.show();
                        if (objectIds.contains(user.getObjectId()))
                            getListView().setItemChecked(objectIds.indexOf(user.getObjectId()),true);
                    }
                    mibarra.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast toast1 = Toast.makeText(getApplicationContext(), "error en addFriendCheckmarks", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
    }


    @Override
    protected void onListItemClick(ListView l,View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        Toast toast1 = Toast.makeText(getApplicationContext(), "Funcioooooonoaaaa", Toast.LENGTH_SHORT);
        toast1.show();
        mFriendsRelation.add(mUsers.get(position));
        if (getListView().isItemChecked(position)){

        }
        else{
            mFriendsRelation.remove(mUsers.get(position));
        }

        mCurrentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });



    }
}



