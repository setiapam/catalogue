package idmy.murphi.moviecatalogue.ui.adapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.MovieCast;

public class MovieCastBinding {

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<MovieCast> items) {
        MovieCastAdapter adapter = (MovieCastAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(items);
        }
    }
}
