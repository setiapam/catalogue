package idmy.murphi.moviecatalogue.ui.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import idmy.murphi.moviecatalogue.data.local.model.Resource;
import idmy.murphi.moviecatalogue.databinding.NetworkStateItemBinding;
import idmy.murphi.moviecatalogue.ui.viewmodel.MovieViewModel;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowViewModel;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    private NetworkStateItemBinding binding;

    private NetworkStateViewHolder(@NonNull NetworkStateItemBinding binding,
                                   final MovieViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;

        binding.retryButton.setOnClickListener(view -> viewModel.retry());
    }

    private NetworkStateViewHolder(@NonNull NetworkStateItemBinding binding,
                                   final TvShowViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;

        binding.retryButton.setOnClickListener(view -> viewModel.retry());
    }

    public static NetworkStateViewHolder create(ViewGroup parent, MovieViewModel viewModel) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NetworkStateItemBinding binding =
                NetworkStateItemBinding.inflate(layoutInflater, parent, false);
        return new NetworkStateViewHolder(binding, viewModel);
    }

    public static NetworkStateViewHolder create(ViewGroup parent, TvShowViewModel viewModel) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NetworkStateItemBinding binding =
                NetworkStateItemBinding.inflate(layoutInflater, parent, false);
        return new NetworkStateViewHolder(binding, viewModel);
    }

    public void bindTo(Resource resource) {
        binding.setResource(resource);
        binding.executePendingBindings();
    }
}
