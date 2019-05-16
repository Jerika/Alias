package org.my.alias.UI.GameScreens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.kots.mob.complex.preferences.ComplexPreferences
import kotlinx.android.synthetic.main.layout_start_screen.*
import org.my.alias.Preferences
import org.my.alias.Preferences.Companion.KEY_CONTINUE
import org.my.alias.R
import org.my.alias.Team
import org.my.alias.UI.ScoreDialog


class StartScreen : AppCompatActivity() {
    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var complexPreferences: ComplexPreferences
    internal var continueGame: Boolean = false

    val checkedRadioButton: Int
        get() {
            when (radio_group_team!!.checkedRadioButtonId) {
                R.id.first_team -> return 1
                R.id.second_team -> return 2
                R.id.thurd_team -> return 3
                R.id.fouth_team -> return 4
                else -> return 1
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_start_screen)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        complexPreferences = ComplexPreferences.getComplexPreferences(this, "all_teams", Context.MODE_PRIVATE)
        continueGame = sharedPreferences.getBoolean(KEY_CONTINUE, false)
        sharedPreferences.edit().putBoolean(KEY_CONTINUE, true).apply()
        setRadioGroupText()

        val active = sharedPreferences.getInt(Preferences.KEY_ACTIVE_TEAM, 1)
        when (active) {
            1 -> {
                first_team.isChecked = true
                info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team1)))
            }
            2 -> {
                second_team.isChecked = true
                info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team2)))
            }
            3 -> {
                thurd_team.isChecked = true
                info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team3)))
            }
            4 -> {
                fouth_team.isChecked = true
                info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team4)))
            }
        }
        radio_group_team.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.first_team -> info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team1)))
                R.id.second_team -> info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team2)))
                R.id.thurd_team -> info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team3)))
                R.id.fouth_team -> info.setText(String.format("%s %s", getString(R.string.info), getString(R.string.for_team4)))
            }
        }
    }

    fun setRadioGroupText() {
        val firstTeam: Team?
        val secondTeam: Team?
        val thirdTeam: Team?
        val fourthTeam: Team?
        val teamsNumber = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2")!!)
        when (teamsNumber) {
            2 -> {
                if (!continueGame) {
                    complexPreferences.putObject(Preferences.KEY_FIRST, Team(1))
                    complexPreferences.putObject(Preferences.KEY_SECOND, Team(2))
                } else {
                    firstTeam = complexPreferences.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
                    first_team.text = getString(R.string.perform_score, getString(R.string.team1),
                            firstTeam!!.score.toString(), firstTeam.steps.toString())
                    secondTeam = complexPreferences.getObject<Team>(Preferences.KEY_SECOND, Team::class.java)
                    second_team.text = getString(R.string.perform_score, getString(R.string.team2),
                            secondTeam!!.score.toString(), secondTeam.steps.toString())
                }
                thurd_team.visibility = View.INVISIBLE
                fouth_team.visibility = View.INVISIBLE
            }
            3 -> {
                if (!continueGame) {
                    complexPreferences.putObject(Preferences.KEY_FIRST, Team(1))
                    complexPreferences.putObject(Preferences.KEY_SECOND, Team(2))
                    complexPreferences.putObject(Preferences.KEY_THIRD, Team(3))
                } else {
                    firstTeam = complexPreferences.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
                    first_team.text = getString(R.string.perform_score, getString(R.string.team1),
                            firstTeam!!.score.toString(), firstTeam.steps.toString())
                    secondTeam = complexPreferences.getObject<Team>(Preferences.KEY_SECOND, Team::class.java)
                    second_team.text = getString(R.string.perform_score, getString(R.string.team2),
                            secondTeam!!.score.toString(), secondTeam.steps.toString())
                    thirdTeam = complexPreferences.getObject<Team>(Preferences.KEY_THIRD, Team::class.java)
                    thurd_team.text = getString(R.string.perform_score, getString(R.string.team3),
                            thirdTeam!!.score.toString(), thirdTeam.steps.toString())
                }
                fouth_team.visibility = View.INVISIBLE
            }
            4 -> if (!continueGame) {
                complexPreferences.putObject(Preferences.KEY_FIRST, Team(1))
                complexPreferences.putObject(Preferences.KEY_SECOND, Team(2))
                complexPreferences.putObject(Preferences.KEY_THIRD, Team(3))
                complexPreferences.putObject(Preferences.KEY_FOURTH, Team(4))
            } else {
                firstTeam = complexPreferences.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
                first_team.text = getString(R.string.perform_score, getString(R.string.team1),
                        firstTeam!!.score.toString(), firstTeam.steps.toString())
                secondTeam = complexPreferences.getObject<Team>(Preferences.KEY_SECOND, Team::class.java)
                second_team.text = getString(R.string.perform_score, getString(R.string.team2),
                        secondTeam!!.score.toString(), secondTeam.steps.toString())
                thirdTeam = complexPreferences.getObject<Team>(Preferences.KEY_THIRD, Team::class.java)
                thurd_team.text = getString(R.string.perform_score, getString(R.string.team3),
                        thirdTeam!!.score.toString(), thirdTeam.steps.toString())
                fourthTeam = complexPreferences.getObject<Team>(Preferences.KEY_FOURTH, Team::class.java)
                fouth_team.text = getString(R.string.perform_score, getString(R.string.team4),
                        fourthTeam!!.score.toString(), fourthTeam.steps.toString())
            }
        }
        complexPreferences.commit()
    }

    fun onStartClick(view: View) {
        sharedPreferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, checkedRadioButton).apply()
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun onShowScoreClick(view: View) {
        val scoreDialog = ScoreDialog()
        scoreDialog.show(supportFragmentManager, "score")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_simple, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.restart -> startActivity(Intent(this, FirstScreen::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}