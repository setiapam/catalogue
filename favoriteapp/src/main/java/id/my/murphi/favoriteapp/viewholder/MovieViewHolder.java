package id.my.murphi.favoriteapp.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.my.murphi.favoriteapp.databinding.ItemMovieBinding;
import id.my.murphi.favoriteapp.entity.Movie;


public class MovieViewHolder extends RecyclerView.ViewHolder {


    private final ItemMovieBinding binding;

    private MovieViewHolder(@NonNull ItemMovieBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bindTo(final Movie movie) {
        binding.setMovie(movie);

        binding.executePendingBindings();
    }

    public static MovieViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding binding =
                ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new MovieViewHolder(binding);
    }

}
