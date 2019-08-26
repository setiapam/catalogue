package idmy.murphi.moviecatalogue.data.remote;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.mockito.junit.MockitoJUnitRunner;

import idmy.murphi.moviecatalogue.data.local.Database;
import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.remote.api.ApiResponse;
import idmy.murphi.moviecatalogue.data.remote.api.MovieApiClient;
import idmy.murphi.moviecatalogue.data.remote.api.MovieService;
import idmy.murphi.moviecatalogue.data.remote.api.TvShowService;
import idmy.murphi.moviecatalogue.utils.AppExecutors;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RemoteDataSourceTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private RemoteDataSource remoteDataSource;
    @Mock
    private AppExecutors mExecutors;
    @Mock
    private MovieService mMovieService;
    @Mock
    private TvShowService mTvShowService;
    @Mock
    private LiveData<ApiResponse<Movie>> apiResponseLiveData;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        remoteDataSource = RemoteDataSource.getInstance(mMovieService,mTvShowService,mExecutors);
    }

    @Test
    public void loadMovie() {
//        Mockito.when(remoteDataSource.loadMovie(429203)).thenReturn(apiResponseLiveData);
        Mockito.verify(mMovieService).getMovieDetails(Long.getLong("429203"));
    }

    @Test
    public void loadMoviesByLang() {
        assertNotNull(remoteDataSource.loadMoviesByLang("en",""));
    }

    @Test
    public void loadTvShow() {
    }

    @Test
    public void loadTvShowsByLang() {
    }
}