package idmy.murphi.moviecatalogue.data.remote.api;


import androidx.lifecycle.LiveData;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static idmy.murphi.moviecatalogue.utils.Constants.ADULT_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.END_DATE_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.LANGUAGE_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.PAGE_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.QUERY_REQUEST_PARAM;
import static idmy.murphi.moviecatalogue.utils.Constants.START_DATE_REQUEST_PARAM;

public interface MovieService {

    @GET("movie/popular")
    Call<MoviesResponse> getMovies(@Query(LANGUAGE_REQUEST_PARAM) String language,
                                   @Query(PAGE_REQUEST_PARAM) int page);

    @GET("movie/{id}?append_to_response=credits")
    LiveData<ApiResponse<Movie>> getMovieDetails(@Path("id") long id);

    @GET("search/movie")
    Call<MoviesResponse> searchMovies(@Query(LANGUAGE_REQUEST_PARAM) String language,
                                      @Query(PAGE_REQUEST_PARAM) int page,
                                      @Query(QUERY_REQUEST_PARAM) String query,
                                      @Query(ADULT_REQUEST_PARAM) boolean adult);

    @GET("discover/movie")
    Call<MoviesResponse> releasedToday(@Query(START_DATE_REQUEST_PARAM) String start,
                                       @Query(END_DATE_REQUEST_PARAM) String end);
}
