package com.svindland.sam.pong

import android.content.res.Resources
import android.graphics.*

class Ball(var image: Bitmap) {
    var x: Int = 0
    var y: Int = 0
    var w: Int = 0
    var h: Int = 0
    private var xVelocity = 20
    private var yVelocity = 20
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private var screenHeight = Resources.getSystem().displayMetrics.heightPixels

    val paint: Paint = Paint()

    init {
        w = image.width
        h = image.height

        x = screenWidth / 2
        y = screenHeight / 2

        paint.setColor(Color.WHITE)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
        canvas.drawLine(0f, (screenHeight / 2).toFloat(), screenWidth.toFloat(), (screenHeight / 2).toFloat(), paint)
    }

    fun update(player1: Player1, player2: Player2) {
        // ball hit wall
        if (x > screenWidth - image.width || x < 0) {
            xVelocity = xVelocity * -1
        }

        if ((player1.y - player1.h / 2) - h / 2 <= y  && y <= (player1.y + player1.h / 2) + h / 2 &&
             (player1.x - player1.w / 2) <= x && x <= (player1.x + player1.w / 2)) {

            yVelocity = yVelocity * -1
        }

        if ((player2.y - player2.h / 2) - h / 2 <= y  && y <= (player2.y + player2.h / 2) + h / 2 &&
                (player2.x - player2.w / 2) <= x && x <= (player2.x + player2.w / 2)) {

            yVelocity = yVelocity * -1
        }

        // player 2 score
        if (y > screenHeight - image.height) {
            player2.score += 1
            xVelocity = 0
            yVelocity = 0

            reset()
        }

        // player 1 score
        if (y < 0) {
            player1.score += 1
            xVelocity = 0
            yVelocity = 0

            reset()
        }

        x += xVelocity
        y += yVelocity
    }

    fun reset() {
        x = screenWidth / 2
        y = screenHeight / 2

        xVelocity = 20
        yVelocity = 20
    }
}