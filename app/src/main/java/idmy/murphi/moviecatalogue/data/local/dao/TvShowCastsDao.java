package idmy.murphi.moviecatalogue.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.TvShowCast;

@Dao
public interface TvShowCastsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllCasts(List<TvShowCast> tvShowCastList);

}
