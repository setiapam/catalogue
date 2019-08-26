package idmy.murphi.moviecatalogue.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.MovieCast;
import idmy.murphi.moviecatalogue.ui.viewholder.MovieCastViewHolder;

public class MovieCastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieCast> castList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MovieCastViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieCast cast = castList.get(position);
        ((MovieCastViewHolder) holder).bindTo(cast);
    }

    @Override
    public int getItemCount() {
        return castList != null ? castList.size() : 0;
    }

    public void submitList(List<MovieCast> casts) {
        castList = casts;
        notifyDataSetChanged();
    }
}

