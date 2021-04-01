package com.cartoonHero.source.stage.scene.shareGourmets.scenarios

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.cartoonHero.source.actors.dataManger.DataManager
import com.cartoonHero.source.actors.express.LogisticsCenter
import com.cartoonHero.source.actors.pilot.GeoCoder
import com.cartoonHero.source.actors.pilot.Pilot
import com.cartoonHero.source.props.isoNationCodeToLocale
import com.cartoonHero.source.props.enities.Parcel
import com.cartoonHero.source.props.enities.initGQInputObject
import com.cartoonHero.source.redux.actions.FoundLocationsAddressAction
import com.cartoonHero.source.redux.actions.MapRemoveAllAnnotationsAction
import com.cartoonHero.source.redux.actions.locationsDynamicQueryAction
import com.cartoonHero.source.redux.appStore
import com.cartoonhero.source.actormodel.Actor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*
import java.util.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class SearchLocationScenario constructor(
    private val context: Context,private val activity: Activity): Actor() {
    private var queryDataParcel: Parcel? = null
    private var markedGQInput = initGQInputObject()
    private val pilot = Pilot(context)
    private val coder = GeoCoder(context)

    fun toBeGoogleSearchUrl(queryText: String, complete: (String) -> Unit) {
        send {
            beGoogleSearchUrl(queryText, complete)
        }
    }
    fun toBeCheckGPSPermission(complete: (Boolean) -> Unit) {
        send {
            beCheckGPSPermission(complete)
        }
    }
    fun toBeRequestCurrentLocation() {
        send {
            beRequestCurrentLocation()
        }
    }
    fun toBeInquireIntoAddressesLocation(addressText: String) {
        send {
            beInquireIntoAddressesLocation(addressText)
        }
    }
    private fun toBeInquireIntoLocationAddress(
        location: Location,locale: Locale) {
        send {
            beInquireIntoLocationAddress(location, locale)
        }
    }
    fun toBeGetQueryDataMarker(
        queryData: List<LocationsDynamicQuery.LocationsDynamicQuery?>,
        complete: (List<MarkerOptions>) -> Unit) {
        send {
            beGetQueryDataMarker(queryData, complete)
        }
    }
    fun toBeGetFoundPlacesMarkers(
        complete: (List<MarkerOptions>) -> Unit) {
        send {
            beGetFoundPlacesMarkers(complete)
        }
    }
    fun toBePrepareGoFoundLocScenario(complete: (Boolean) -> Unit) {
        send {
            bePrepareGoFoundLocScenario(complete)
        }
    }
    fun toBeCancelFoundLocParcel() {
        send {
            beCancelFoundLocParcel()
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
        pilot.toBeCheckPermission(this,activity) {
            CoroutineScope(Dispatchers.Main).launch {
                complete(it)
            }
        }
    }
    private fun beRequestCurrentLocation() {
        pilot.toBeRequestLocationUpdates(
            this,0L,0.0F) {
            enable: Boolean, location: Location? ->
            if (enable && location != null) {
                beInquireIntoLocationAddress(location, Locale.ROOT)
            }
        }
    }
    private fun beInquireIntoAddressesLocation(addressText: String) {
        coder.toBeGetFromLocationName(this,addressText) {
            val location = Location(LocationManager.GPS_PROVIDER)
            location.latitude = it.latitude
            location.longitude = it.longitude
            beInquireIntoLocationAddress(location,it.locale)
            CoroutineScope(Dispatchers.Main).launch {
                appStore.dispatch(MapRemoveAllAnnotationsAction())
                toBeInquireIntoLocationAddress(
                    location,isoNationCodeToLocale(it.countryCode))
            }
        }
    }
    private fun beInquireIntoLocationAddress(location: Location,locale: Locale) {
        coder.toBeGetFromLocation(
            this,location,locale) {
            DataManager().toBeConvertAddressesToInputAddresses(
                this, listOf(it)) { result ->
                if (result.isNotEmpty()) {
                    val inputObj = initGQInputObject()
                    inputObj.address = result.first()
                    when(locale) {
                        Locale.ROOT -> {
                            toBeInquireIntoAddressesLocation(inputObj.address.completeInfo)
                        }
                        else -> {
                            markedGQInput = inputObj
                            CoroutineScope(Dispatchers.Main).launch {
                                appStore.dispatch(FoundLocationsAddressAction(
                                    markedGQInput
                                ))
                            }
                        }
                    }
                }
            }
            if (locale != Locale.ROOT) {
                DataManager().toBeConvertAddressToAddressDqCmd(this,it) {
                    CoroutineScope(Dispatchers.Main).launch {
                        appStore.dispatch(
                            locationsDynamicQueryAction(null,it)
                        )
                    }
                }
            }
        }
    }
    private fun beGetQueryDataMarker(
        queryData: List<LocationsDynamicQuery.LocationsDynamicQuery?>,
        complete: (List<MarkerOptions>) -> Unit) {
        DataManager().toBeConvertLocDQDataToGQInputObjects(this,queryData) {
            queryDataParcel = LogisticsCenter.applyExpressService(
                this,"FoundLocScenario",it)
            val data = it.first()
            val latitude = data.address.latitude as Double
            val longitude = data.address.longitude as Double
            val position = LatLng(latitude,longitude)
            val marker = MarkerOptions()
            marker.position(position)
            CoroutineScope(Dispatchers.Main).launch {
                complete(listOf(marker))
            }
        }
    }
    private fun beGetFoundPlacesMarkers(complete: (List<MarkerOptions>) -> Unit) {
        val latitude = markedGQInput.address.latitude as Double
        val longitude = markedGQInput.address.longitude as Double
        val position = LatLng(latitude,longitude)
        val marker = MarkerOptions()
        marker.position(position)
        CoroutineScope(Dispatchers.Main).launch {
            complete(listOf(marker))
        }
    }
    private fun bePrepareGoFoundLocScenario(complete: (Boolean) -> Unit) {
        var isPrepared = false
        if (queryDataParcel != null) {
            queryDataParcel = null
            isPrepared = true
        } else {
            LogisticsCenter
                .applyExpressService(this,"AddGourmetScenario",markedGQInput)
        }
        CoroutineScope(Dispatchers.Main).launch {
            complete(isPrepared)
        }
    }
    private fun beCancelFoundLocParcel() {
        if (queryDataParcel != null) {
            LogisticsCenter.cancelService(
                "FoundLocScenario", queryDataParcel!!)
            queryDataParcel = null
        }
    }
}