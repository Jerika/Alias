package org.my.alias.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import org.my.alias.R;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    SharedPreferences pref;
    CheckBoxPreference light;
    CheckBoxPreference medium;
    CheckBoxPreference hard;
    CheckBoxPreference superHard;


    public boolean reallyChecked() {
        if (light.isChecked()|| medium.isChecked()|| hard.isChecked()|| superHard.isChecked()){
            return true;
        }else {
           return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ListPreference duration = (ListPreference) findPreference ("duration");
        duration.setSummary(duration.getEntry());
        ListPreference teams= (ListPreference) findPreference ("teams");
        light = (CheckBoxPreference) findPreference(getString(R.string.key_light));
        medium = (CheckBoxPreference) findPreference(getString(R.string.key_middle));
        hard = (CheckBoxPreference) findPreference(getString(R.string.key_hard));
        superHard = (CheckBoxPreference) findPreference(getString(R.string.key_super_hard));
        teams.setSummary(teams.getEntry());
        pref.registerOnSharedPreferenceChangeListener(this);
        light.setOnPreferenceClickListener(this);
        medium.setOnPreferenceClickListener(this);
        hard.setOnPreferenceClickListener(this);
        superHard.setOnPreferenceClickListener(this);
        if (!reallyChecked()){
            medium.setChecked(true);
            pref.edit().putBoolean("middle", true).apply();
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        Preference pref = findPreference(key);
        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
        }

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
        if (checkBoxPreference.getKey().equals(getString(R.string.key_light))){
            if (checkBoxPreference.isChecked()){
                pref.edit().putBoolean("light", true).apply();
            } else {
                pref.edit().putBoolean("light", false).apply();
            }
        }else if (checkBoxPreference.getKey().equals(getString(R.string.key_middle))){
                if (checkBoxPreference.isChecked()){
                    pref.edit().putBoolean("middle", true).apply();
                } else {
                    pref.edit().putBoolean("middle", false).apply();
                }
            }else if (checkBoxPreference.getKey().equals(getString(R.string.key_hard))){
                    if (checkBoxPreference.isChecked()){
                        pref.edit().putBoolean("hard", true).apply();
                    } else {
                        pref.edit().putBoolean("hard", false).apply();
                    }
                } else if (checkBoxPreference.getKey().equals(getString(R.string.key_super_hard))){
                        if (checkBoxPreference.isChecked()){
                            pref.edit().putBoolean("super_hard", true).apply();
                        } else {
                            pref.edit().putBoolean("super_hard", false).apply();
                        }
            }
        return false;
    }
}


