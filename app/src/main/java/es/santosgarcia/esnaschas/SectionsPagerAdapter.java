package es.santosgarcia.esnaschas;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Santos on 26/01/2016.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected Context context;
    static  final int NUMBER_OF_TABS=2;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        context = context;
    }



    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DummySectionFragment (defined as a static inner class
        // below) with the page number as its lone argument.

        switch(position) {
            case 0:
                return new InboxFragment();
            case 1:
                return new FriendsFragment();
        }

        return null;
    }



    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return null;
            case 1:
                return null;
        }
        return null;
    }
}
