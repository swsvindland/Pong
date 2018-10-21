package com.svindland.sam.pong

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView2(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {
    private val thread: GameThread2
    private var ball: Ball? = null
    private var player1: Player1? = null
    private var player2: Player2? = null

    private var touched: Boolean = false
    private var p2touched: Boolean = false
    private var touched_x: Int = 0
    private var p2touched_x: Int = 0

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    private val paint: Paint = Paint()

    private var win: Int = 10

    init {
        holder.addCallback(this)

        thread = GameThread2(holder, this)

        paint.setColor(Color.WHITE)

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

        if (p2touched) {
            player2!!.updateTouch(p2touched_x)
        }

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
        if (event.getY() < Resources.getSystem().displayMetrics.heightPixels / 2) {
            if (event.getX() > Resources.getSystem().displayMetrics.widthPixels / 2) {
                p2touched_x = 25
            } else {
                p2touched_x = -25
            }

            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> p2touched = true
                MotionEvent.ACTION_MOVE -> p2touched = true
                MotionEvent.ACTION_UP -> p2touched = false
                MotionEvent.ACTION_CANCEL -> p2touched = false
                MotionEvent.ACTION_OUTSIDE -> p2touched = false
            }
        }
        if (event.getY() > Resources.getSystem().displayMetrics.heightPixels / 2){
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
        }

        return true
    }
}