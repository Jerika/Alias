package org.my.alias;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.com.kots.mob.complex.preferences.ComplexPreferences;

public class Preferences {
    private static Preferences instance;
    private Context context;
    ComplexPreferences complexPreferences;
    SharedPreferences sharedPreferences;
    public static final String KEY_FIRST = "first";
    public static final String KEY_SECOND  = "second";
    public static final String KEY_THIRD = "thurd";
    public static final String KEY_FOURTH = "fouth";
    public static final String KEY_ACTIVE_TEAM = "active_team";
    public static final String KEY_QANTITY_TEAMS_FROM_PREFS = "teams";
    public static final String KEY_PAIR = "pair";
    public static final String KEY_SCORE = "score";
    public static final String KEY_WORD = "word";
    public static final String KEY_TIMER = "timer";


    private Preferences(Context context) {
        super();
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        complexPreferences = ComplexPreferences.getComplexPreferences(context, "all_teams", context.MODE_PRIVATE);

    }

    public static Preferences getInstance(Context context) {
        if (instance == null) {
            instance = new Preferences(context);
        }
        return instance;
    }

    public void saveTeam (Team team, int teamNumder, boolean teamSaved, boolean onlyOne, int checkedScore, int previousScore) {
        int lastScore = team.getScore();
        if (onlyOne){
            team.setScore(lastScore +1);
        }else{
            team.setScore(lastScore - previousScore + checkedScore);
            if (!teamSaved)
            {
                int lastSteps = team.getSteps();
                team.setSteps(lastSteps + 1);
            }
        }
        switch (teamNumder) {
            case 1:
                complexPreferences.putObject(KEY_FIRST, team);
                break;
            case 2:
                complexPreferences.putObject(KEY_SECOND, team);
                break;
            case 3:
                complexPreferences.putObject(KEY_THIRD, team);
                break;
            case 4:
                complexPreferences.putObject(KEY_FOURTH, team);
                break;
        }
        complexPreferences.commit();
        if (!onlyOne){
            int numberActiveTeam = sharedPreferences.getInt(Preferences.KEY_ACTIVE_TEAM, 1);
            int teamsNumber = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2"));

            if (numberActiveTeam == teamsNumber){
                sharedPreferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, 1).apply();
            } else {
                sharedPreferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, numberActiveTeam + 1).apply();
            }
        }

    }

    public Team getTeamFromPreference(int teamNumber) {
        Team team = null;
        switch (teamNumber) {
            case 1:
                team = complexPreferences.getObject(KEY_FIRST, Team.class);
                break;
            case 2:
                team = complexPreferences.getObject(KEY_SECOND, Team.class);
                break;
            case 3:
                team = complexPreferences.getObject(KEY_THIRD, Team.class);
                break;
            case 4:
                team = complexPreferences.getObject(KEY_FOURTH, Team.class);
                break;
        }
        return team;
    }
}