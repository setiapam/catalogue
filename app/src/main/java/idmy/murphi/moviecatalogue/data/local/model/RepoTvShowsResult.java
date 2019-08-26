package idmy.murphi.moviecatalogue.data.local.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import idmy.murphi.moviecatalogue.data.remote.paging.TvShowPageKeyedDataSource;

public class RepoTvShowsResult {
    public LiveData<PagedList<TvShow>> data;
    public LiveData<Resource> resource;
    public MutableLiveData<TvShowPageKeyedDataSource> sourceLiveData;

    public RepoTvShowsResult(LiveData<PagedList<TvShow>> data,
                             LiveData<Resource> resource,
                             MutableLiveData<TvShowPageKeyedDataSource> sourceLiveData) {
        this.data = data;
        this.resource = resource;
        this.sourceLiveData = sourceLiveData;
    }
}
