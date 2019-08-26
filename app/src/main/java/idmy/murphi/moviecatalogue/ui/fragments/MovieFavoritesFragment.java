package idmy.murphi.moviecatalogue.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.databinding.FragmentFavoriteMoviesBinding;
import idmy.murphi.moviecatalogue.ui.adapter.MovieFavoritesAdapter;
import idmy.murphi.moviecatalogue.ui.viewmodel.MovieFavoritesViewModel;
import idmy.murphi.moviecatalogue.utils.Injection;
import idmy.murphi.moviecatalogue.utils.ItemOffsetDecoration;
import idmy.murphi.moviecatalogue.utils.ViewModelFactory;

public class MovieFavoritesFragment extends Fragment {

    private MovieFavoritesViewModel viewModel;
    private FragmentFavoriteMoviesBinding binding;

    public static MovieFavoritesFragment newInstance() {
        return new MovieFavoritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = obtainViewModel(getActivity());
        setupListAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setupListAdapter() {
        RecyclerView recyclerView = binding.moviesList.rvMovieList;
        final MovieFavoritesAdapter favoritesAdapter = new MovieFavoritesAdapter();
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(Objects.requireNonNull(getActivity()), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        viewModel.getFavoriteListLiveData().observe(getViewLifecycleOwner(), movieList -> {
            if (movieList.isEmpty()) {
                binding.moviesList.rvMovieList.setVisibility(View.GONE);
                binding.emptyState.setVisibility(View.VISIBLE);
            } else {
                binding.moviesList.rvMovieList.setVisibility(View.VISIBLE);
                binding.emptyState.setVisibility(View.GONE);
                favoritesAdapter.submitList(movieList);
            }
        });
    }

    private MovieFavoritesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
        return ViewModelProviders.of(activity, factory).get(MovieFavoritesViewModel.class);
    }
}
