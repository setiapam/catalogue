package idmy.murphi.moviecatalogue.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import java.util.concurrent.Executor;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.remote.api.MovieService;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    public MutableLiveData<MoviePageKeyedDataSource> sourceLiveData = new MutableLiveData<>();
    private final MovieService movieService;
    private final Executor networkExecutor;
    private String LANGUAGE;
    private String SEARCH;

    public MovieDataSourceFactory(MovieService movieService, Executor networkExecutor, String lang, String search) {
        this.movieService = movieService;
        this.networkExecutor = networkExecutor;
        this.LANGUAGE = lang;
        this.SEARCH = search;
    }

    @NonNull
    @Override
    public DataSource create() {
        MoviePageKeyedDataSource movieDataSource =
                new MoviePageKeyedDataSource(movieService, networkExecutor, LANGUAGE, SEARCH);
        sourceLiveData.postValue(movieDataSource);
        return movieDataSource;
    }
}
