package com.cartoonHero.source.redux.actions

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.apollographql.apollo.api.Input
import com.cartoonHero.source.actors.dataManger.GQCreateObject
import com.cartoonHero.source.redux.appStore
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*
import org.rekotlin.Action
import type.AddressDqCmd
import type.InputAddress
import type.InputBranch
import type.InputShop
import java.lang.StringBuilder
import java.util.*

enum class GeoActionStatus {
    Started, Completed, NotFound, Failed
}

// 地址轉成經緯度
class GeoCodeAddressAction constructor(val codedAddress: String) : Action {
    var status: GeoActionStatus = GeoActionStatus.Started
    var address: Address? = null
}
fun geoCodeAddressAction(context: Context,address: String): GeoCodeAddressAction {
    val action = GeoCodeAddressAction(address)
    val geoCoder = Geocoder(context, Locale.getDefault())
    CoroutineScope(Dispatchers.IO).launch {
        val transJob:Deferred<List<Address>> =
            async {
                return@async geoCoder.getFromLocationName(address,1)
            }
        withContext(Dispatchers.Main) {
            val results = transJob.await()
            if (results.isNotEmpty()) {
                action.address = results.first()
                action.status = GeoActionStatus.Completed
            } else {
                action.status = GeoActionStatus.NotFound
            }
            appStore.dispatch(action)
        }
    }
    return action
}
// 經緯度轉地址
class ReverseLocationAction constructor(val reversedLoc: Location) : Action {
    var status: GeoActionStatus = GeoActionStatus.Started
    var address: Address? = null
}

fun reverseLocationAction(context: Context,location: Location): ReverseLocationAction{
    val action = ReverseLocationAction(location)
    val geoCoder = Geocoder(context, Locale.getDefault())
    CoroutineScope(Dispatchers.IO).launch {
        val transJob:Deferred<List<Address>> =
            async {
             return@async geoCoder.getFromLocation(
                location.latitude, location.longitude, 1
            )
        }
        withContext(Dispatchers.Main) {
            val results = transJob.await()
            if (results.isNotEmpty()) {
                action.address = results.first()
                action.status = GeoActionStatus.Completed
            } else {
                action.status = GeoActionStatus.NotFound
            }
            appStore.dispatch(action)
        }
    }
    return action
}

class ParseAndQueryLocAction constructor(isNeedQuery: Boolean, val address: Address): Action {

    init {
        if (isNeedQuery) {
            val dqCmd = parseAddressToAddressDqCmd()
            appStore.dispatch(locationsDynamicQueryAction(dqCmd))
        }
        parseToGQCreateObject()
    }

    private fun parseToGQCreateObject(): GQCreateObject {
        val inputAddress = InputAddress(
            latitude = address.latitude,
            longitude = address.longitude,
            nation = Input.optional(address.countryName),
            isoNationCode = Input.optional(address.countryCode),
            locality = Input.optional(address.locality),
            subLocality = Input.optional(address.subLocality),
            administrativeArea = Input.optional(address.adminArea),
            subAdministrativeArea = Input.optional(address.subAdminArea),
            postalCode = Input.optional(address.postalCode),
            thoroughfare = Input.optional(address.thoroughfare),
            subThoroughfare = Input.optional(address.subThoroughfare),
            completeInfo = ""
        )
        return GQCreateObject(
            address = inputAddress,
            shopBranch = InputBranch(
                name = ""
            ),
            shop = InputShop(
                title = "",
                underPrice = 0.0
            )
        )
    }
    private fun parseAddressToAddressDqCmd(): AddressDqCmd {
        return AddressDqCmd(
            nation = Input.optional(address.countryName),
            isoNationCode = Input.optional(address.countryCode),
            locality = Input.optional(address.locality),
            subLocality = Input.optional(address.subLocality),
            administrativeArea = Input.optional(address.adminArea),
            subAdministrativeArea = Input.optional(address.subAdminArea),
            postalCode = Input.optional(address.postalCode),
            thoroughfare = Input.optional(address.thoroughfare),
            subThoroughfare = Input.optional(address.subThoroughfare),
        )
    }
}

class MarLocationOnMapAction constructor(
    val inputObjects: List<GQCreateObject>): Action {
    var actionType = ""
    var status = GeoActionStatus.Started
    var annotations = mutableListOf<MarkerOptions>()
}
fun marLocationOnMapAction(inputObjects: List<GQCreateObject>): MarLocationOnMapAction {
    val action = MarLocationOnMapAction(inputObjects)
    action.annotations.clear()
    CoroutineScope(Dispatchers.IO).launch {
        for (gqObj in inputObjects) {
            val markerOpt = MarkerOptions()
            val titleBuilder = StringBuilder(gqObj.shop.title)
                .append("\n").append(gqObj.shopBranch.name)
            markerOpt.title(titleBuilder.toString())
            val latitude = gqObj.address.latitude as Double
            val longitude = gqObj.address.longitude as Double
            markerOpt.position(LatLng(latitude,longitude))
            action.annotations.add(markerOpt)
        }
        withContext(Dispatchers.Main) {
            action.status = GeoActionStatus.Completed
            appStore.dispatch(action)
        }
    }
    return action
}