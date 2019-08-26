package idmy.murphi.moviecatalogue.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MovieDetails;
import idmy.murphi.moviecatalogue.data.local.model.RepoMoviesResult;
import idmy.murphi.moviecatalogue.data.local.model.RepoTvShowsResult;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.local.model.TvShowDetails;

public interface DataSource {

    RepoMoviesResult loadMoviesByLanguage(String lang, String search);

    LiveData<Resource<MovieDetails>> loadMovie(long movieId);

    void favoriteMovie(Movie movie, Context mContext);

    void unfavoriteMovie(Movie movie, Context mContext);

    LiveData<List<Movie>> getAllFavoriteMovies();


    RepoTvShowsResult loadTvShowsByLanguage(String lang, String search);

    LiveData<Resource<TvShowDetails>> loadTvShow(long tvShowId);

    void favoriteTvShow(TvShow tvShow);

    void unfavoriteTvShow(TvShow tvShow);

    LiveData<List<TvShow>> getAllFavoriteTvShows();
}
