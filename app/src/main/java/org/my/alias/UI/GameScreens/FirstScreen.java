package org.my.alias.UI.GameScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import org.my.alias.Preferences;
import org.my.alias.R;
import org.my.alias.UI.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstScreen extends AppCompatActivity {
    SharedPreferences preferences;
    @BindView(R.id.compexity)
    RadioGroup radioGroupComplexity;
    @BindView(R.id.team)
    RadioGroup radioGroupTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        ButterKnife.bind(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("continue", false).apply();

        radioGroupComplexity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int l = 0;
            }
        });
        radioGroupTeam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int l = 0;
            }
        });

    }

    public void onStartClick(View view) {
        preferences.edit().putString("compl", Preferences.getInstance(this).getComplexity()).apply();
        preferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, 1).apply();
        startActivity(new Intent(this, StartScreen.class));
    }

}
