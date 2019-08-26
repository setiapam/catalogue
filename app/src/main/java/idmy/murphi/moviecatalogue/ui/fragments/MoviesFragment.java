package idmy.murphi.moviecatalogue.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
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
import idmy.murphi.moviecatalogue.ui.activities.SettingActivity;
import idmy.murphi.moviecatalogue.ui.adapter.MoviesAdapter;
import idmy.murphi.moviecatalogue.ui.viewmodel.MovieViewModel;
import idmy.murphi.moviecatalogue.utils.Injection;
import idmy.murphi.moviecatalogue.utils.ItemOffsetDecoration;
import idmy.murphi.moviecatalogue.utils.ViewModelFactory;

public class MoviesFragment extends Fragment {
    private MovieViewModel movieViewModel;
    private MoviesAdapter moviesAdapter;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieViewModel = obtainViewModel(getActivity());
        RecyclerView recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movie_list);
        moviesAdapter = new MoviesAdapter(movieViewModel);
        final GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (moviesAdapter.getItemViewType(position) == R.layout.network_state_item) {
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(moviesAdapter);

        movieViewModel.getNetworkState().observe(getViewLifecycleOwner(), moviesAdapter::setNetworkState);

        movieViewModel.getMoviesPagedList().observe(getViewLifecycleOwner(), moviesAdapter::submitList);

    }

    private MovieViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity, getResources().getConfiguration().locale.getLanguage().toLowerCase());
        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
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
            Intent mIntent = new Intent(getContext(), SettingActivity.class);
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
                    movieViewModel.getMoviesPagedList(query).observe(getViewLifecycleOwner(), moviesAdapter::submitList);
                } else {
                    movieViewModel.getMoviesPagedList().observe(getViewLifecycleOwner(), moviesAdapter::submitList);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieViewModel.getMoviesPagedList(newText).observe(getViewLifecycleOwner(), moviesAdapter::submitList);
                return true;
            }
        });
    }
}
