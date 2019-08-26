package idmy.murphi.moviecatalogue.data.local;

import androidx.lifecycle.LiveData;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MovieCast;
import idmy.murphi.moviecatalogue.data.local.model.MovieDetails;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.local.model.TvShowCast;
import idmy.murphi.moviecatalogue.data.local.model.TvShowDetails;
import idmy.murphi.moviecatalogue.utils.AppExecutors;

public class LocalDataSource {
    private static volatile LocalDataSource sInstance;

    private final Database mDatabase;

    private LocalDataSource(Database database) {
        mDatabase = database;
    }

    public static LocalDataSource getInstance(Database database) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new LocalDataSource(database);
                }
            }
        }
        return sInstance;
    }

    public void saveMovie(Movie movie) {
        mDatabase.movieDao().insertMovie(movie);
        insertMovieCastList(movie.getCreditsResponse().getMovieCast(), movie.getId());
    }

    public void saveTvShow(TvShow tvShow) {
        mDatabase.tvShowDao().insertTvShow(tvShow);
        insertTvShowCastList(tvShow.getCreditsResponse().getTvShowCast(), tvShow.getId());

    }

    private void insertMovieCastList(List<MovieCast> movieCastList, long movieId) {
        for (MovieCast movieCast : movieCastList) {
            movieCast.setMovieId(movieId);
        }
        mDatabase.movieCastsDao().insertAllCasts(movieCastList);
    }

    public LiveData<MovieDetails> getMovie(long movieId) {
        return mDatabase.movieDao().getMovie(movieId);
    }

    public void favoriteMovie(Movie movie) {
        mDatabase.movieDao().favoriteMovie(movie.getId());
    }

    public void unfavoriteMovie(Movie movie) {
        mDatabase.movieDao().unFavoriteMovie(movie.getId());
    }

    private void insertTvShowCastList(List<TvShowCast> tvShowCastList, long tvShowId) {
        for (TvShowCast tvShowCast : tvShowCastList) {
            tvShowCast.setTvShowId(tvShowId);
        }
        mDatabase.tvShowCastsDao().insertAllCasts(tvShowCastList);
    }

    public LiveData<TvShowDetails> getTvShow(long tvShowId) {
        return mDatabase.tvShowDao().getTvShow(tvShowId);
    }

    public void favoriteTvShow(TvShow tvShow) {
        mDatabase.tvShowDao().favoriteTvShow(tvShow.getId());
    }

    public void unfavoriteTvShow(TvShow tvShow) {
        mDatabase.tvShowDao().unFavoriteTvShow(tvShow.getId());
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mDatabase.movieDao().getAllFavoriteMovies();
    }

    public LiveData<List<TvShow>> getAllFavoriteTvShows() {
        return mDatabase.tvShowDao().getAllFavoriteTvShows();
    }
}
