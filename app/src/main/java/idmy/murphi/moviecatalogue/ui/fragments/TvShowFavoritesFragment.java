package idmy.murphi.moviecatalogue.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.databinding.FragmentFavoriteTvshowsBinding;
import idmy.murphi.moviecatalogue.ui.adapter.TvShowFavoritesAdapter;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowFavoritesViewModel;
import idmy.murphi.moviecatalogue.utils.Injection;
import idmy.murphi.moviecatalogue.utils.ItemOffsetDecoration;
import idmy.murphi.moviecatalogue.utils.ViewModelFactory;

public class TvShowFavoritesFragment extends Fragment {

    private TvShowFavoritesViewModel viewModel;
    private FragmentFavoriteTvshowsBinding binding;

    public static TvShowFavoritesFragment newInstance() {
        return new TvShowFavoritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteTvshowsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = obtainViewModel(getActivity());
        setupListAdapter();
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = binding.tvShowList.rvTvList;
        final TvShowFavoritesAdapter favoritesAdapter = new TvShowFavoritesAdapter();
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(Objects.requireNonNull(getActivity()), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        viewModel.getFavoriteListLiveData().observe(getViewLifecycleOwner(), tvShowList -> {
            if (tvShowList.isEmpty()) {
                binding.tvShowList.rvTvList.setVisibility(View.GONE);
                binding.emptyState.setVisibility(View.VISIBLE);
            } else {
                binding.tvShowList.rvTvList.setVisibility(View.VISIBLE);
                binding.emptyState.setVisibility(View.GONE);
                favoritesAdapter.submitList(tvShowList);
            }
        });
    }

    private TvShowFavoritesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
        return ViewModelProviders.of(activity, factory).get(TvShowFavoritesViewModel.class);
    }
}
