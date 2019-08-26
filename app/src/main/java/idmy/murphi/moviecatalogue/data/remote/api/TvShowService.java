package idmy.murphi.moviecatalogue.data.remote.api;


import androidx.lifecycle.LiveData;

import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.local.model.TvShowsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static idmy.murphi.moviecatalogue.utils.Constants.ADULT_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.LANGUAGE_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.PAGE_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.QUERY_REQUEST_PARAM;

public interface TvShowService {

    @GET("tv/popular")
    Call<TvShowsResponse> getTvShows(@Query(LANGUAGE_REQUEST_PARAM) String language,
                                     @Query(PAGE_REQUEST_PARAM) int page);

    @GET("tv/{id}?append_to_response=credits")
    LiveData<ApiResponse<TvShow>> getTvShowDetails(@Path("id") long id);

    @GET("search/tv")
    Call<TvShowsResponse> searchTvShows(@Query(LANGUAGE_REQUEST_PARAM) String language,
                                        @Query(PAGE_REQUEST_PARAM) int page,
                                        @Query(QUERY_REQUEST_PARAM) String query,
                                        @Query(ADULT_REQUEST_PARAM) boolean adult);
}
