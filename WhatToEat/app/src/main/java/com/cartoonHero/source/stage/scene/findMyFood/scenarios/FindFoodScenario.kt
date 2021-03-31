package com.cartoonHero.source.stage.scene.findMyFood.scenarios

import android.app.Activity
import android.content.Context
import android.location.Location
import com.cartoonHero.source.actors.mathematician.Mathematician
import com.cartoonHero.source.actors.pilot.Pilot
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.*
import java.util.*

data class CenterPoint (
    var location: Location? = null,
    var zoomLevel: Int = 16
)
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class FindFoodScenario constructor(
    private val context: Context, private val activity: Activity
): Actor() {
    private var centerPt = CenterPoint()
    private fun beCheckGPSPermission(complete: (Boolean) -> Unit) {
        Pilot(context).toBeCheckPermission(this,activity) {
            CoroutineScope(Dispatchers.Main).launch {
                complete(it)
            }
        }
    }
    private fun beRequestCurrentLocation() {
        Pilot(context).toBeRequestLocationUpdates(
            this,0L,0.0F) {
                enable: Boolean, location: Location? ->
            if (enable && location != null) {
                centerPt.location = location
                Mathematician().toBeCalculateRange(
                    this,location,searchRange()) {

                }
            }
        }
    }

    private fun searchRange(): Float {
        return 0.0F
    }
}