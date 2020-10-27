package com.cartoonhero.source.actors.inlineCls

import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import kotlin.math.roundToInt

// extension property to get display metrics instance
val Activity.displayMetrics: DisplayMetrics
    get() {
        // display metrics is a structure describing general information
        // about a display, such as its size, density, and font scaling
        val displayMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= 30) {
            windowManager.defaultDisplay.apply {
                getRealMetrics(displayMetrics)
            }

        } else {
            // getMetrics() method was deprecated in api level 30
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        return displayMetrics
    }


// extension property to get screen width and height in dp
val Activity.screenSizeInDp: Point
    get() {
        val point = Point()
        displayMetrics.apply {
            // screen width in dp
            point.x = (widthPixels / density).roundToInt()

            // screen height in dp
            point.y = (heightPixels / density).roundToInt()
        }

        return point
    }