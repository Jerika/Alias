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
import kotlinx.android.synthetic.main.last_word_dialog.view.*
import org.my.alias.Pair
import org.my.alias.Preferences
import org.my.alias.R
import org.my.alias.Team
import org.my.alias.UI.GameScreens.FinishScreen
import java.util.*

class LastWordDialog : DialogFragment{
    private lateinit var playWords: ArrayList<Pair>
    private var score = 0
    private lateinit var word: String
    private var checkedRadio: Int = 0
    private var complexPreferences: ComplexPreferences? = null
    private var sharedPreferences: SharedPreferences? = null

    private val checkedTeam: Team?
        get() {
            when (checkedRadio) {
                R.id.first_team -> return complexPreferences!!.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
                R.id.second_team -> return complexPreferences!!.getObject<Team>(Preferences.KEY_SECOND, Team::class.java)
                R.id.thurd_team -> return complexPreferences!!.getObject<Team>(Preferences.KEY_THIRD, Team::class.java)
                R.id.fouth_team -> return complexPreferences!!.getObject<Team>(Preferences.KEY_FOURTH, Team::class.java)
                R.id.none -> return null
                else -> return complexPreferences!!.getObject<Team>(Preferences.KEY_FIRST, Team::class.java)
            }
        }

    constructor() : super() {}

    @SuppressLint("ValidFragment")
    constructor(word: String, pair: ArrayList<Pair>, score: Int) : super() {
        this.word = word
        this.playWords = pair
        this.score = score
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(SAVE_KEY_WORD, word)
        outState.putInt(SAVE_KEY_SCORE, score)
        outState.putSerializable(SAVE_KEY_PLAYWORDS, playWords)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(activity)
        val v = inflater.inflate(R.layout.last_word_dialog, null)
        confifUi(v)
        return AlertDialog.Builder(context!!, R.style.AlertDialogStyle).setView(v).setTitle(getString(R.string.who_guessed, word.toUpperCase()))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    val team = checkedTeam
                    if (team != null) {
                        if (team.number == sharedPreferences!!.getInt(Preferences.KEY_ACTIVE_TEAM, 1)) {
                            playWords.add(Pair(word, true))
                        } else {
                            activity?.let { Preferences.getInstance(it).saveTeam(team, team.number, true, true, 0, 0) }
                        }
                    }
                    val intent = Intent(activity, FinishScreen::class.java)
                    intent.putExtra(Preferences.KEY_PAIR, playWords)
                    intent.putExtra(Preferences.KEY_SCORE, score)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    activity!!.finish()
                    dismiss()
                }.create()
    }

    fun confifUi(view: View) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        complexPreferences = ComplexPreferences.getComplexPreferences(activity, "all_teams", Context.MODE_PRIVATE)
        when (Integer.parseInt(sharedPreferences!!.getString(Preferences.KEY_QANTITY_TEAMS_FROM_PREFS, "2")!!)) {
            2 -> {
                view.thurd_team.visibility = View.INVISIBLE
                view.fouth_team.visibility = View.INVISIBLE
            }
            3 -> view.fouth_team.visibility = View.INVISIBLE
        }
        view.guessed_radio.setOnCheckedChangeListener { _, i ->
            checkedRadio = i
        }
    }

    companion object {
        private val SAVE_KEY_WORD = "word"
        private val SAVE_KEY_PLAYWORDS = "playWords"
        private val SAVE_KEY_SCORE = "score"
    }

}
