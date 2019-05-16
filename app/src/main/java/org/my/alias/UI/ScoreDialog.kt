package org.my.alias.UI

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.com.kots.mob.complex.preferences.ComplexPreferences
import kotlinx.android.synthetic.main.dialog_layout.view.*
import org.my.alias.Preferences
import org.my.alias.Preferences.Companion.KEY_CONTINUE
import org.my.alias.R
import org.my.alias.Team
import org.my.alias.UI.GameScreens.StartScreen
import java.util.*


class ScoreDialog : DialogFragment {
    internal var SAVE_DATA_KEY = "score"
    internal lateinit var allTeams: ArrayList<Team>
    lateinit var sharedPreferences : SharedPreferences
    lateinit var complexPreferences : ComplexPreferences
    lateinit var firstTeam: Team
    lateinit var secondTeam: Team
    lateinit var thirdTeam: Team
    lateinit var fourthTeam: Team

    constructor() : super() {}

    @SuppressLint("ValidFragment")
    constructor(allTeams: ArrayList<Team>) : super() {
        this.allTeams = allTeams
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SAVE_DATA_KEY, allTeams)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        complexPreferences = ComplexPreferences.getComplexPreferences(activity, "all_teams", Context.MODE_PRIVATE)
        val inflater = LayoutInflater.from(activity)
        val v = inflater.inflate(R.layout.dialog_layout, null)
        confifUi(v);
        return AlertDialog.Builder(activity!!).setView(v).setTitle(getString(R.string.score))
                .setInverseBackgroundForced(true)
                .setPositiveButton(getString(R.string.ok)) { dialog, which -> dismiss() }.setNegativeButton(getString(R.string.next_round)) { dialog, which ->
                    val intent = Intent(activity, StartScreen::class.java)
                    intent.getBooleanExtra(KEY_CONTINUE, true)
                    startActivity(intent)
                }.create()
    }

    private fun confifUi(view: View) {
        val teamsNumber = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2")!!)
        when (teamsNumber) {
            2 -> {
                firstTeam = complexPreferences.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
                view.first_team.text = getString(R.string.perform_score, getString(R.string.team1),
                        firstTeam.score.toString(), firstTeam.steps.toString())
                secondTeam = complexPreferences.getObject<Team>(Preferences.KEY_SECOND, Team::class.java)
                view.second_team.text = getString(R.string.perform_score, getString(R.string.team2),
                        secondTeam.score.toString(), secondTeam.steps.toString())
                view.thurd_team.visibility = View.GONE
                view.fourth_team.visibility = View.GONE
            }
            3 -> {
                firstTeam = complexPreferences.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
                view.first_team.text = getString(R.string.perform_score, getString(R.string.team1),
                        firstTeam.score.toString(), firstTeam.steps.toString())
                secondTeam = complexPreferences.getObject<Team>(Preferences.KEY_SECOND, Team::class.java)
                view.second_team.text = getString(R.string.perform_score, getString(R.string.team2),
                        secondTeam.score.toString(), secondTeam.steps.toString())
                thirdTeam = complexPreferences.getObject<Team>(Preferences.KEY_THIRD, Team::class.java)
                view.thurd_team.text = getString(R.string.perform_score, getString(R.string.team3),
                        thirdTeam.score.toString(), thirdTeam.steps.toString())
                view.fourth_team.visibility = View.GONE
            }
            4 -> {
                firstTeam = complexPreferences.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
                view.first_team.text = getString(R.string.perform_score, getString(R.string.team1),
                        firstTeam.score.toString(), firstTeam.steps.toString())
                secondTeam = complexPreferences.getObject<Team>(Preferences.KEY_SECOND, Team::class.java)
                view.second_team.text = getString(R.string.perform_score, getString(R.string.team2),
                        secondTeam.score.toString(), secondTeam.steps.toString())
                thirdTeam = complexPreferences.getObject<Team>(Preferences.KEY_THIRD, Team::class.java)
                view.thurd_team.text = getString(R.string.perform_score, getString(R.string.team3),
                        thirdTeam.score.toString(), thirdTeam.steps.toString())
                fourthTeam = complexPreferences.getObject<Team>(Preferences.KEY_FOURTH, Team::class.java)
                view.fourth_team.text = getString(R.string.perform_score, getString(R.string.team4),
                        fourthTeam.score.toString(), fourthTeam.steps.toString())
            }
        }
    }

}
