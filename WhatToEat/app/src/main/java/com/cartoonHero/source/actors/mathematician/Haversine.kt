package com.cartoonHero.source.actors.mathematician

import android.location.Location
import kotlin.math.PI

class Haversine {
    private val earthRadius = 6371.0

    private fun dLongitude(location:Location, range: Float): Double {
        return 0.0
    }
    private fun dLatitude(location:Location, range: Float): Double {
        val dLat = range / earthRadius
        return rad2deg(dLat)
    }
    private fun deg2rad(number: Double): Double {
        return number * PI / 180.0
    }
    private fun rad2deg(number: Double): Double {
        return number * 180.0 / PI
    }
}