package org.my.alias.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.my.alias.Preferences;
import org.my.alias.R;
import org.my.alias.Team;
import org.my.alias.UI.GameScreens.StartScreen;

import java.util.ArrayList;

import br.com.kots.mob.complex.preferences.ComplexPreferences;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreDialog extends DialogFragment {
    String SAVE_DATA_KEY = "score";
    ArrayList<Team> allTeams;
    @BindView (R.id.first_team)TextView textFirst;
    @BindView (R.id.second_team)TextView textSecond;
    @BindView (R.id.thurd_team)TextView textThird;
    @BindView (R.id.fourth_team)TextView textFourth;


    public ScoreDialog(){
        super();
    }

    @SuppressLint("ValidFragment")
    public ScoreDialog(ArrayList<Team> allTeams){
        super();
        this.allTeams = allTeams;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVE_DATA_KEY, allTeams);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "all_teams", getActivity().MODE_PRIVATE);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.dialog_layout, null);
        ButterKnife.bind(this, v);
        Team firstTeam;
        Team secondTeam;
        Team thirdTeam;
        Team fourthTeam;
        int teamsNumber = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2"));
        switch (teamsNumber) {
            case 2:
                firstTeam = complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
                textFirst.setText(getString(R.string.perform_score, getString(R.string.team1),
                        String.valueOf(firstTeam.getScore()), String.valueOf(firstTeam.getSteps())));
                secondTeam = complexPreferences.getObject(Preferences.KEY_SECOND, Team.class);
                textSecond.setText(getString(R.string.perform_score, getString(R.string.team2),
                        String.valueOf(secondTeam.getScore()), String.valueOf(secondTeam.getSteps())));
                textThird.setVisibility(View.GONE);
                textFourth.setVisibility(View.GONE);
                break;
            case 3:
                firstTeam = complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
                textFirst.setText(getString(R.string.perform_score, getString(R.string.team1),
                        String.valueOf(firstTeam.getScore()), String.valueOf(firstTeam.getSteps())));
                secondTeam = complexPreferences.getObject(Preferences.KEY_SECOND, Team.class);
                textSecond.setText(getString(R.string.perform_score, getString(R.string.team2),
                        String.valueOf(secondTeam.getScore()), String.valueOf(secondTeam.getSteps())));
                thirdTeam = complexPreferences.getObject(Preferences.KEY_THIRD, Team.class);
                textThird.setText(getString(R.string.perform_score, getString(R.string.team3),
                        String.valueOf(thirdTeam.getScore()), String.valueOf(thirdTeam.getSteps())));
                textFourth.setVisibility(View.GONE);
                break;
            case 4:
                firstTeam = complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
                textFirst.setText(getString(R.string.perform_score, getString(R.string.team1),
                        String.valueOf(firstTeam.getScore()), String.valueOf(firstTeam.getSteps())));
                secondTeam = complexPreferences.getObject(Preferences.KEY_SECOND, Team.class);
                textSecond.setText(getString(R.string.perform_score, getString(R.string.team2),
                        String.valueOf(secondTeam.getScore()), String.valueOf(secondTeam.getSteps())));
                thirdTeam = complexPreferences.getObject(Preferences.KEY_THIRD, Team.class);
                textThird.setText(getString(R.string.perform_score, getString(R.string.team3),
                        String.valueOf(thirdTeam.getScore()), String.valueOf(thirdTeam.getSteps())));
                fourthTeam = complexPreferences.getObject(Preferences.KEY_FOURTH, Team.class);
                textFourth.setText(getString(R.string.perform_score, getString(R.string.team4),
                        String.valueOf(fourthTeam.getScore()), String.valueOf(fourthTeam.getSteps())));
                break;
        }

        AlertDialog score = new AlertDialog.Builder(getActivity()).setView(v).setTitle(getString(R.string.score))
                .setInverseBackgroundForced(true)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).setNegativeButton(getString(R.string.next_round), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), StartScreen.class);
                        intent.getBooleanExtra("continue", true);
                        startActivity(intent);
                    }
                }).create();
        return score;
    }
}
