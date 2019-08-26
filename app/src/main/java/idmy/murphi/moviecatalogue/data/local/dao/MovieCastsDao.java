package idmy.murphi.moviecatalogue.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.MovieCast;

@Dao
public interface MovieCastsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllCasts(List<MovieCast> movieCastList);

}
