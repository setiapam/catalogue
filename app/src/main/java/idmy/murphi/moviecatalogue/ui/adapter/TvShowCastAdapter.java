package idmy.murphi.moviecatalogue.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.TvShowCast;
import idmy.murphi.moviecatalogue.ui.viewholder.TvShowCastViewHolder;

public class TvShowCastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TvShowCast> castList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TvShowCastViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TvShowCast cast = castList.get(position);
        ((TvShowCastViewHolder) holder).bindTo(cast);
    }

    @Override
    public int getItemCount() {
        return castList != null ? castList.size() : 0;
    }

    public void submitList(List<TvShowCast> casts) {
        castList = casts;
        notifyDataSetChanged();
    }
}

