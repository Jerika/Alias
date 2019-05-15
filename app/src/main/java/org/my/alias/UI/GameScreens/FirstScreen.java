package org.my.alias.UI.GameScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import org.my.alias.Preferences;
import org.my.alias.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static org.my.alias.Preferences.KEY_COMPLEXITY;
import static org.my.alias.Preferences.KEY_CONTINUE;
import static org.my.alias.Preferences.KEY_QANTITY_TEAMS_FROM_PREFS;
import static org.my.alias.Preferences.KEY_TIMER;

public class FirstScreen extends AppCompatActivity {
    SharedPreferences preferences;
    @BindView(R.id.compexity)
    RadioGroup radioGroupComplexity;
    @BindView(R.id.team)
    RadioGroup radioGroupTeam;
    @BindView(R.id.time)
    RadioGroup radioGroupTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        ButterKnife.bind(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean(KEY_CONTINUE, false).apply();
        configUi();
        configCheckedListeners();
    }

    private void configUi() {
        int compl = Integer.parseInt(preferences.getString(KEY_COMPLEXITY, "2"));
        radioGroupComplexity.check(compl);
        int teams = Integer.parseInt(preferences.getString(KEY_QANTITY_TEAMS_FROM_PREFS, "2"));
        radioGroupTeam.check(teams  + 3);
        int time = preferences.getInt(KEY_TIMER, 60);
        switch (time) {
            case 30:
                radioGroupTime.check(8);
                break;
            case 45:
                radioGroupTime.check(9);
                break;
            case 60:
                radioGroupTime.check(10);
                break;
        }
    }

    private void configCheckedListeners() {
        radioGroupComplexity.setOnCheckedChangeListener((radioGroup, i) ->
                preferences.edit().putString(KEY_COMPLEXITY, String.valueOf(i)).apply());
        radioGroupTeam.setOnCheckedChangeListener((radioGroup, i) ->
                preferences.edit().putString(KEY_QANTITY_TEAMS_FROM_PREFS, String.valueOf(i - 3)).apply());
        radioGroupTime.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case 8:
                    preferences.edit().putInt(KEY_TIMER, 30).apply();
                    break;
                case 9:
                    preferences.edit().putInt(KEY_TIMER, 45).apply();
                    break;
                case 10:
                    preferences.edit().putInt(KEY_TIMER, 60).apply();
                    break;
            }
        });
    }

    public void onStartClick(View view) {
        preferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, 1).apply();
        startActivity(new Intent(this, StartScreen.class));
    }
}
