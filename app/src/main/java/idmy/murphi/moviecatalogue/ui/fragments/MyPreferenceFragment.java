package idmy.murphi.moviecatalogue.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.receiver.ReminderReceiver;

public class MyPreferenceFragment extends PreferenceFragmentCompat {

    private static final String LANGUAGE = "language";
    private static final String DAILY_REMINDER = "DailyReminder";
    private static final String RELEASED_REMINDER = "ReleasedReminder";
    private ReminderReceiver reminderReceiver;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        reminderReceiver = new ReminderReceiver();
        Preference language = findPreference(LANGUAGE);
        SwitchPreference dailyReminder = (SwitchPreference) findPreference(DAILY_REMINDER);
        SwitchPreference releasedReminder = (SwitchPreference) findPreference(RELEASED_REMINDER);

        if (dailyReminder.isEnabled()) {
            reminderReceiver.setDailyReminder(getContext(), DAILY_REMINDER);
        }
        if (releasedReminder.isEnabled()) {
            reminderReceiver.setReleasedReminder(getContext(), RELEASED_REMINDER);
        }

        dailyReminder.setOnPreferenceChangeListener((preference, newValue) -> {
            if (newValue.equals(true)) {
                reminderReceiver.setDailyReminder(getContext(), DAILY_REMINDER);
            } else {
                reminderReceiver.cancelAlarm(getContext(), DAILY_REMINDER);
            }
            return true;
        });

        releasedReminder.setOnPreferenceChangeListener((preference, newValue) -> {
            if (newValue.equals(true)) {
                reminderReceiver.setReleasedReminder(getContext(), RELEASED_REMINDER);
            } else {
                reminderReceiver.cancelAlarm(getContext(), RELEASED_REMINDER);
            }
            return true;
        });

        language.setOnPreferenceClickListener(preference -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            return true;
        });
    }
}
