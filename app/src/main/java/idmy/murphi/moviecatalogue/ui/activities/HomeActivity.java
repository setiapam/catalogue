package idmy.murphi.moviecatalogue.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.ui.fragments.FavoritesFragment;
import idmy.murphi.moviecatalogue.ui.fragments.MoviesFragment;
import idmy.murphi.moviecatalogue.ui.fragments.TvShowsFragment;
import idmy.murphi.moviecatalogue.utils.ActivityUtils;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);
        setupBottomNavigation();

        if (savedInstanceState == null) {
            setupViewFragment();
        }
    }

    private void setupViewFragment() {
        MoviesFragment discoverMoviesFragment = MoviesFragment.newInstance();
        ActivityUtils.replaceFragmentInActivity(
                getSupportFragmentManager(), discoverMoviesFragment, R.id.fragment_container);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_movie:
                    ActivityUtils.replaceFragmentInActivity(
                            getSupportFragmentManager(), MoviesFragment.newInstance(),
                            R.id.fragment_container);
                    return true;
                case R.id.action_tv:
                    ActivityUtils.replaceFragmentInActivity(
                            getSupportFragmentManager(), TvShowsFragment.newInstance(),
                            R.id.fragment_container);
                    return true;
                case R.id.action_favorites:
                    ActivityUtils.replaceFragmentInActivity(
                            getSupportFragmentManager(), FavoritesFragment.newInstance(),
                            R.id.fragment_container);
                    return true;
            }
            return false;
        });
    }
}