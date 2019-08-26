package idmy.murphi.moviecatalogue.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.LocalDataSource;
import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MovieDetails;
import idmy.murphi.moviecatalogue.data.local.model.RepoMoviesResult;
import idmy.murphi.moviecatalogue.data.local.model.RepoTvShowsResult;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.local.model.TvShowDetails;
import idmy.murphi.moviecatalogue.data.remote.RemoteDataSource;
import idmy.murphi.moviecatalogue.data.remote.api.ApiResponse;
import idmy.murphi.moviecatalogue.utils.AppExecutors;
import idmy.murphi.moviecatalogue.widget.FavoriteWidget;

public class Repository implements DataSource {

    private static volatile Repository sInstance;

    private final LocalDataSource mLocalDataSource;

    private final RemoteDataSource mRemoteDataSource;

    private final AppExecutors mExecutors;

    private Repository(LocalDataSource localDataSource,
                       RemoteDataSource remoteDataSource,
                       AppExecutors executors) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mExecutors = executors;
    }

    public static Repository getInstance(LocalDataSource localDataSource,
                                         RemoteDataSource remoteDataSource,
                                         AppExecutors executors) {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository(localDataSource, remoteDataSource, executors);
                }
            }
        }
        return sInstance;
    }


    @Override
    public RepoMoviesResult loadMoviesByLanguage(String lang, String search) {
        return mRemoteDataSource.loadMoviesByLang(lang, search);
    }

    @Override
    public LiveData<Resource<MovieDetails>> loadMovie(long movieId) {
        return new NetworkBoundResource<MovieDetails, Movie>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull Movie item) {
                mLocalDataSource.saveMovie(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable MovieDetails data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<MovieDetails> loadFromDb() {
                return mLocalDataSource.getMovie(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                return mRemoteDataSource.loadMovie(movieId);
            }

            @Override
            protected void onFetchFailed() {
            }
        }.getAsLiveData();
    }

    @Override
    public void favoriteMovie(Movie movie, Context mContext) {
        mExecutors.diskIO().execute(() -> {
            mLocalDataSource.favoriteMovie(movie);
            FavoriteWidget.sendRefreshBroadcast(mContext);
        });
    }

    @Override
    public void unfavoriteMovie(Movie movie, Context mContext) {
        mExecutors.diskIO().execute(() -> {
            mLocalDataSource.unfavoriteMovie(movie);
            FavoriteWidget.sendRefreshBroadcast(mContext);
        });
    }

    @Override
    public RepoTvShowsResult loadTvShowsByLanguage(String lang, String search) {
        return mRemoteDataSource.loadTvShowsByLang(lang, search);
    }

    @Override
    public LiveData<Resource<TvShowDetails>> loadTvShow(long tvShowId) {
        return new NetworkBoundResource<TvShowDetails, TvShow>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull TvShow item) {
                mLocalDataSource.saveTvShow(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable TvShowDetails data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<TvShowDetails> loadFromDb() {
                return mLocalDataSource.getTvShow(tvShowId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TvShow>> createCall() {
                return mRemoteDataSource.loadTvShow(tvShowId);
            }

            @Override
            protected void onFetchFailed() {

            }
        }.getAsLiveData();
    }

    @Override
    public void favoriteTvShow(TvShow tvShow) {
        mExecutors.diskIO().execute(() -> mLocalDataSource.favoriteTvShow(tvShow));
    }

    @Override
    public void unfavoriteTvShow(TvShow tvShow) {
        mExecutors.diskIO().execute(() -> mLocalDataSource.unfavoriteTvShow(tvShow));
    }

    @Override
    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mLocalDataSource.getAllFavoriteMovies();
    }

    @Override
    public LiveData<List<TvShow>> getAllFavoriteTvShows() {
        return mLocalDataSource.getAllFavoriteTvShows();
    }


}