package idmy.murphi.moviecatalogue.ui.viewmodel;


import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.jraska.livedata.TestObserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import idmy.murphi.moviecatalogue.data.Repository;
import idmy.murphi.moviecatalogue.data.local.Database;
import idmy.murphi.moviecatalogue.data.local.LocalDataSource;
import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MoviesResponse;
import idmy.murphi.moviecatalogue.data.local.model.RepoMoviesResult;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.remote.RemoteDataSource;
import idmy.murphi.moviecatalogue.data.remote.api.MovieService;
import idmy.murphi.moviecatalogue.data.remote.api.TvShowService;
import idmy.murphi.moviecatalogue.utils.AppExecutors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MovieViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    @Mock
    private AppExecutors mExecutors;
    @Mock
    private MovieService movieService;
    @Mock
    private TvShowService tvShowService;
    @Mock
    private Database database;

    private static String LANGUAGE = "en";
    private static String SEARCH = "en";
    private MovieViewModel movieViewModel;
    private LocalDataSource mLocalDataSource;
    @Mock
    private RemoteDataSource mRemoteDataSource;
    private RepoMoviesResult repoMoviesResult;
    private LiveData<RepoMoviesResult> repoMoviesResult2;
    private LiveData<PagedList<Movie>> pagedList;
    private LiveData<PagedList<Movie>> pagedListLiveData2;
    private MutableLiveData<String> language = new MutableLiveData<>();
    private MutableLiveData<String> search = new MutableLiveData<>();
    public MutableLiveData<Resource> networkState = new MutableLiveData<>();
    @Mock
    private Repository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        mRemoteDataSource = RemoteDataSource.getInstance(movieService,tvShowService,mExecutors);
        mLocalDataSource = mLocalDataSource.getInstance(database);
//        Repository repository = Repository.getInstance(mLocalDataSource, mRemoteDataSource, mExecutors);

        movieViewModel = new MovieViewModel(repository, LANGUAGE);
    }

    @Test
    public void getMoviesPagedList() {
        LiveData<PagedList<Movie>> pagedListLiveData = movieViewModel.getMoviesPagedList();

        assertNotNull(pagedListLiveData);
        assertEquals(20,pagedListLiveData.getValue().size());

    }

    @Test
    public void getNetworkState() {
        assertNotNull(movieViewModel.getNetworkState());
    }

    @Test
    public void getLanguage() {
        assertNotNull(movieViewModel.getLanguage());
    }

    @Test
    public void getMoviesPagedListSearch() {
        assertNotNull(movieViewModel.getMoviesPagedList("king"));
    }
}