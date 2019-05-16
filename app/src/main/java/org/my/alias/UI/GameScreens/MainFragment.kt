package org.my.alias.UI.GameScreens

import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_layout.*
import org.my.alias.DatabaseHelper
import org.my.alias.Pair
import org.my.alias.Preferences.Companion.KEY_TIMER
import org.my.alias.R
import org.my.alias.UI.LastWordDialog
import org.my.alias.UI.card.ListAdapter
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper
import java.io.IOException
import java.util.*

class MainFragment : Fragment(), View.OnClickListener {
    private var score: Int = 0
    private var countDownTimer: CountDownTimer? = null
    private var duration: Int = 0
    private var finish = false
    private var full: Double = 0.toDouble()
    private val playWords = ArrayList<Pair>()
    private var itemTouchHelper: ItemTouchHelper? = null
    private var adapter: ListAdapter? = null
    private var helper: DatabaseHelper? = null

    private val swipeableTouchHelperCallback = object : SwipeableTouchHelperCallback(object : OnItemSwiped {
        override fun onItemSwiped() {
            adapter!!.removeTopItem()
            adapter!!.addItem(helper!!.words!!)
        }

        override fun onItemSwipedLeft() {
            val notGuessed = Pair(adapter!!.current, false)
            playWords.add(notGuessed)
            score--
        }

        override fun onItemSwipedRight() {
            val guessed = Pair(adapter!!.current, true)
            playWords.add(guessed)
            score++
        }

        override fun onItemSwipedUp() {

        }

        override fun onItemSwipedDown() {

        }
    }) {
        override fun getAllowedSwipeDirectionsMovementFlags(viewHolder: RecyclerView.ViewHolder?): Int {
            return ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openDatabase()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        duration = sharedPreferences.getInt(KEY_TIMER, 60)
        full = 100 / duration.toDouble()
        createTimer()
        countDownTimer!!.start()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val words = helper!!.tenWords
        adapter = ListAdapter(words, context!!)

        itemTouchHelper = ItemTouchHelper(swipeableTouchHelperCallback)
        itemTouchHelper!!.attachToRecyclerView(recycler_view)
        recycler_view.layoutManager = SwipeableLayoutManager().setAngle(10)
                .setAnimationDuratuion(450)
                .setMaxShowCount(3)
                .setScaleGap(0.1f)
                .setTransYGap(0)
        recycler_view.adapter = adapter
        setTypeface()
        score = 0
        custom_progressBar.setProgressWithInvalidate(100f)
        ok.setOnClickListener(this)
        skip.setOnClickListener(this)
    }

    private fun openDatabase() {
        helper = activity?.let { DatabaseHelper(it) }
        try {
            helper!!.createDataBase()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        helper!!.openDataBase()

    }

    private fun setTypeface() {
        val type = Typeface.createFromAsset(activity!!.assets, "a_stamper.ttf")
        timer.typeface = type
    }

    private fun createTimer() {
        countDownTimer = object : CountDownTimer((duration * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished / 1000
                timer.text = sec.toString()
                val progressBar = (full * sec).toFloat()
                custom_progressBar.setProgressWithAnimation(progressBar)
            }

            override fun onFinish() {
                if (!finish) {
                    val mediaPlayer = MediaPlayer.create(activity, R.raw.stop)
                    mediaPlayer.start()
                }
                finish = true
                val lastWordDialog = LastWordDialog(adapter!!.current, playWords, score)
                lastWordDialog.isCancelable = false
                lastWordDialog.show(fragmentManager, "lastWord")
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ok -> {
                itemTouchHelper!!.swipe(recycler_view.findViewHolderForAdapterPosition(0),
                        ItemTouchHelper.RIGHT)
                disableButton(ok)
                disableButton(skip)
            }
            R.id.skip -> {
                itemTouchHelper!!.swipe(recycler_view!!.findViewHolderForAdapterPosition(0),
                        ItemTouchHelper.LEFT)
                disableButton(ok)
                disableButton(skip)
            }
        }
    }

    private fun disableButton(button: Button) {
        button.isClickable = false
        button.postDelayed({ button.isClickable = true }, 500)
    }
}
