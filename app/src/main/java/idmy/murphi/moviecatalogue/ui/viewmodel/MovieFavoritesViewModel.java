package idmy.murphi.moviecatalogue.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.model.Movie;

public class MovieFavoritesViewModel extends ViewModel {

    private LiveData<List<Movie>> favoriteListLiveData;

    public MovieFavoritesViewModel(Repository repository) {
        favoriteListLiveData = repository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteListLiveData() {
        return favoriteListLiveData;
    }
}
