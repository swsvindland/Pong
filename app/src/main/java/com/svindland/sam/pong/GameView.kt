package com.svindland.sam.pong

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import java.util.*

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {
    private val thread: GameThread
    private var ball: Ball? = null
    private var player1: Player1? = null
    private var player2: Player2? = null

    private var touched: Boolean = false
    private var touched_x: Int = 0
    private var p2touched_x: Int = 0

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    private val paint: Paint = Paint()

    private var difficulty: Int = 50
    private var win = 10

    init {
        holder.addCallback(this)

        thread = GameThread(holder, this)

        paint.setColor(Color.WHITE)

        val diff = MainActivity().diff
        if (diff == "I Suck") {difficulty = 0}
        else if(diff == "Easy") {difficulty = 25}
        else if(diff == "Medium") {difficulty = 50}
        else if(diff == "Hard") {difficulty = 75}
        else {difficulty = 100}

        win = MainActivity().win
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        ball = Ball(BitmapFactory.decodeResource(resources, R.drawable.ball))
        player1 = Player1(BitmapFactory.decodeResource(resources, R.drawable.bat))
        player2 = Player2(BitmapFactory.decodeResource(resources, R.drawable.bat))

        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    fun update() {
        ball!!.update(player1!!, player2!!)

        if(touched) {
            player1!!.updateTouch(touched_x)
        }

        p2touched_x = rand(-difficulty, difficulty) // really bad rng AI
        player2!!.updateTouch(p2touched_x)

        if (player1!!.score >= win) {
            player1!!.score = 0
            player2!!.score = 0
        }

        if(player2!!.score >= win) {
            player1!!.score = 0
            player2!!.score = 0
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        player1!!.draw(canvas)
        player2!!.draw(canvas)
        ball!!.draw(canvas)

        paint.setTextSize(150f);
        canvas.drawText("${player1!!.score}", 100f, (screenHeight / 2).toFloat(), paint)
        canvas.drawText("${player2!!.score}", (screenWidth - 200).toFloat(), (screenHeight / 2).toFloat(), paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.getX() > Resources.getSystem().displayMetrics.widthPixels / 2) {
            touched_x = 25
        } else {
            touched_x = -25
        }

        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> touched = true
            MotionEvent.ACTION_MOVE -> touched = true
            MotionEvent.ACTION_UP -> touched = false
            MotionEvent.ACTION_CANCEL -> touched = false
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }



        return true
    }

    val random = Random()

    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }
}