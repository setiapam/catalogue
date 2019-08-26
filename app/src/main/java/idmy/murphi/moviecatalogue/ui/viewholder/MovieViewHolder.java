package idmy.murphi.moviecatalogue.ui.viewholder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.databinding.ItemMovieBinding;
import idmy.murphi.moviecatalogue.ui.activities.DetailMovieActivity;


public class MovieViewHolder extends RecyclerView.ViewHolder {


    private final ItemMovieBinding binding;

    private MovieViewHolder(@NonNull ItemMovieBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bindTo(final Movie movie) {
        binding.setMovie(movie);
        binding.getRoot().setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, movie.getId());
            view.getContext().startActivity(intent);
        });

        binding.executePendingBindings();
    }

    public static MovieViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding binding =
                ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new MovieViewHolder(binding);
    }

}
