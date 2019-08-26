package idmy.murphi.moviecatalogue.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;

public class TvShowFavoritesViewModel extends ViewModel {

    private LiveData<List<TvShow>> favoriteListLiveData;

    public TvShowFavoritesViewModel(Repository repository) {
        favoriteListLiveData = repository.getAllFavoriteTvShows();
    }

    public LiveData<List<TvShow>> getFavoriteListLiveData() {
        return favoriteListLiveData;
    }
}
