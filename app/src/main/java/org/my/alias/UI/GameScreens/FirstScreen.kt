package org.my.alias.UI.GameScreens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.start_layout.*
import org.my.alias.Preferences
import org.my.alias.Preferences.Companion.KEY_COMPLEXITY
import org.my.alias.Preferences.Companion.KEY_CONTINUE
import org.my.alias.Preferences.Companion.KEY_QANTITY_TEAMS_FROM_PREFS
import org.my.alias.Preferences.Companion.KEY_TIMER
import org.my.alias.R


class FirstScreen : AppCompatActivity() {
    internal lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_layout)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.edit().putBoolean(KEY_CONTINUE, false).apply()
        configUi()
        configCheckedListeners()
    }

    private fun configUi() {
        val compl = Integer.parseInt(preferences.getString(KEY_COMPLEXITY, "2")!!)
        complexity.check(compl)
        val teams = Integer.parseInt(preferences.getString(KEY_QANTITY_TEAMS_FROM_PREFS, "2")!!)
        team.check(teams + 3)
        val timeFromPrefs = preferences.getInt(KEY_TIMER, 60)
        when (timeFromPrefs) {
            30 -> time.check(8)
            45 -> time.check(9)
            60 -> time.check(10)
        }
    }

    private fun configCheckedListeners() {
        complexity.setOnCheckedChangeListener { radioGroup, i -> preferences.edit().putString(KEY_COMPLEXITY, i.toString()).apply() }
        team.setOnCheckedChangeListener { radioGroup, i -> preferences.edit().putString(KEY_QANTITY_TEAMS_FROM_PREFS, (i - 3).toString()).apply() }
        time.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                8 -> preferences.edit().putInt(KEY_TIMER, 30).apply()
                9 -> preferences.edit().putInt(KEY_TIMER, 45).apply()
                10 -> preferences.edit().putInt(KEY_TIMER, 60).apply()
            }
        }
    }

    fun onStartClick(view: View) {
        preferences.edit().putInt(Preferences.KEY_ACTIVE_TEAM, 1).apply()
        startActivity(Intent(this, StartScreen::class.java))
    }
}
