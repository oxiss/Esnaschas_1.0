package es.santosgarcia.esnaschas;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Santos on 26/01/2016.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context context;
    static  final int NUMBER_OF_TABS=2;

    public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);

    }
    @Override
    public Fragment getItem(int position) {
        return new InboxFragment();
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
