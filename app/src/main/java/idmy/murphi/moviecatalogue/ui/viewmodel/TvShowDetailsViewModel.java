package idmy.murphi.moviecatalogue.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.local.model.TvShowDetails;
import idmy.murphi.moviecatalogue.utils.SnackbarMessage;

public class TvShowDetailsViewModel extends ViewModel {

    private final Repository repository;

    private LiveData<Resource<TvShowDetails>> result;

    private MutableLiveData<Long> tvShowIdLiveData = new MutableLiveData<>();

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private boolean isFavorite;

    public TvShowDetailsViewModel(final Repository repository) {
        this.repository = repository;
    }

    public void init(long tvShowId) {
        if (result != null) {
            return;
        }

        result = Transformations.switchMap(tvShowIdLiveData,
                repository::loadTvShow);

        setMovieIdLiveData(tvShowId);
    }

    public LiveData<Resource<TvShowDetails>> getResult() {
        return result;
    }

    public SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private void setMovieIdLiveData(long tvShowId) {
        tvShowIdLiveData.setValue(tvShowId);
    }

    public void retry(long tvShowId) {
        setMovieIdLiveData(tvShowId);
    }

    public void onFavoriteClicked() {
        TvShowDetails tvShowDetails = result.getValue().data;
        if (!isFavorite) {
            if (tvShowDetails != null) {
                repository.favoriteTvShow(tvShowDetails.tvShow);
            }
            isFavorite = true;
            showSnackbarMessage(R.string.tv_added_successfully);
        } else {
            if (tvShowDetails != null) {
                repository.unfavoriteTvShow(tvShowDetails.tvShow);
            }
            isFavorite = false;
            showSnackbarMessage(R.string.tv_removed_successfully);
        }
    }

    private void showSnackbarMessage(Integer message) {
        mSnackbarText.setValue(message);
    }
}
