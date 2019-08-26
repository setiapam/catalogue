package idmy.murphi.moviecatalogue.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.ui.fragments.MovieFavoritesFragment;
import idmy.murphi.moviecatalogue.ui.fragments.TvShowFavoritesFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;


    public SectionsPagerAdapter(Context c, FragmentManager fm) {
        super(fm);
        this.mContext = c;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieFavoritesFragment.newInstance();
            case 1:
                return TvShowFavoritesFragment.newInstance();
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}