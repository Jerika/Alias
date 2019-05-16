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
import kotlinx.android.synthetic.main.layout_finish_screen.*
import org.my.alias.*
import org.my.alias.UI.ScoreDialog
import java.util.*

class FinishScreen : AppCompatActivity() {
    internal lateinit var adapter: WordsAdapter
    internal var score: Int = 0
    internal lateinit var preferences: SharedPreferences
    internal lateinit var wordsArrayList: ArrayList<Pair>
    internal lateinit var activeTeam: Team
    internal lateinit var complexPreferences: ComplexPreferences
    internal var numberActiveTeam: Int = 0
    internal var teamSaved: Boolean = false
    internal var previousScore: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_finish_screen)
        val intent = intent
        wordsArrayList = intent.getSerializableExtra(Preferences.KEY_PAIR) as ArrayList<Pair>
        previousScore = 0
        adapter = WordsAdapter(this, wordsArrayList)
        listView.adapter = adapter
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        numberActiveTeam = preferences.getInt(Preferences.KEY_ACTIVE_TEAM, 1)
        complexPreferences = ComplexPreferences.getComplexPreferences(this, "all_teams", Context.MODE_PRIVATE)
        activeTeam = Preferences.getInstance(this).getTeamFromPreference(numberActiveTeam)!!
        teamSaved = false
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


    fun onNextRoundClick(view: View) {
        val checkedScore = checkScore()
        Preferences.getInstance(this).saveTeam(activeTeam, numberActiveTeam, teamSaved, false, checkedScore, previousScore)
        startActivity(Intent(this, StartScreen::class.java))
    }

    fun onShowScoreClick(view: View) {
        val checkedScore = checkScore()
        Preferences.getInstance(this).saveTeam(activeTeam, numberActiveTeam, teamSaved, false, checkedScore, previousScore)
        teamSaved = true
        previousScore = checkedScore
        val scoreDialog = ScoreDialog()
        scoreDialog.show(supportFragmentManager, "score")
    }


    fun checkScore(): Int {
        var checkedScore = 0
        for (pair in wordsArrayList) {
            if (pair.isGuess) {
                checkedScore++
            } else {
                checkedScore--
            }
        }
        return checkedScore
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }
}
