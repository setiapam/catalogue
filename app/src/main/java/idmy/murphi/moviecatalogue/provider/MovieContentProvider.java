package idmy.murphi.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import idmy.murphi.moviecatalogue.data.local.Database;
import idmy.murphi.moviecatalogue.data.local.model.Movie;

public class MovieContentProvider extends ContentProvider {
    public static final String AUTHORITY = "idmy.murphi.moviecatalogue.provider";
    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY + "/" + Movie.TABLE_NAME);
    private static final int CODE_MOVIE_FAVORITE = 1;
    private final UriMatcher MATCHER;
    private Database database;

    public MovieContentProvider() {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME, CODE_MOVIE_FAVORITE);
    }

    @Override
    public boolean onCreate() {
        database = Database.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_FAVORITE) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            Cursor cursor = database.movieDao().selectIsFavorite();
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        if (MATCHER.match(uri) == CODE_MOVIE_FAVORITE) {
            return "vnd.android.cursor.fav/" + AUTHORITY + "." + Movie.TABLE_NAME;
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
