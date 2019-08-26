package idmy.murphi.moviecatalogue.data.local.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import idmy.murphi.moviecatalogue.data.remote.paging.MoviePageKeyedDataSource;

public class RepoMoviesResult {
    public LiveData<PagedList<Movie>> data;
    public LiveData<Resource> resource;
    public MutableLiveData<MoviePageKeyedDataSource> sourceLiveData;

    public RepoMoviesResult(LiveData<PagedList<Movie>> data,
                            LiveData<Resource> resource,
                            MutableLiveData<MoviePageKeyedDataSource> sourceLiveData) {
        this.data = data;
        this.resource = resource;
        this.sourceLiveData = sourceLiveData;
    }

    public RepoMoviesResult(LiveData<PagedList<Movie>> data,
                            LiveData<Resource> resource) {
        this.data = data;
        this.resource = resource;
    }
}
