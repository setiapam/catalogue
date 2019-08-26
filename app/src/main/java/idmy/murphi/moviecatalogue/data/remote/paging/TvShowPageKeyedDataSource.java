package idmy.murphi.moviecatalogue.data.remote.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.local.model.TvShowsResponse;
import idmy.murphi.moviecatalogue.data.remote.api.TvShowService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowPageKeyedDataSource extends PageKeyedDataSource<Integer, TvShow> {

    private static final String TAG = TvShowPageKeyedDataSource.class.getSimpleName();
    private static final int FIRST_PAGE = 1;
    public MutableLiveData<Resource> networkState = new MutableLiveData<>();
    private final TvShowService tvShowService;
    private final Executor networkExecutor;
    public RetryCallback retryCallback = null;
    private String LANGUAGE;
    private String SEARCH;
    private boolean ADULT = false;

    TvShowPageKeyedDataSource(TvShowService tvShowService, Executor networkExecutor, String lang, String search) {
        this.tvShowService = tvShowService;
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
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, TvShow> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(Resource.loading(null));
        Call<TvShowsResponse> request;
        if (!SEARCH.equals("")) {
            request = tvShowService.searchTvShows(LANGUAGE, FIRST_PAGE, SEARCH, ADULT);
        } else {
            request = tvShowService.getTvShows(LANGUAGE, FIRST_PAGE);
        }
        try {
            Response<TvShowsResponse> response = request.execute();
            TvShowsResponse data = response.body();
            List<TvShow> tvShowList =
                    data != null ? data.getTvShows() : Collections.emptyList();
            retryCallback = null;
            networkState.postValue(Resource.success(null));
            callback.onResult(tvShowList, null, FIRST_PAGE + 1);
        } catch (IOException e) {
            retryCallback = () -> networkExecutor.execute(() -> loadInitial(params, callback));
            networkState.postValue(Resource.error(e.getMessage(), null));
        }
    }


    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, final @NonNull LoadCallback<Integer, TvShow> callback) {
        Log.i(TAG, "Loading page " + params.key);
        networkState.postValue(Resource.loading(null));
        Call<TvShowsResponse> request;
        if (!SEARCH.equals("")) {
            request = tvShowService.searchTvShows(LANGUAGE, params.key, SEARCH, ADULT);
        } else {
            request = tvShowService.getTvShows(LANGUAGE, params.key);
        }

        request.enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowsResponse> call, @NonNull Response<TvShowsResponse> response) {
                if (response.isSuccessful()) {
                    TvShowsResponse data = response.body();
                    List<TvShow> movieList =
                            data != null ? data.getTvShows() : Collections.emptyList();

                    retryCallback = null;
                    callback.onResult(movieList, params.key + 1);
                    networkState.postValue(Resource.success(null));
                } else {
                    retryCallback = () -> loadAfter(params, callback);
                    networkState.postValue(Resource.error("error code: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowsResponse> call, @NonNull Throwable t) {
                retryCallback = () -> networkExecutor.execute(() -> loadAfter(params, callback));
                networkState.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TvShow> callback) {

    }

    public interface RetryCallback {
        void invoke();
    }
}
