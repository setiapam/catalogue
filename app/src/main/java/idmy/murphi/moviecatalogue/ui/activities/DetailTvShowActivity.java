package idmy.murphi.moviecatalogue.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.databinding.ActivityDetailTvShowBinding;
import idmy.murphi.moviecatalogue.ui.adapter.TvShowCastAdapter;
import idmy.murphi.moviecatalogue.ui.viewmodel.TvShowDetailsViewModel;
import idmy.murphi.moviecatalogue.utils.Injection;
import idmy.murphi.moviecatalogue.utils.UiUtils;
import idmy.murphi.moviecatalogue.utils.ViewModelFactory;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW_ID = "extra_tv_show_id";

    private static final int DEFAULT_ID = -1;

    private ActivityDetailTvShowBinding mBinding;

    private TvShowDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
        final long tvShowId = getIntent().getLongExtra(EXTRA_TV_SHOW_ID, DEFAULT_ID);
        if (tvShowId == DEFAULT_ID) {
            closeOnError();
            return;
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tv_show);
        mBinding.setLifecycleOwner(this);

        mViewModel = obtainViewModel();
        mViewModel.init(tvShowId);
        setupToolbar();
        setupCastAdapter();

        mViewModel.getResult().observe(this, resource -> {
            if (resource.data != null &&
                    resource.data.tvShow != null) {
                mViewModel.setFavorite(resource.data.tvShow.isFavorite());
                invalidateOptionsMenu();
            }
            mBinding.setResource(resource);
            mBinding.setTvShowDetails(resource.data);
        });

        mBinding.networkState.retryButton.setOnClickListener(view -> mViewModel.retry(tvShowId));

        mViewModel.getSnackbarMessage().observe(this, (Observer<Integer>) message -> Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show());
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            handleCollapsedToolbarTitle();
        }
    }

    private void setupCastAdapter() {
        RecyclerView listCast = mBinding.tvShowDetailsInfo.listCast;
        listCast.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        listCast.setAdapter(new TvShowCastAdapter());
        ViewCompat.setNestedScrollingEnabled(listCast, false);
    }

    private TvShowDetailsViewModel obtainViewModel() {
        ViewModelFactory factory = Injection.provideViewModelFactory(this, getResources().getConfiguration().locale.getLanguage().toLowerCase());
        return ViewModelProviders.of(this, factory).get(TvShowDetailsViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);

        MenuItem favoriteItem = menu.findItem(R.id.action_favorite);
        if (mViewModel.isFavorite()) {
            favoriteItem.setIcon(R.drawable.ic_favorite_black_24dp)
                    .setTitle(R.string.action_remove_from_favorites);
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_border_black_24dp)
                    .setTitle(R.string.action_favorite);
        }
        UiUtils.tintMenuIcon(this, favoriteItem, R.color.md_white_1000);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            mViewModel.onFavoriteClicked();
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        throw new IllegalArgumentException("Access denied.");
    }

    private void handleCollapsedToolbarTitle() {
        mBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbar.setTitle(
                            mViewModel.getResult().getValue().data.tvShow.getTitle());
                    isShow = true;
                } else if (isShow) {
                    mBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
