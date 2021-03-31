package com.cartoonHero.source.actors.pilot

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class Pilot constructor(private val context: Context) : Actor() {
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun toBeCheckPermission(
        sender: Actor,activity: Activity, complete: (Boolean) -> Unit) {
        send {
            beCheckPermission(sender,activity, complete)
        }
    }
    fun toBeRequestLocationUpdates(
        sender: Actor,minTime: Long,minDistance: Float,
        complete: (enable:Boolean,location:Location?) -> Unit) {
        send {
            beRequestLocationUpdates(sender,minTime,minDistance,complete)
        }

    }

    // behaviors
    private fun beCheckPermission(
        sender: Actor,activity: Activity, complete: (Boolean) -> Unit) {
        if (checkPermission()) {
            sender.send { complete(true) }
        } else {
            sender.send {
                complete(false)
                CoroutineScope(Dispatchers.Main).launch {
                    requestPermission(activity)
                }
            }
        }
    }

    private fun beRequestLocationUpdates(
        sender: Actor,minTime: Long,minDistance: Float,
        complete: (enable:Boolean,location:Location?) -> Unit) {
        if (!checkPermission()) {
            sender.send {
                complete(false,null)
            }
            return
        }
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (gps || network) {
            when {
                gps -> {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,minTime,minDistance
                    ) {
                        send {
                            complete(true,it)
                        }
                    }
                }
                network -> {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,minTime,minDistance) {
                        send {
                            complete(true,it)
                        }
                    }
                }
            }
        }
    }
    // private methods
    private fun requestPermission(activity: Activity) {
        val permissionId = 1000
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
            permissionId
        )
    }
    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
}