package id.my.murphi.favoriteapp.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.my.murphi.favoriteapp.adapter.MovieFavoritesAdapter;
import id.my.murphi.favoriteapp.databinding.FragmentFavoriteMoviesBinding;
import id.my.murphi.favoriteapp.entity.Movie;

import static id.my.murphi.favoriteapp.Constants.URI_MOVIE;

public class MovieFavoritesFragment extends Fragment {

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
        setupListAdapter();
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = binding.moviesList.rvMovieList;
        String[] projection = {Movie.COLUMN_TITLE, Movie.COLUMN_POSTER};
        Cursor cursor = getContext().getContentResolver().query(URI_MOVIE, projection, null, null, null);

        final MovieFavoritesAdapter favoritesAdapter = new MovieFavoritesAdapter(cursor);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(layoutManager);

        if (cursor.getCount() == 0) {
            binding.moviesList.rvMovieList.setVisibility(View.GONE);
            binding.emptyState.setVisibility(View.VISIBLE);
        } else {
            binding.moviesList.rvMovieList.setVisibility(View.VISIBLE);
            binding.emptyState.setVisibility(View.GONE);
        }
    }

}
