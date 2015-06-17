package org.my.alias.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.my.alias.R;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment(), "prefs").commit();
    }

    @Override
    public void onBackPressed() {
        SettingsFragment fragment = (SettingsFragment) getFragmentManager().findFragmentByTag("prefs");
        if (fragment.reallyChecked()){
            super.onBackPressed();
        }else {
            Toast.makeText(this, getString(R.string.choose_complexity),Toast.LENGTH_SHORT).show();
        }
    }
}
