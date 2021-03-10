package com.cartoonHero.source.stage.scene.addGourmets.scenarios

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.cartoonHero.source.actors.pilot.GeoCoder
import com.cartoonHero.source.actors.pilot.Pilot
import com.cartoonHero.source.redux.actions.MapRemoveAllAnnotationsAction
import com.cartoonHero.source.redux.appStore
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.*
import java.util.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi

class SearchLocationScenario
constructor(context: Context,activity: Activity): Actor() {
    private val mContext = context
    private val mActivity = activity

    fun toBeCheckGPSPermission(complete: (Boolean) -> Unit) {
        send {
            beCheckGPSPermission(complete)
        }
    }
    fun toBeRequestCurrentLocation(complete: (Location) -> Unit) {
        send {
            beRequestCurrentLocation(complete)
        }
    }
    fun toBeInquireIntoAddressesLocation(addressText: String) {
        send {
            beInquireIntoAddressesLocation(addressText)
        }
    }
    fun toBeInquireIntoLocationsAddress(location: Location,locale: Locale){
        send {
            beInquireIntoLocationsAddress(location, locale)
        }
    }

    private fun beGoogleSearchUrl(queryText: String, complete: (String) -> Unit) {
        val query = queryText.replace(" ", "+")
        val url = "https://www.google.co.in/search?q=$query"
        CoroutineScope(Dispatchers.Main).launch {
            complete(url)
        }
    }
    private fun beCheckGPSPermission(complete: (Boolean) -> Unit) {
        Pilot(mContext).toBeCheckPermission(this,mActivity) {
            CoroutineScope(Dispatchers.Main).launch {
                complete(it)
            }
        }
    }

    private fun beRequestCurrentLocation(complete: (Location) -> Unit) {
        Pilot(mContext).toBeRequestLocationUpdates(
            this,0L,0.0F) {
            enable: Boolean, location: Location? ->
            if (enable && location != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    complete(location)
                }
            }
        }
    }
    private fun beInquireIntoAddressesLocation(addressText: String) {
        GeoCoder(mContext).toBeGetFromLocationName(this,addressText) {
            val location = Location(LocationManager.GPS_PROVIDER)
            location.latitude = it.latitude
            location.longitude = it.longitude
            beInquireIntoLocationsAddress(location,it.locale)
            CoroutineScope(Dispatchers.Main).launch {
                appStore.dispatch(MapRemoveAllAnnotationsAction())
            }
        }
    }
    private fun beInquireIntoLocationsAddress(location: Location,locale: Locale) {
        GeoCoder(mContext).toBeGetFromLocation(this,location,locale) {

        }
    }
}