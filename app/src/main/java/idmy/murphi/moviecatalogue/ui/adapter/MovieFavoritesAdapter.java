package idmy.murphi.moviecatalogue.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.ui.viewholder.MovieViewHolder;


public class MovieFavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> mMoviesList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MovieViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);
        ((MovieViewHolder) holder).bindTo(movie);
    }

    @Override
    public int getItemCount() {
        return mMoviesList != null ? mMoviesList.size() : 0;
    }

    public void submitList(List<Movie> movies) {
        mMoviesList = movies;
        notifyDataSetChanged();
    }
}
