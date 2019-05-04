package org.my.alias.UI.GameScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.my.alias.Preferences;
import org.my.alias.R;
import org.my.alias.Team;
import org.my.alias.UI.ScoreDialog;

import br.com.kots.mob.complex.preferences.ComplexPreferences;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StartScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ComplexPreferences complexPreferences;
    @BindView (R.id.radio_group_team) RadioGroup radioGroup;
    @BindView (R.id.first_team) RadioButton radioFirst;
    @BindView (R.id.second_team)RadioButton radioSecond;
    @BindView (R.id.thurd_team)RadioButton radioThird;
    @BindView (R.id.fouth_team)RadioButton radioFourth;
    @BindView (R.id.info)TextView info;
    boolean continueGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_start_screen);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        complexPreferences = ComplexPreferences.getComplexPreferences(this, "all_teams", MODE_PRIVATE);
        continueGame = sharedPreferences.getBoolean("continue", false);
        sharedPreferences.edit().putBoolean("continue", true).apply();
        setRadioGroupText();

        int active = sharedPreferences.getInt(Preferences.KEY_ACTIVE_TEAM, 1);
        switch (active) {
            case 1:
                radioFirst.setChecked(true);
                info.setText(getString(R.string.info) + " " + getString(R.string.for_team1));
                break;
            case 2:
                radioSecond.setChecked(true);
                info.setText(getString(R.string.info) + " " + getString(R.string.for_team2));
                break;
            case 3:
                radioThird.setChecked(true);
                info.setText(getString(R.string.info) + " " + getString(R.string.for_team3));
                break;
            case 4:
                radioFourth.setChecked(true);
                info.setText(getString(R.string.info) + " " + getString(R.string.for_team4));
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.first_team:
                        info.setText(getString(R.string.info) + " " + getString(R.string.for_team1));
                        break;
                    case R.id.second_team:
                        info.setText(getString(R.string.info) + " " + getString(R.string.for_team2));
                        break;
                    case R.id.thurd_team:
                        info.setText(getString(R.string.info) + " " + getString(R.string.for_team3));
                        break;
                    case R.id.fouth_team:
                        info.setText(getString(R.string.info) + " " + getString(R.string.for_team4));
                        break;
                }
            }
        });


    }

    public void setRadioGroupText() {
        Team firstTeam;
        Team secondTeam;
        Team thirdTeam;
        Team fourthTeam;
        int teamsNumber = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2"));
        switch (teamsNumber) {
            case 2:
                if(!continueGame){
                    complexPreferences.putObject(Preferences.KEY_FIRST, new Team(1));
                    complexPreferences.putObject(Preferences.KEY_SECOND, new Team(2));
                }else {
                    firstTeam = complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
                    radioFirst.setText(getString(R.string.perform_score, getString(R.string.team1),
                            String.valueOf(firstTeam.getScore()), String.valueOf(firstTeam.getSteps())));
                    secondTeam = complexPreferences.getObject(Preferences.KEY_SECOND, Team.class);
                    radioSecond.setText(getString(R.string.perform_score, getString(R.string.team2),
                            String.valueOf(secondTeam.getScore()), String.valueOf(secondTeam.getSteps())));
                }
                radioThird.setVisibility(View.INVISIBLE);
                radioFourth.setVisibility(View.INVISIBLE);
                break;
            case 3:
                if(!continueGame) {
                    complexPreferences.putObject(Preferences.KEY_FIRST, new Team(1));
                    complexPreferences.putObject(Preferences.KEY_SECOND, new Team(2));
                    complexPreferences.putObject(Preferences.KEY_THIRD, new Team(3));
                }else {
                    firstTeam = complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
                    radioFirst.setText(getString(R.string.perform_score, getString(R.string.team1),
                            String.valueOf(firstTeam.getScore()), String.valueOf(firstTeam.getSteps())));
                    secondTeam = complexPreferences.getObject(Preferences.KEY_SECOND, Team.class);
                    radioSecond.setText(getString(R.string.perform_score, getString(R.string.team2),
                            String.valueOf(secondTeam.getScore()), String.valueOf(secondTeam.getSteps())));
                    thirdTeam = complexPreferences.getObject(Preferences.KEY_THIRD, Team.class);
                    radioThird.setText(getString(R.string.perform_score, getString(R.string.team3),
                            String.valueOf(thirdTeam.getScore()), String.valueOf(thirdTeam.getSteps())));
                }
                radioFourth.setVisibility(View.INVISIBLE);
                break;
            case 4:
                if(!continueGame) {
                    complexPreferences.putObject(Preferences.KEY_FIRST, new Team(1));
                    complexPreferences.putObject(Preferences.KEY_SECOND, new Team(2));
                    complexPreferences.putObject(Preferences.KEY_THIRD, new Team(3));
                    complexPreferences.putObject(Preferences.KEY_FOURTH, new Team(4));
                }else{
                    firstTeam = complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
                    radioFirst.setText(getString(R.string.perform_score, getString(R.string.team1),
                            String.valueOf(firstTeam.getScore()), String.valueOf(firstTeam.getSteps())));
                    secondTeam = complexPreferences.getObject(Preferences.KEY_SECOND, Team.class);
                    radioSecond.setText(getString(R.string.perform_score, getString(R.string.team2),
                            String.valueOf(secondTeam.getScore()), String.valueOf(secondTeam.getSteps())));
                    thirdTeam = complexPreferences.getObject(Preferences.KEY_THIRD, Team.class);
                    radioThird.setText(getString(R.string.perform_score, getString(R.string.team3),
                            String.valueOf(thirdTeam.getScore()), String.valueOf(thirdTeam.getSteps())));
                    fourthTeam = complexPreferences.getObject(Preferences.KEY_FOURTH, Team.class);
                    radioFourth.setText(getString(R.string.perform_score, getString(R.string.team4),
                            String.valueOf(fourthTeam.getScore()), String.valueOf(fourthTeam.getSteps())));
                }

                break;
        }
        complexPreferences.commit();
    }

    public int getCheckedRadioButton() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.first_team:
                return 1;
            case R.id.second_team:
                return 2;
            case R.id.thurd_team:
                return 3;
            case R.id.fouth_team:
                return 4;
            default:
                return 1;
        }
    }

    public void onStartClick(View view) {
        sharedPreferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, getCheckedRadioButton()).apply();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onShowScoreClick(View view) {
        ScoreDialog scoreDialog = new ScoreDialog();
        scoreDialog.show(getSupportFragmentManager(), "score");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.restart:
                startActivity(new Intent(this, FirstScreen.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}