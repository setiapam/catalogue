package idmy.murphi.moviecatalogue.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.ui.fragments.MyPreferenceFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar myToolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.setting_container, new MyPreferenceFragment())
                .commit();
    }
}
