package com.cartoonHero.source.actors.pilot

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class GeoCoder constructor(context: Context): Actor() {
    private val mContext = context
    fun toBeGetFromLocationName(
        sender: Actor, addressText: String,
        complete: (Address) -> Unit) {
        send {
            beGetFromLocationName(sender,addressText,complete)
        }
    }
    fun toBeGetFromLocation(
        sender: Actor,location: Location,
        locale: Locale,
        complete: (Address) -> Unit) {
        beGetFromLocation(sender,location,locale,complete)
    }
    private fun beGetFromLocationName(
        sender: Actor, addressText: String,
        complete: (Address) -> Unit) {
        val geoCoder = Geocoder(mContext, Locale.getDefault())
        val results = geoCoder.getFromLocationName(addressText,1)
        if (results.count() > 0) {
            sender.send {
                complete(results.first())
            }
        }
    }
    private fun beGetFromLocation(
        sender: Actor,location: Location,
        locale: Locale,
        complete: (Address) -> Unit) {
        val geoCoder = Geocoder(mContext, locale)
        val results = geoCoder.getFromLocation(
            location.latitude,location.longitude,1)
        if (results.count() > 0) {
            sender.send {
                complete(results.first())
            }
        }
    }
}