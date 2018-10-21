package com.svindland.sam.pong

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        val inflator: MenuInflater = menuInflater
        inflator.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        else if(item.itemId == R.id.about) {
            Toast.makeText(this, "Pong with Friends v1 \n Sam Svindland", Toast.LENGTH_SHORT).show()
            return true
        }
        else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun singlePlayer(view: View) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun twoPlayer(view: View) {
        val intent: Intent = Intent(this, Main2Activity::class.java)
        startActivity(intent)
    }
}
