package es.santosgarcia.esnaschas;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Recipients_activity extends ListActivity {


    protected List<ParseUser> mFriends;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;
    private ProgressBar prg;
    protected MenuItem mSendMenuItem;
    protected Uri mMediaUri;
    protected String mFileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mMediaUri = intent.getData();
        mFileType=intent.getStringExtra(ParseConstants.KEY_FILE_TYPE);

        prg = (ProgressBar)findViewById(R.id.progressBar3);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
        prg.setVisibility(View.VISIBLE);
        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {


                if (e == null) {
                    mFriends = friends;
                    String[] usernames = new String[mFriends.size()];
                    int i = 0;
                    prg.setVisibility(View.GONE);
                    for (ParseUser user : mFriends) {
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getListView().getContext(),
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    setListAdapter(adapter);

                    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                } else {
                    Toast toastFriends = Toast.makeText(Recipients_activity.this, "Error", Toast.LENGTH_SHORT);
                    toastFriends.show();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipients, menu);
        mSendMenuItem = menu.getItem(0);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_send:
                ParseObject message = createMessage();
                if (message == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("error al seleccionar archivo")
                            .setTitle("Error")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    send(message);
                    finish();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void send(ParseObject message) {
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // success!
                    Toast.makeText(Recipients_activity.this,"Mensaje enviado!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Recipients_activity.this,"Mensaje NO enviado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private ParseObject createMessage(){
        ParseObject message = new ParseObject(ParseConstants.CLASS_MESSAGES);
        message.put(ParseConstants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
        message.put(ParseConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
        message.put(ParseConstants.KEY_RECIPIENTS_ID, getRecipientsIds());
        message.put(ParseConstants.KEY_FILE_TYPE, mFileType);
        byte[] miarraybytes = FileHelper.getByteArrayFromFile(this,mMediaUri);

        if (miarraybytes==null){
            return null;
        }
        else{
            if (mFileType.equals(ParseConstants.TYPE_IMAGE)) {
                miarraybytes = FileHelper.reduceImageForUpload(miarraybytes);
            }

            String fileName = FileHelper.getFileName(this, mMediaUri, mFileType);
            ParseFile file = new ParseFile(fileName, miarraybytes);
            message.put(ParseConstants.KEY_FILE, file);

            return message;
        }
    }

    private ArrayList<String> getRecipientsIds(){
        ArrayList<String> recipientList = new ArrayList<String>();
        for (int i =0; i < getListView().getCount();i++){
            if (getListView().isItemChecked(i)){
                recipientList.add(mFriends.get(i).getObjectId());
            }
        }
        return  recipientList;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (l.getCheckedItemCount() > 0) {
            mSendMenuItem.setVisible(true);
        } else {
            mSendMenuItem.setVisible(false);
        }
    }
    }


