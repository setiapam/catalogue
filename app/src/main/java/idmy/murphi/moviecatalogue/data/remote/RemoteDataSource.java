package idmy.murphi.moviecatalogue.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.RepoMoviesResult;
import idmy.murphi.moviecatalogue.data.local.model.RepoTvShowsResult;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.remote.api.ApiResponse;
import idmy.murphi.moviecatalogue.data.remote.api.MovieService;
import idmy.murphi.moviecatalogue.data.remote.api.TvShowService;
import idmy.murphi.moviecatalogue.data.remote.paging.MovieDataSourceFactory;
import idmy.murphi.moviecatalogue.data.remote.paging.TvShowDataSourceFactory;
import idmy.murphi.moviecatalogue.utils.AppExecutors;

import static idmy.murphi.moviecatalogue.utils.Constants.PAGE_SIZE;

public class RemoteDataSource {

    private final AppExecutors mExecutors;

    private static volatile RemoteDataSource sInstance;

    private final MovieService mMovieService;

    private final TvShowService mTvShowService;

    private RemoteDataSource(MovieService movieService, TvShowService tvShowService,
                             AppExecutors executors) {
        mMovieService = movieService;
        mTvShowService = tvShowService;
        mExecutors = executors;
    }

    public static RemoteDataSource getInstance(MovieService movieService, TvShowService mTvShowService,
                                               AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new RemoteDataSource(movieService, mTvShowService, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<ApiResponse<Movie>> loadMovie(final long movieId) {
        return mMovieService.getMovieDetails(movieId);
    }

    public RepoMoviesResult loadMoviesByLang(String lang, String search) {
        MovieDataSourceFactory sourceFactory =
                new MovieDataSourceFactory(mMovieService, mExecutors.networkIO(), lang, search);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        LiveData<PagedList<Movie>> moviesPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<Resource> networkState = Transformations.switchMap(sourceFactory.sourceLiveData, input -> input.networkState);

        return new RepoMoviesResult(
                moviesPagedList,
                networkState,
                sourceFactory.sourceLiveData
        );
    }

    public LiveData<ApiResponse<TvShow>> loadTvShow(final long tvShowId) {
        return mTvShowService.getTvShowDetails(tvShowId);
    }

    public RepoTvShowsResult loadTvShowsByLang(String lang, String search) {
        TvShowDataSourceFactory sourceFactory =
                new TvShowDataSourceFactory(mTvShowService, mExecutors.networkIO(), lang, search);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        LiveData<PagedList<TvShow>> tvShowsPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<Resource> networkState = Transformations.switchMap(sourceFactory.sourceLiveData, input -> input.networkState);

        return new RepoTvShowsResult(
                tvShowsPagedList,
                networkState,
                sourceFactory.sourceLiveData
        );
    }
}
