package es.santosgarcia.esnaschas;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santos on 26/01/2016.
 */
public class InboxFragment extends ListFragment{

    ProgressBar spinner;
    protected List<ParseObject> mMessages;
    private ArrayList<String>messages;
    private ArrayAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inboxfragment, container, false);
        spinner = (ProgressBar)rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();
        messages = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, messages);
        setListAdapter(adapter);

        ParseQuery<ParseObject>query=ParseQuery.getQuery(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENTS_ID, ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    mMessages = parseObjects;

                    for (ParseObject message : mMessages) {
                        adapter.add(message.getString(ParseConstants.KEY_SENDER_NAME));
                    }
                    spinner.setVisibility(View.INVISIBLE);
                } else {
                    Toast toastFriends = Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT);
                    toastFriends.show();
                }
            }
        });



    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);
        ParseObject message = mMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());

        if (messageType.equals(ParseConstants.TYPE_IMAGE)){
            Intent i = new Intent (getActivity(), ViewImageActivity.class);
            i.setData(fileUri);
            startActivity(i);
        }
        else{
            Intent i = new Intent (Intent.ACTION_VIEW,fileUri);
            i.setDataAndType(fileUri,"video/*");
            startActivity(i);
        }

        List<String> ids = message.getList(ParseConstants.KEY_RECIPIENTS_ID);
        if (ids.size()>1){
            //borrar receptor de la lista
            ids.remove(ParseUser.getCurrentUser().getObjectId());
            ArrayList<String> idsToRemove = new ArrayList();
            idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
            message.removeAll(ParseConstants.KEY_RECIPIENTS_ID,idsToRemove);
            message.saveInBackground();
        }
        else{
            //borrar mensaje
            message.deleteInBackground();
        }

    }


}
