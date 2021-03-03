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
class Pilot constructor(context: Context) : Actor() {
    private val mContext = context
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun toBeCheckPermission(
        sender: Actor,activity: Activity, complete: (Boolean) -> Unit) {
        send {
            beCheckPermission(sender,activity, complete)
        }
    }
    fun toBeRequestCurrentLocation(
        sender: Actor,
        complete: (enable:Boolean,location:Location?) -> Unit) {
        send {
            beRequestCurrentLocation(sender,complete)
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

    private fun beRequestCurrentLocation(sender: Actor,
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
                        LocationManager.GPS_PROVIDER,0L,0.0f
                    ) {
                        send {
                            complete(true,it)
                        }
                    }
                }
                network -> {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,0L,0.0f) {
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
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
}