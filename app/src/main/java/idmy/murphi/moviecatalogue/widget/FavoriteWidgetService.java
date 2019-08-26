package idmy.murphi.moviecatalogue.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.utils.Constants;

import static idmy.murphi.moviecatalogue.provider.MovieContentProvider.URI_MOVIE;

public class FavoriteWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mAppWidgetId;
    private Cursor cursor;

    StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private void initCursor() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        String[] projection = {Movie.COLUMN_TITLE, Movie.COLUMN_BACKDROP};
        cursor = mContext.getContentResolver().query(URI_MOVIE, projection, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        initCursor();
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (i == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(i)) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favorite_widget_item);
        cursor.moveToPosition(i);
        rv.setTextViewText(R.id.widget_title, cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)));
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(Constants.BACKDROP_URL + cursor.getString(cursor.getColumnIndex(Movie.COLUMN_BACKDROP)))
                    .submit(512, 512)
                    .get();

            rv.setImageViewBitmap(R.id.widget_backdrop, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return cursor.moveToPosition(i) ? cursor.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
