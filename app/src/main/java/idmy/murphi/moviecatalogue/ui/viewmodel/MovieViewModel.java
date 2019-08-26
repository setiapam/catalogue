package idmy.murphi.moviecatalogue.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.RepoMoviesResult;
import idmy.murphi.moviecatalogue.data.local.model.Resource;

public class MovieViewModel extends ViewModel {
    private LiveData<RepoMoviesResult> repoMoviesResult;

    private LiveData<PagedList<Movie>> pagedList;

    private LiveData<Resource> networkState;

    private MutableLiveData<String> language = new MutableLiveData<>();

    private MutableLiveData<String> search = new MutableLiveData<>();

    private Repository repository;

    public MovieViewModel(final Repository repository, String lang) {
        this.repository = repository;

        language.setValue(lang);

        repoMoviesResult = Transformations.map(language, input -> repository.loadMoviesByLanguage(language.getValue(), search.getValue()));

        pagedList = Transformations.switchMap(repoMoviesResult, input -> input.data);

        networkState = Transformations.switchMap(repoMoviesResult, input -> input.resource);
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList() {
        return pagedList;
    }

    public LiveData<Resource> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<String> getLanguage() {
        return language;
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList(String search) {
        this.search.setValue(search);
        repoMoviesResult = Transformations.map(language, input -> repository.loadMoviesByLanguage(language.getValue(), this.search.getValue()));
        pagedList = Transformations.switchMap(repoMoviesResult, input -> input.data);
        networkState = Transformations.switchMap(repoMoviesResult, input -> input.resource);
        return pagedList;
    }

    public void retry() {
        repoMoviesResult.getValue().sourceLiveData.getValue().retryCallback.invoke();
    }
}
