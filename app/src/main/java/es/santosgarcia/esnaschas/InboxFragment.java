package es.santosgarcia.esnaschas;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by Santos on 26/01/2016.
 */
public class InboxFragment extends ListFragment{

    ProgressBar spinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inboxfragment, container, false);
         spinner = (ProgressBar)rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        return rootView;
    }


}
