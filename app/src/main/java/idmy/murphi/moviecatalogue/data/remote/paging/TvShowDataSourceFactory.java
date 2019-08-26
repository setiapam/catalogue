package idmy.murphi.moviecatalogue.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import java.util.concurrent.Executor;

import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.remote.api.TvShowService;

public class TvShowDataSourceFactory extends DataSource.Factory<Integer, TvShow> {

    public MutableLiveData<TvShowPageKeyedDataSource> sourceLiveData = new MutableLiveData<>();
    private final TvShowService tvShowService;
    private final Executor networkExecutor;
    private String LANGUAGE;
    private String SEARCH;

    public TvShowDataSourceFactory(TvShowService tvShowService, Executor networkExecutor, String lang, String search) {
        this.tvShowService = tvShowService;
        this.networkExecutor = networkExecutor;
        this.LANGUAGE = lang;
        this.SEARCH = search;
    }


    @NonNull
    @Override
    public DataSource create() {
        TvShowPageKeyedDataSource tvShowDataSource =
                new TvShowPageKeyedDataSource(tvShowService, networkExecutor, LANGUAGE, SEARCH);
        sourceLiveData.postValue(tvShowDataSource);
        return tvShowDataSource;
    }

}
