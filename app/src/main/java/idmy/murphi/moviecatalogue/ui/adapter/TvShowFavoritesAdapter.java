package idmy.murphi.moviecatalogue.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.ui.viewholder.TvShowViewHolder;


public class TvShowFavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TvShow> tvShowList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TvShowViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TvShow tvShow = tvShowList.get(position);
        ((TvShowViewHolder) holder).bindTo(tvShow);
    }

    @Override
    public int getItemCount() {
        return tvShowList != null ? tvShowList.size() : 0;
    }

    public void submitList(List<TvShow> tvShows) {
        tvShowList = tvShows;
        notifyDataSetChanged();
    }
}
