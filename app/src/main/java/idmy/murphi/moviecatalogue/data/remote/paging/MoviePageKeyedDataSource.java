package idmy.murphi.moviecatalogue.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MoviesResponse;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.remote.api.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePageKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final int FIRST_PAGE = 1;
    public MutableLiveData<Resource> networkState = new MutableLiveData<>();
    private final MovieService moviesService;
    private final Executor networkExecutor;
    public RetryCallback retryCallback = null;
    private String LANGUAGE;
    private String SEARCH;
    private boolean ADULT = false;

    MoviePageKeyedDataSource(MovieService moviesService, Executor networkExecutor, String lang, String search) {
        this.moviesService = moviesService;
        this.networkExecutor = networkExecutor;
        if (lang.equalsIgnoreCase("in")) {
            this.LANGUAGE = "id";
        } else {
            this.LANGUAGE = lang.toLowerCase();
        }
        if (search != null) {
            this.SEARCH = search;
        } else {
            this.SEARCH = "";
        }

    }

    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        networkState.postValue(Resource.loading(null));
        Call<MoviesResponse> request;
        if (!SEARCH.equals("")) {
            request = moviesService.searchMovies(LANGUAGE, FIRST_PAGE, SEARCH, ADULT);
        } else {
            request = moviesService.getMovies(LANGUAGE, FIRST_PAGE);
        }
        try {
            Response<MoviesResponse> response = request.execute();
            MoviesResponse data = response.body();
            List<Movie> movieList =
                    data != null ? data.getMovies() : Collections.emptyList();
            retryCallback = null;
            networkState.postValue(Resource.success(null));
            callback.onResult(movieList, null, FIRST_PAGE + 1);
        } catch (IOException e) {
            retryCallback = () -> networkExecutor.execute(() -> loadInitial(params, callback));
            networkState.postValue(Resource.error(e.getMessage(), null));
        }
    }


    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, final @NonNull LoadCallback<Integer, Movie> callback) {
        networkState.postValue(Resource.loading(null));
        Call<MoviesResponse> request;
        if (!SEARCH.equals("")) {
            request = moviesService.searchMovies(LANGUAGE, params.key, SEARCH, ADULT);
        } else {
            request = moviesService.getMovies(LANGUAGE, params.key);
        }

        request.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse data = response.body();
                    List<Movie> movieList =
                            data != null ? data.getMovies() : Collections.emptyList();

                    retryCallback = null;
                    callback.onResult(movieList, params.key + 1);
                    networkState.postValue(Resource.success(null));
                } else {
                    retryCallback = () -> loadAfter(params, callback);
                    networkState.postValue(Resource.error("error code: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                retryCallback = () -> networkExecutor.execute(() -> loadAfter(params, callback));
                networkState.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    public interface RetryCallback {
        void invoke();
    }
}
