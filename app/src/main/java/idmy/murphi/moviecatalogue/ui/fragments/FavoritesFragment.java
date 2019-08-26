package idmy.murphi.moviecatalogue.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.ui.adapter.SectionsPagerAdapter;

public class FavoritesFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabs;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tabs);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }
}
