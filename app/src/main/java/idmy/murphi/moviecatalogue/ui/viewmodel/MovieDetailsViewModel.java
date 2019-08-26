package idmy.murphi.moviecatalogue.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.model.MovieDetails;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.utils.SnackbarMessage;

public class MovieDetailsViewModel extends ViewModel {

    private final Repository repository;

    private LiveData<Resource<MovieDetails>> result;

    private MutableLiveData<Long> movieIdLiveData = new MutableLiveData<>();

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private boolean isFavorite;

    public MovieDetailsViewModel(final Repository repository) {
        this.repository = repository;
    }

    public void init(long movieId) {
        if (result != null) {
            return;
        }

        result = Transformations.switchMap(movieIdLiveData,
                repository::loadMovie);

        setMovieIdLiveData(movieId);
    }

    public LiveData<Resource<MovieDetails>> getResult() {
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

    private void setMovieIdLiveData(long movieId) {
        movieIdLiveData.setValue(movieId);
    }

    public void retry(long movieId) {
        setMovieIdLiveData(movieId);
    }

    public void onFavoriteClicked(Context mContext) {
        MovieDetails movieDetails = Objects.requireNonNull(result.getValue()).data;
        if (!isFavorite) {
            assert movieDetails != null;
            repository.favoriteMovie(movieDetails.movie, mContext);
            isFavorite = true;
            showSnackbarMessage(R.string.movie_added_successfully);
        } else {
            assert movieDetails != null;
            repository.unfavoriteMovie(movieDetails.movie, mContext);
            isFavorite = false;
            showSnackbarMessage(R.string.movie_removed_successfully);
        }
    }

    private void showSnackbarMessage(Integer message) {
        mSnackbarText.setValue(message);
    }
}
