package idmy.murphi.moviecatalogue.ui.viewholder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.databinding.ItemTvBinding;
import idmy.murphi.moviecatalogue.ui.activities.DetailTvShowActivity;


public class TvShowViewHolder extends RecyclerView.ViewHolder {


    private final ItemTvBinding binding;

    private TvShowViewHolder(@NonNull ItemTvBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bindTo(final TvShow tvShow) {
        binding.setTv(tvShow);
        binding.getRoot().setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailTvShowActivity.class);
            intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW_ID, tvShow.getId());
            view.getContext().startActivity(intent);
        });

        binding.executePendingBindings();
    }

    public static TvShowViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTvBinding binding =
                ItemTvBinding.inflate(layoutInflater, parent, false);
        return new TvShowViewHolder(binding);
    }

}
