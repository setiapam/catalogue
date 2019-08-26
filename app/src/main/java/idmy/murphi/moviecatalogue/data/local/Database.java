package idmy.murphi.moviecatalogue.data.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import idmy.murphi.moviecatalogue.data.local.dao.MovieCastsDao;
import idmy.murphi.moviecatalogue.data.local.dao.MovieDao;
import idmy.murphi.moviecatalogue.data.local.dao.TvShowCastsDao;
import idmy.murphi.moviecatalogue.data.local.dao.TvShowDao;
import idmy.murphi.moviecatalogue.data.local.model.Converters;
import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MovieCast;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.data.local.model.TvShowCast;

import static idmy.murphi.moviecatalogue.utils.Constants.DATABASE_NAME;

@androidx.room.Database(
        entities = {Movie.class, MovieCast.class, TvShow.class, TvShowCast.class},
        version = 1,
        exportSchema = false)
@TypeConverters(Converters.class)
public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public abstract MovieDao movieDao();

    abstract TvShowDao tvShowDao();

    abstract MovieCastsDao movieCastsDao();

    abstract TvShowCastsDao tvShowCastsDao();

    private static final Object sLock = new Object();

    public static Database getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context);
            }
            return INSTANCE;
        }
    }

    private static Database buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                Database.class,
                DATABASE_NAME)
                .build();
    }

}
