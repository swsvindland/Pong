package com.svindland.sam.pong

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Player2(private val image: Bitmap) {
    var x: Int
    var y: Int
    val w: Int
    val h: Int

    var score = 0

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        w = image.width
        h = image.height

        x = screenWidth / 2
        y = 200
    }


    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    fun updateTouch(touch_x: Int) {
        if (x > screenWidth - image.width ) {
            x = screenWidth - image.width
        }

        if ( x < 0) {
            x = 0
        }

        x += touch_x
    }
}