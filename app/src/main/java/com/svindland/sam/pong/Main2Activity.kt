package com.svindland.sam.pong

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.WindowManager

class Main2Activity : Activity() {
    var win: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        win = sharedPref.getString("score_to_win", "10").toInt()

        setContentView(R.layout.activity_main2)
    }
}
