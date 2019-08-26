package id.my.murphi.favoriteapp.adapter;

import android.database.Cursor;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.my.murphi.favoriteapp.entity.Movie;
import id.my.murphi.favoriteapp.viewholder.MovieViewHolder;


public class MovieFavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Cursor cursor;

    public MovieFavoritesAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MovieViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        Movie movie = new Movie();
        movie.setTitle(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_POSTER)));
        ((MovieViewHolder) holder).bindTo(movie);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

}
