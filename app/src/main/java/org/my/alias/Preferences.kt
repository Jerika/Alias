package org.my.alias

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import java.util.ArrayList

import br.com.kots.mob.complex.preferences.ComplexPreferences

class Preferences private constructor(private val context: Context) {
    internal var complexPreferences: ComplexPreferences
    internal var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        complexPreferences = ComplexPreferences.getComplexPreferences(context, "all_teams", Context.MODE_PRIVATE)

    }

    fun saveTeam(team: Team, teamNumder: Int, teamSaved: Boolean, onlyOne: Boolean, checkedScore: Int, previousScore: Int) {
        val lastScore = team.score
        if (onlyOne) {
            team.score = lastScore + 1
        } else {
            team.score = lastScore - previousScore + checkedScore
            if (!teamSaved) {
                val lastSteps = team.steps
                team.steps = lastSteps + 1
            }
        }
        when (teamNumder) {
            1 -> complexPreferences.putObject(KEY_FIRST, team)
            2 -> complexPreferences.putObject(KEY_SECOND, team)
            3 -> complexPreferences.putObject(KEY_THIRD, team)
            4 -> complexPreferences.putObject(KEY_FOURTH, team)
        }
        complexPreferences.commit()
        if (!onlyOne) {
            val numberActiveTeam = sharedPreferences.getInt(Preferences.KEY_ACTIVE_TEAM, 1)
            val teamsNumber = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2")!!)

            if (numberActiveTeam == teamsNumber) {
                sharedPreferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, 1).apply()
            } else {
                sharedPreferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, numberActiveTeam + 1).apply()
            }
        }

    }

    fun getTeamFromPreference(teamNumber: Int): Team? {
        var team: Team? = null
        when (teamNumber) {
            1 -> team = complexPreferences.getObject<Team>(KEY_FIRST, Team::class.java)
            2 -> team = complexPreferences.getObject<Team>(KEY_SECOND, Team::class.java)
            3 -> team = complexPreferences.getObject<Team>(KEY_THIRD, Team::class.java)
            4 -> team = complexPreferences.getObject<Team>(KEY_FOURTH, Team::class.java)
        }
        return team
    }

    companion object {
        private var instance: Preferences? = null
        val KEY_FIRST = "first"
        val KEY_SECOND = "second"
        val KEY_THIRD = "thurd"
        val KEY_FOURTH = "fouth"
        val KEY_ACTIVE_TEAM = "active_team"
        val KEY_QANTITY_TEAMS_FROM_PREFS = "teams"
        val KEY_PAIR = "pair"
        val KEY_SCORE = "score"
        val KEY_WORD = "word"
        val KEY_TIMER = "timer"
        val KEY_FINISH = "finish"
        val KEY_COMPLEXITY = "compl"
        val KEY_CONTINUE = "continue"

        fun getInstance(context: Context): Preferences {
            if (instance == null) {
                instance = Preferences(context)
            }
            return instance as Preferences
        }
    }

    //    public String getComplexity(){
    //        ArrayList<Integer> complexity = new ArrayList<>();
    //        boolean light = sharedPreferences.getBoolean("light", false);
    //        if (light){
    //            complexity.add(1);
    //        }
    //        boolean middle = sharedPreferences.getBoolean("middle", false);
    //        if (middle){
    //            complexity.add(2);
    //        }
    //        boolean hard = sharedPreferences.getBoolean("hard", false);
    //        if (hard){
    //            complexity.add(3);
    //        }
    //        boolean superHard = sharedPreferences.getBoolean("superHard", false);
    //        if (superHard){
    //            complexity.add(4);
    //        }
    //        String compl = null;
    //        for (int i = 0; i < complexity.size(); i++){
    //            if (i == 0){
    //                compl = String.valueOf(complexity.get(0));
    //            }else{
    //                compl = compl + ", " +  complexity.get(i);
    //            }
    //
    //        }
    //        return compl;
    //    }

}