package org.my.alias.UI.GameScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.my.alias.AutoResizeTextView;
import org.my.alias.DatabaseHelper;
import org.my.alias.Pair;
import org.my.alias.Preferences;
import org.my.alias.R;

import org.my.alias.UI.LastWordDialog;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    @InjectView (R.id.ok)Button ok;
    @InjectView (R.id.skip)Button skip;
    @InjectView (R.id.word)AutoResizeTextView word;

    int score;
    @InjectView (R.id.timer)TextView timer;
    DatabaseHelper helper;
    CountDownTimer countDownTimer;
    int duration;
    boolean finish = false;

    ArrayList<Pair> playWords = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        duration = Integer.parseInt(sharedPreferences.getString("duration", "60"));

        ok.setOnClickListener(this);
        skip.setOnClickListener(this);
        Typeface type = Typeface.createFromAsset(getAssets(),"a_stamper.ttf");
        word.setTypeface(type);

        timer.setTypeface(type);
        score = 0;
        helper = new DatabaseHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();
        word.setText(helper.getWords());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.restart:
                countDownTimer.cancel();
                startActivity(new Intent(this, FirstScreen.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        word.setText(savedInstanceState.getString(Preferences.KEY_WORD));
        duration = savedInstanceState.getInt(Preferences.KEY_TIMER);
        playWords = (ArrayList<Pair>) savedInstanceState.getSerializable(Preferences.KEY_PAIR);
        score = savedInstanceState.getInt(Preferences.KEY_SCORE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(!finish){
            outState.putInt(Preferences.KEY_TIMER, Integer.parseInt(String.valueOf(timer.getText())));
        }
        outState.putString(Preferences.KEY_WORD, String.valueOf(word.getText()));
        outState.putSerializable(Preferences.KEY_PAIR, playWords);
        outState.putInt(Preferences.KEY_SCORE, score);
        countDownTimer.cancel();
        super.onSaveInstanceState(outState);
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

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        countDownTimer = new CountDownTimer(duration* 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }
            public void onFinish() {
                finish = true;
                LastWordDialog lastWordDialog = new LastWordDialog(word.getText(), playWords, score);
                lastWordDialog.setCancelable(false);
                lastWordDialog.show(getFragmentManager(), "lastWord");
                /*Intent intent = new Intent(getApplicationContext(), FinishScreen.class);
                intent.putExtra(KEY_PAIR, playWords);
                intent.putExtra(KEY_SCORE, score);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                //finish();
            }
        }
                .start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        countDownTimer.cancel();
        super.onPause();
    }
}
