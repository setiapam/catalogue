package idmy.murphi.moviecatalogue.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.data.local.model.TvShow;
import idmy.murphi.moviecatalogue.ui.viewholder.NetworkStateViewHolder;
import idmy.murphi.moviecatalogue.ui.viewholder.TvShowViewHolder;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowViewModel;

public class TvShowsAdapter extends PagedListAdapter<TvShow, RecyclerView.ViewHolder> {
    private TvShowViewModel tvViewModel;

    private Resource resource = null;

    public TvShowsAdapter(TvShowViewModel viewModel) {
        super(TV_COMPARATOR);
        tvViewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_tv:
                return TvShowViewHolder.create(parent);
            case R.layout.network_state_item:
                return NetworkStateViewHolder.create(parent, tvViewModel);
            default:
                throw new IllegalArgumentException("unknown view type " + viewType);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_tv:
                ((TvShowViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateViewHolder) holder).bindTo(resource);
                break;
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.item_tv;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    private boolean hasExtraRow() {
        return resource != null && resource.status != Resource.Status.SUCCESS;
    }

    public void setNetworkState(Resource resource) {
        Resource previousState = this.resource;
        boolean hadExtraRow = hasExtraRow();
        this.resource = resource;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && !previousState.equals(resource)) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private static DiffUtil.ItemCallback<TvShow> TV_COMPARATOR = new DiffUtil.ItemCallback<TvShow>() {
        @Override
        public boolean areItemsTheSame(@NonNull TvShow oldItem, @NonNull TvShow newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TvShow oldItem, @NonNull TvShow newItem) {
            return oldItem.equals(newItem);
        }
    };
}
