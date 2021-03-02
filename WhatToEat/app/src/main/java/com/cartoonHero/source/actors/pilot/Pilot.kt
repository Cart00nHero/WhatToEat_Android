package com.cartoonHero.source.actors.pilot

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
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

    // private methods
    private fun requestPermission(activity: Activity) {
        val permissionId = 1000    //可隨意自訂一個唯一的整數
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            permissionId
        )
    }
    // behaviors
    private fun beCheckPermission(
        sender: Actor,activity: Activity, complete: (Boolean) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            sender.send { complete(false) }
            CoroutineScope(Dispatchers.Main).launch {
                requestPermission(activity)
            }
        } else {
            sender.send { complete(true) }
        }
    }

    @SuppressLint("MissingPermission")
    private fun beRequestCurrentLocation() {
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (gps || network) {
            when {
                gps -> {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,10.toLong(),0.0f
                    ) { TODO("Not yet implemented") }
                }
            }
        }
    }
}