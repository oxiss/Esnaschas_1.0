package es.santosgarcia.esnaschas;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Santos on 28/01/2016.
 */
public class FriendsFragment extends ListFragment {
    protected List<ParseUser> mFriends;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;
    private ProgressBar prg;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friendsfragment, container, false);
        prg = (ProgressBar)rootView.findViewById(R.id.progressFriends);
        prg.setVisibility(View.GONE);

        return rootView;
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
                            android.R.layout.simple_list_item_1,
                            usernames);
                    setListAdapter(adapter);

                } else {
                    Toast toastFriends = Toast.makeText(getContext(), "EEEEEEERROR", Toast.LENGTH_SHORT);
                    toastFriends.show();

                }

            }
        });
    }
}
