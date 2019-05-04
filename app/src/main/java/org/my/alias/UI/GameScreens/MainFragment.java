package org.my.alias.UI.GameScreens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.my.alias.DatabaseHelper;
import org.my.alias.Pair;
import org.my.alias.R;
import org.my.alias.UI.CustomView.AutoResizeTextView;
import org.my.alias.UI.CustomView.CircleProgressBar;
import org.my.alias.UI.LastWordDialog;
import org.my.alias.UI.card.ListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import swipeable.com.layoutmanager.OnItemSwiped;
import swipeable.com.layoutmanager.SwipeableLayoutManager;
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback;
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper;

public class MainFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.custom_progressBar)
    CircleProgressBar CircleBar;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.skip)
    Button skip;
    private int score;
    private CountDownTimer countDownTimer;
    private int duration;
    private boolean finish = false;
    private double full;
    private ArrayList<Pair> playWords = new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;
    private ListAdapter adapter;
    private DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDatabase();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        duration = Integer.parseInt(sharedPreferences.getString("duration", "60"));
        full = 100 / (double) duration;
        createTimer();
        countDownTimer.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_layout, null);
        ButterKnife.bind(this, view);
        ArrayList<String> words = helper.get10Words();
        adapter = new ListAdapter(words, getContext());

        itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new SwipeableLayoutManager().setAngle(10)
                .setAnimationDuratuion(450)
                .setMaxShowCount(3)
                .setScaleGap(0.1f)
                .setTransYGap(0));
        recyclerView.setAdapter(adapter);
        setTypeface();
        score = 0;
        CircleBar.setProgress(100);
        ok.setOnClickListener(this);
        skip.setOnClickListener(this);
        return view;
    }

    private SwipeableTouchHelperCallback swipeableTouchHelperCallback =
            new SwipeableTouchHelperCallback(new OnItemSwiped() {
                @Override
                public void onItemSwiped() {
                    adapter.removeTopItem();
                    adapter.addItem(helper.getWords());
                }

                @Override
                public void onItemSwipedLeft() {
                    Pair notGuessed = new Pair(adapter.getCurrent(), false);
                    playWords.add(notGuessed);
                    score--;
                }

                @Override
                public void onItemSwipedRight() {
                    Pair guessed = new Pair(adapter.getCurrent(), true);
                    playWords.add(guessed);
                    score++;
                }

                @Override
                public void onItemSwipedUp() {

                }

                @Override
                public void onItemSwipedDown() {

                }
            }) {
                @Override
                public int getAllowedSwipeDirectionsMovementFlags(RecyclerView.ViewHolder viewHolder) {
                    return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                }
            };

    private void openDatabase() {
        helper = new DatabaseHelper(getActivity());
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }

    private void setTypeface() {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "a_stamper.ttf");
        timer.setTypeface(type);
    }

    private void createTimer() {
        countDownTimer = new CountDownTimer(duration * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                timer.setText(String.valueOf(sec));
                float progressBar = (float) (full * sec);
                CircleBar.setProgressWithAnimation(progressBar);
            }

            public void onFinish() {
                if (!finish) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.stop);
                    mediaPlayer.start();
                }
                finish = true;
                LastWordDialog lastWordDialog = new LastWordDialog(adapter.getCurrent(), playWords, score);
                lastWordDialog.setCancelable(false);
                lastWordDialog.show(getFragmentManager(), "lastWord");
            }
        };
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                itemTouchHelper.swipe(recyclerView.findViewHolderForAdapterPosition(0),
                        ItemTouchHelper.RIGHT);
                disableButton(ok);
                disableButton(skip);
                break;
            case R.id.skip:
                itemTouchHelper.swipe(recyclerView.findViewHolderForAdapterPosition(0),
                        ItemTouchHelper.LEFT);
                disableButton(ok);
                disableButton(skip);
                break;
        }
    }

    private void disableButton(Button button) {
        button.setClickable(false);
        button.postDelayed(() -> button.setClickable(true),300);
    }

}
