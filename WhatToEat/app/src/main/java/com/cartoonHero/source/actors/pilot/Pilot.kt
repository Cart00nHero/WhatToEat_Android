package com.cartoonHero.source.actors.pilot

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class Pilot constructor(context: Context) : Actor() {
    private val mContext = context
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun toBeCheckPermission(sender: Actor, complete: (Boolean) -> Unit) {
        send {
            beCheckPermission(sender, complete)
        }
    }
    fun toBeRequestPermission(activity: Activity) {
        send {
            beRequestPermission(activity)
        }
    }

    private fun beCheckPermission(sender: Actor, complete: (Boolean) -> Unit) {
        val accessCoarseLocation =
            ActivityCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
        val accessFineLOCATION =
            ActivityCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
        when {
            accessCoarseLocation == PackageManager.PERMISSION_GRANTED ->
                sender.send { complete(true) }
            accessFineLOCATION == PackageManager.PERMISSION_GRANTED ->
                sender.send { complete(true) }
            else -> sender.send { complete(false) }
        }
    }

    private fun beRequestPermission(activity: Activity) {
        val permissionId = 1000    //可隨意自訂一個唯一的整數
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            permissionId
        )
    }

    private fun beRequestCurrentLocation() {
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (gps || network) {
            when {
                gps -> {
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER)
                }
            }
        }
    }
}