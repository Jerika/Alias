package org.my.alias.UI.GameScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.my.alias.Preferences;
import org.my.alias.R;
import org.my.alias.UI.SettingsActivity;

public class FirstScreen extends AppCompatActivity {
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first_screen);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("continue", false).apply();

    }

    public void onSettingsClick(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void onStartClick(View view) {
        preferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, 1).apply();
        startActivity(new Intent(this, StartScreen.class));
    }

}
