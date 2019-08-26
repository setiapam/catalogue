package idmy.murphi.moviecatalogue.data.local.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MovieDetails;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.id= :movieId")
    LiveData<MovieDetails> getMovie(long movieId);

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("UPDATE movie SET is_favorite = 1 WHERE movie.id = :movieId")
    void favoriteMovie(long movieId);

    @Query("UPDATE movie SET is_favorite = 0 WHERE movie.id = :movieId")
    void unFavoriteMovie(long movieId);

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    Cursor selectIsFavorite();
}
