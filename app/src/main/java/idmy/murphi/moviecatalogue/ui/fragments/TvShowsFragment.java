package idmy.murphi.moviecatalogue.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.ui.adapter.TvShowsAdapter;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowViewModel;
import idmy.murphi.moviecatalogue.utils.Injection;
import idmy.murphi.moviecatalogue.utils.ItemOffsetDecoration;
import idmy.murphi.moviecatalogue.utils.ViewModelFactory;

public class TvShowsFragment extends Fragment {
    private TvShowViewModel tvShowViewModel;
    private TvShowsAdapter tvShowsAdapter;

    public static TvShowsFragment newInstance() {
        return new TvShowsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvShowViewModel = obtainViewModel(getActivity());

        RecyclerView recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_tv_list);
        tvShowsAdapter = new TvShowsAdapter(tvShowViewModel);
        final GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (tvShowsAdapter.getItemViewType(position) == R.layout.network_state_item) {
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });

        recyclerView.setAdapter(tvShowsAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        tvShowViewModel.getNetworkState().observe(getViewLifecycleOwner(), tvShowsAdapter::setNetworkState);

        tvShowViewModel.getTvShowsPagedList().observe(getViewLifecycleOwner(), tvShowsAdapter::submitList);
    }

    private TvShowViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity, getResources().getConfiguration().locale.getLanguage().toLowerCase());
        return ViewModelProviders.of(activity, factory).get(TvShowViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return true;
    }

    public void search(final SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    searchView.setQuery("", false);
                    searchView.clearFocus();
                    searchView.setIconified(true);
                    tvShowViewModel.getTvShowsPagedList(query).observe(getViewLifecycleOwner(), tvShowsAdapter::submitList);
                } else {
                    tvShowViewModel.getTvShowsPagedList().observe(getViewLifecycleOwner(), tvShowsAdapter::submitList);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tvShowViewModel.getTvShowsPagedList(newText).observe(getViewLifecycleOwner(), tvShowsAdapter::submitList);
                return true;
            }
        });
    }

}
