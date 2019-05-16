package org.my.alias.UI.GameScreens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import org.my.alias.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment(), "prefs").commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
