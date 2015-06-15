package org.my.alias.UI.GameScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.my.alias.Pair;
import org.my.alias.Preferences;
import org.my.alias.R;
import org.my.alias.UI.ScoreDialog;
import org.my.alias.Team;
import org.my.alias.WordsAdapter;

import java.util.ArrayList;

import br.com.kots.mob.complex.preferences.ComplexPreferences;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class FinishScreen extends AppCompatActivity {
    @InjectView (R.id.listView) ListView list;
    WordsAdapter adapter;
    int score;
    SharedPreferences preferences;
    ArrayList<Pair> wordsArrayList;
    Team activeTeam;
    ComplexPreferences complexPreferences;
    int numberActiveTeam;
    boolean teamSaved;
    int previousScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_finish_screen);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        wordsArrayList = (ArrayList<Pair>) intent.getSerializableExtra(Preferences.KEY_PAIR);

        previousScore = 0;
        adapter = new WordsAdapter(this, wordsArrayList);
        list.setAdapter(adapter);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        numberActiveTeam = preferences.getInt(Preferences.KEY_ACTIVE_TEAM, 1);
        complexPreferences = ComplexPreferences.getComplexPreferences(this, "all_teams", MODE_PRIVATE);
        activeTeam = Preferences.getInstance(this).getTeamFromPreference(numberActiveTeam);
        teamSaved = false;
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
                startActivity(new Intent(this, FirstScreen.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


public void onNextRoundClick(View view) {
        int checkedScore = checkScore();
        Preferences.getInstance(this).saveTeam(activeTeam, numberActiveTeam, teamSaved, false, checkedScore, previousScore);
        startActivity(new Intent(this, StartScreen.class));
    }

    public void onShowScoreClick(View view) {
        int checkedScore = checkScore();
        Preferences.getInstance(this).saveTeam(activeTeam, numberActiveTeam, teamSaved, false, checkedScore, previousScore);
        teamSaved = true;
        previousScore = checkedScore;
        ScoreDialog scoreDialog = new ScoreDialog();
        scoreDialog.show(getFragmentManager(), "score");
    }


    public int checkScore(){
        int checkedScore = 0;
        for (Pair pair:wordsArrayList){
            if (pair.isGuess()){
                checkedScore++;
            }else{
                checkedScore--;
            }
        }
        return checkedScore;
    }


}
