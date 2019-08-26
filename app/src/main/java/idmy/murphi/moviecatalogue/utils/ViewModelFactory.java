package idmy.murphi.moviecatalogue.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.ui.viewmodel.MovieDetailsViewModel;
import idmy.murphi.moviecatalogue.ui.viewmodel.MovieFavoritesViewModel;
import idmy.murphi.moviecatalogue.ui.viewmodel.MovieViewModel;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowDetailsViewModel;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowFavoritesViewModel;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    private String lang;

    static ViewModelFactory getInstance(Repository repository, String lang) {
        return new ViewModelFactory(repository, lang);
    }

    static ViewModelFactory getInstance(Repository repository) {
        return new ViewModelFactory(repository);
    }

    private ViewModelFactory(Repository repository, String lang) {
        this.repository = repository;
        this.lang = lang;
    }

    private ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(repository, lang);
        } else if (modelClass.isAssignableFrom(TvShowViewModel.class)) {
            return (T) new TvShowViewModel(repository, lang);
        } else if (modelClass.isAssignableFrom(MovieDetailsViewModel.class)) {
            return (T) new MovieDetailsViewModel(repository);
        } else if (modelClass.isAssignableFrom(TvShowDetailsViewModel.class)) {
            return (T) new TvShowDetailsViewModel(repository);
        } else if (modelClass.isAssignableFrom(MovieFavoritesViewModel.class)) {
            return (T) new MovieFavoritesViewModel(repository);
        } else if (modelClass.isAssignableFrom(TvShowFavoritesViewModel.class)) {
            return (T) new TvShowFavoritesViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
