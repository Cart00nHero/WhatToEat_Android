package com.cartoonHero.source.actors.mathematician

import android.location.Location
import com.apollographql.apollo.api.Input
import com.cartoonHero.source.enities.GQSearchRange
import type.InputCoordinate
import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

class Haversine {
    private val earthRadius = 6371.0
    fun calculateRange(location:Location, range: Float): GQSearchRange {
        val dLng = dLongitude(location,range)
        val dLat = dLatitude(location, range)
        val maxLat = location.latitude + dLat
        val minLat = location.latitude - dLat
        val maxLng = location.longitude + dLng
        val minLng = location.longitude-dLng
        val maxCoordinate = InputCoordinate(
            latitude = Input.optional(maxLat.toBigDecimal()),
            longitude = Input.optional(maxLng.toBigDecimal())
        )
        val minCoordinate = InputCoordinate(
            latitude = Input.optional(minLat.toBigDecimal()),
            longitude = Input.optional(minLng.toBigDecimal())
        )
        return GQSearchRange(max = maxCoordinate,min = minCoordinate)
    }
    private fun dLongitude(location:Location, range: Float): Double {
        val dLng =
            2 * asin(sin(range/(2*earthRadius)/ cos(deg2rad(location.latitude))))
        return rad2deg(dLng)
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