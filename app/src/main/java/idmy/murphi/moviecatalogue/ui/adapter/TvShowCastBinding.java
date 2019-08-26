package idmy.murphi.moviecatalogue.ui.adapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import idmy.murphi.moviecatalogue.data.local.model.TvShowCast;

public class TvShowCastBinding {

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<TvShowCast> items) {
        TvShowCastAdapter adapter = (TvShowCastAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(items);
        }
    }
}
