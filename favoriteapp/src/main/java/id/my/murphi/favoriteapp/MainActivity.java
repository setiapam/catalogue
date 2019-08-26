package id.my.murphi.favoriteapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import id.my.murphi.favoriteapp.fragment.MovieFavoritesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityUtils.replaceFragmentInActivity(
                getSupportFragmentManager(), MovieFavoritesFragment.newInstance(),
                R.id.fragment_container);
    }
}
