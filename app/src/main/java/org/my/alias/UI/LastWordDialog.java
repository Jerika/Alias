package org.my.alias.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.my.alias.Pair;
import org.my.alias.Preferences;
import org.my.alias.R;
import org.my.alias.Team;
import org.my.alias.UI.GameScreens.FinishScreen;

import java.util.ArrayList;

import br.com.kots.mob.complex.preferences.ComplexPreferences;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LastWordDialog extends DialogFragment implements View.OnClickListener{

    @BindView (R.id.first_team) RadioButton RadioFirst;
    @BindView (R.id.second_team)RadioButton RadioSecond;
    @BindView (R.id.thurd_team)RadioButton RadioThird;
    @BindView (R.id.fouth_team)RadioButton RadioFourth;
    @BindView (R.id.none) RadioButton RadioNone;
    @BindView (R.id.lastWord) TextView text;

    private ArrayList<Pair> playWords;
    private int score;
    private CharSequence word;
    private int checkedRadio;
    private ComplexPreferences complexPreferences;
    private SharedPreferences sharedPreferences;
    private static final String SAVE_KEY_WORD = "word";
    private static final String SAVE_KEY_PLAYWORDS = "playWords";
    private static final String SAVE_KEY_SCORE = "score";


    public LastWordDialog(){
        super();
    }

    @SuppressLint("ValidFragment")
    public LastWordDialog(CharSequence word, ArrayList<Pair> pair, int score){
        super();
        this.word = word;
        this.playWords = pair;
        this.score = score;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_KEY_WORD, word);
        outState.putInt(SAVE_KEY_SCORE, score);
        outState.putSerializable(SAVE_KEY_PLAYWORDS, playWords);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "all_teams", getActivity().MODE_PRIVATE);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.last_word_dialog, null);
        ButterKnife.bind(this, v);
        text.setText(getString(R.string.who_guessed, word));
        RadioFirst.setOnClickListener(this);
        RadioSecond.setOnClickListener(this);
        RadioThird.setOnClickListener(this);
        RadioFourth.setOnClickListener(this);
        RadioNone.setOnClickListener(this);
        switch (Integer.parseInt(sharedPreferences.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2"))){
            case 2:
                RadioThird.setVisibility(View.INVISIBLE);
                RadioFourth.setVisibility(View.INVISIBLE);
                break;
            case 3:
                RadioFourth.setVisibility(View.INVISIBLE);
                break;
        }

        AlertDialog lastWord = new AlertDialog.Builder(getContext()).setView(v).setTitle(R.string.who)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    Team team = getCheckedTeam();
                    if (team != null){
                        if (team.getNumber() == sharedPreferences.getInt(Preferences.KEY_ACTIVE_TEAM, 1)) {
                            playWords.add(new Pair((String) word, true));
                        } else {
                            Preferences.getInstance(getActivity()).saveTeam(team, team.getNumber(), true, true, 0, 0 );
                        }
                    }
                    Intent intent = new Intent(getActivity(), FinishScreen.class);
                    intent.putExtra(Preferences.KEY_PAIR, playWords);
                    intent.putExtra(Preferences.KEY_SCORE, score);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();
                    dismiss();
                }).create();
        return lastWord ;
    }

    public Team getCheckedTeam(){
        switch (checkedRadio){
            case R.id.first_team:
                return complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
            case R.id.second_team:
                return complexPreferences.getObject(Preferences.KEY_SECOND, Team.class);
            case R.id.thurd_team:
                return complexPreferences.getObject(Preferences.KEY_THIRD, Team.class);
            case R.id.fouth_team:
                return complexPreferences.getObject(Preferences.KEY_FOURTH, Team.class);
            case R.id.none:
                return null;
            default:
                return complexPreferences.getObject(Preferences.KEY_FIRST, Team.class);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_team:
                checkedRadio = R.id.first_team;
                RadioFirst.setChecked(true);
                RadioSecond.setChecked(false);
                RadioThird.setChecked(false);
                RadioFourth.setChecked(false);
                RadioNone.setChecked(false);
                break;
            case R.id.second_team:
                checkedRadio = R.id.second_team;
                RadioFirst.setChecked(false);
                RadioSecond.setChecked(true);
                RadioThird.setChecked(false);
                RadioFourth.setChecked(false);
                RadioNone.setChecked(false);
                break;
            case R.id.thurd_team:
                checkedRadio = R.id.thurd_team;
                RadioFirst.setChecked(false);
                RadioSecond.setChecked(false);
                RadioThird.setChecked(true);
                RadioFourth.setChecked(false);
                RadioNone.setChecked(false);
                break;
            case R.id.fouth_team:
                checkedRadio = R.id.fouth_team;
                RadioFirst.setChecked(false);
                RadioSecond.setChecked(false);
                RadioThird.setChecked(false);
                RadioFourth.setChecked(true);
                RadioNone.setChecked(false);
                break;
            case R.id.none:
                checkedRadio = R.id.none;
                RadioFirst.setChecked(false);
                RadioSecond.setChecked(false);
                RadioThird.setChecked(false);
                RadioFourth.setChecked(false);
                RadioNone.setChecked(true);
                break;
        }
    }

}
