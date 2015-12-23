package org.my.alias.UI.GameScreens;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.my.alias.DatabaseHelper;
import org.my.alias.Pair;
import org.my.alias.R;
import org.my.alias.UI.CustomView.AutoResizeTextView;
import org.my.alias.UI.CustomView.CircleProgressBar;
import org.my.alias.UI.LastWordDialog;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.ok)
    Button ok;
    @InjectView (R.id.skip)
    Button skip;
    @InjectView (R.id.word)
    AutoResizeTextView word;
    @InjectView(R.id.custom_progressBar)
    CircleProgressBar CircleBar;
    @InjectView (R.id.timer)
    TextView timer;
    int score;
    DatabaseHelper helper;
    CountDownTimer countDownTimer;
    int duration;
    boolean finish = false;
    double full;
    ArrayList<Pair> playWords = new ArrayList<>();

    public MainFragment() {
        super();
        setRetainInstance(true);
    }

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
        View view = inflater.inflate(R.layout.activity_main, null);
        ButterKnife.inject(this, view);
        ok.setOnClickListener(this);
        skip.setOnClickListener(this);
        setTypeface();
        score = 0;
        word.setText(helper.getWords());
        CircleBar.setProgress(100);
        return view;
    }

    private void setTypeface() {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "a_stamper.ttf");
        word.setTypeface(type);
        timer.setTypeface(type);
    }

    private void openDatabase() {
        helper = new DatabaseHelper(getActivity());
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }

    public void createTimer(){
        countDownTimer = new CountDownTimer(duration* 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                timer.setText(String.valueOf(sec));
                float progressBar = (float) (full*sec);
                CircleBar.setProgressWithAnimation(progressBar);
            }
            public void onFinish() {
                if (!finish){
                    MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.stop);
                    mediaPlayer.start();
                }
                finish = true;
                LastWordDialog lastWordDialog = new LastWordDialog(word.getText(), playWords, score);
                lastWordDialog.setCancelable(false);
                lastWordDialog.show(getFragmentManager(), "lastWord");
            }
        };
    }

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                Pair guessed = new Pair(word.getText().toString(), true);
                playWords.add(guessed);
                score++;
                word.setTextSize(50);
                word.setText(helper.getWords());
                break;
            case R.id.skip:
                Pair notGuessed = new Pair(word.getText().toString(), false);
                playWords.add(notGuessed);
                score--;
                word.setText(helper.getWords());
                break;
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
