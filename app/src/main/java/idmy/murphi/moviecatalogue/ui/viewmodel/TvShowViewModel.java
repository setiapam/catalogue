package idmy.murphi.moviecatalogue.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.model.RepoTvShowsResult;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;

public class TvShowViewModel extends ViewModel {
    private LiveData<RepoTvShowsResult> repoTvShowsResult;

    private LiveData<PagedList<TvShow>> pagedList;

    private LiveData<Resource> networkState;

    private MutableLiveData<String> language = new MutableLiveData<>();

    private MutableLiveData<String> search = new MutableLiveData<>();

    private Repository repository;

    public TvShowViewModel(final Repository repository, String lang) {
        this.repository = repository;

        language.setValue(lang);

        repoTvShowsResult = Transformations.map(language, input -> repository.loadTvShowsByLanguage(language.getValue(), search.getValue()));

        pagedList = Transformations.switchMap(repoTvShowsResult, input -> input.data);

        networkState = Transformations.switchMap(repoTvShowsResult, input -> input.resource);
    }

    public LiveData<PagedList<TvShow>> getTvShowsPagedList() {
        return pagedList;
    }

    public LiveData<PagedList<TvShow>> getTvShowsPagedList(String search) {
        this.search.setValue(search);
        repoTvShowsResult = Transformations.map(language, input -> repository.loadTvShowsByLanguage(language.getValue(), this.search.getValue()));
        pagedList = Transformations.switchMap(repoTvShowsResult, input -> input.data);
        networkState = Transformations.switchMap(repoTvShowsResult, input -> input.resource);
        return pagedList;
    }

    public MutableLiveData<String> getLanguage() {
        return language;
    }

    public void setLanguage(String lang) {
        language.setValue(lang);
    }

    public LiveData<Resource> getNetworkState() {
        return networkState;
    }

    public void retry() {
        repoTvShowsResult.getValue().sourceLiveData.getValue().retryCallback.invoke();
    }
}

