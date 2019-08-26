package idmy.murphi.moviecatalogue.data.local.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.local.model.TvShowDetails;

@Dao
public interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(TvShow tvShow);

    @Transaction
    @Query("SELECT * FROM tv_show WHERE tv_show.id= :tvShowId")
    LiveData<TvShowDetails> getTvShow(long tvShowId);

    @Query("SELECT * FROM tv_show WHERE is_favorite = 1")
    LiveData<List<TvShow>> getAllFavoriteTvShows();

    @Query("UPDATE tv_show SET is_favorite = 1 WHERE tv_show.id = :tvShowId")
    void favoriteTvShow(long tvShowId);

    @Query("UPDATE tv_show SET is_favorite = 0 WHERE tv_show.id = :tvShowId")
    void unFavoriteTvShow(long tvShowId);

    @Query("SELECT * FROM tv_show WHERE is_favorite = 1")
    Cursor selectIsFavorite();
}
