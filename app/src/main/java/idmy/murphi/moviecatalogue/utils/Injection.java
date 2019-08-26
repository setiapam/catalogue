package idmy.murphi.moviecatalogue.utils;

import android.content.Context;

import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.Database;
import idmy.murphi.moviecatalogue.data.local.LocalDataSource;
import idmy.murphi.moviecatalogue.data.remote.RemoteDataSource;
import idmy.murphi.moviecatalogue.data.remote.api.MovieApiClient;
import idmy.murphi.moviecatalogue.data.remote.api.MovieService;
import idmy.murphi.moviecatalogue.data.remote.api.TvShowApiClient;
import idmy.murphi.moviecatalogue.data.remote.api.TvShowService;

public class Injection {

    private static RemoteDataSource provideRemoteDataSource() {
        MovieService movieService = MovieApiClient.getClient();
        TvShowService tvShowService = TvShowApiClient.getClient();
        AppExecutors executors = AppExecutors.getInstance();
        return RemoteDataSource.getInstance(movieService, tvShowService, executors);
    }

    private static LocalDataSource provideLocalDataSource(Context context) {
        Database database = Database.getInstance(context.getApplicationContext());
        return LocalDataSource.getInstance(database);
    }

    private static Repository provideRepository(Context context) {
        RemoteDataSource remoteDataSource = provideRemoteDataSource();
        LocalDataSource localDataSource = provideLocalDataSource(context);
        AppExecutors executors = AppExecutors.getInstance();
        return Repository.getInstance(
                localDataSource,
                remoteDataSource,
                executors);
    }

    public static ViewModelFactory provideViewModelFactory(Context context, String lang) {
        Repository repository = provideRepository(context);
        return ViewModelFactory.getInstance(repository, lang);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        Repository repository = provideRepository(context);
        return ViewModelFactory.getInstance(repository);
    }
}
