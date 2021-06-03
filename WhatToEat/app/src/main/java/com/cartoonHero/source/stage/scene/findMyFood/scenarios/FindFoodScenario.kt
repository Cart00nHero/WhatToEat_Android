package com.cartoonHero.source.stage.scene.findMyFood.scenarios

import android.app.Activity
import android.content.Context
import android.location.Location
import com.cartoonHero.source.actors.mathematician.Mathematician
import com.cartoonHero.source.actors.pilot.Pilot
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.*

data class CenterPoint (
    var location: Location? = null,
    var zoomLevel: Int = 16
)
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class FindFoodScenario constructor(
    private val context: Context, private val activity: Activity
): Actor() {
    private val pilot = Pilot(context)
    private var centerPt = CenterPoint()
    private var lastSearchPt = CenterPoint()
    private var lastQueryData:
            List<SearchForRangeQuery.SearchForRange?>? = null

    fun toBeCheckGPSPermission(complete: (Boolean) -> Unit) {
        send {
            beCheckGPSPermission(complete)
        }
    }
    private fun beCheckGPSPermission(complete: (Boolean) -> Unit) {
        pilot.toBeCheckPermission(this,activity) {
            CoroutineScope(Dispatchers.Main).launch {
                complete(it)
            }
        }
    }

    fun toBeRequestCurrentLocation() {
        send {
            beRequestCurrentLocation()
        }
    }
    private fun beRequestCurrentLocation() {
        pilot.toBeRequestLocationUpdates(
            this,0L,0.0F) {
                enable: Boolean, location: Location? ->
            if (enable && location != null) {
                centerPt.location = location
                Mathematician().toBeCalculateRange(
                    this,location,searchRange(16)) {
                }
            }
        }
    }

    fun toBeStoreQueryData(
        queryData: List<SearchForRangeQuery.SearchForRange?>) {
        send {
            toBeStoreQueryData(queryData)
        }
    }
    private fun beStoreQueryData(
        queryData: List<SearchForRangeQuery.SearchForRange?>) {
        lastQueryData = queryData
    }

    fun toBeUpdateCenterPoint(
        zoomLevel: Int, location: Location?,
        complete: (CenterPoint) -> Unit?) {
        send {
            beUpdateCenterPoint(zoomLevel, location, complete)
        }
    }
    private fun beUpdateCenterPoint(
        zoomLevel: Int, location: Location?, complete: (CenterPoint) -> Unit?) {
        if (zoomLevel > -1) {
            centerPt.zoomLevel = zoomLevel
        }
        if (location != null) {
            centerPt.location = location
        }
        complete(centerPt)
    }

    @Suppress("SameParameterValue")
    private fun searchRange(zoomLevel: Int): Float {
        // KM
        if (zoomLevel >= 17) {
            return 0.2F
        }
        if (zoomLevel == 16) {
            return 0.5F
        }
        if (zoomLevel <= 15) {
            return 1.0F
        }
        return 0.0F
    }
}