package com.cartoonHero.source.actors.dataManger

import android.location.Address
import com.apollographql.apollo.api.Input
import com.cartoonHero.source.props.convertAnyToJson
import com.cartoonHero.source.props.toAny
import com.cartoonHero.source.props.toJson
import com.cartoonHero.source.props.toMap
import com.cartoonHero.source.props.enities.GQInputObject
import com.cartoonHero.source.props.enities.initInputAddress
import com.cartoonHero.source.props.enities.initInputBranch
import com.cartoonhero.source.actormodel.Actor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import type.AddressDqCmd
import type.InputAddress
import type.InputBranch

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class DataManager: Actor() {
    fun toBeConvertAddressesToInputAddresses(
        sender: Actor, addresses: List<Address>,
        complete: (List<InputAddress>) -> Unit) {
        send {
            beConvertAddressesToInputAddresses(
                sender, addresses, complete)
        }
    }
    fun toBeConvertAddressToAddressDqCmd(
        sender: Actor,address: Address,complete: (AddressDqCmd) -> Unit) {
        send {
            beConvertAddressToAddressDqCmd(sender, address, complete)
        }
    }
    fun toBeConvertLocDQDataToGQInputObjects(
        sender: Actor,
        queryData:List<LocationsDynamicQuery.LocationsDynamicQuery?>,
        complete: (List<GQInputObject>) -> Unit) {
        send {
            beConvertLocDQDataToGQInputObjects(sender, queryData, complete)
        }
    }
    fun toBeSearchRangeDataToLocDqCmd(
        sender: Actor,searchData: SearchForRangeQuery.SearchForRange,
        complete: (AddressDqCmd) -> Unit) {
        send {
            beSearchRangeDataToLocDqCmd(sender, searchData, complete)
        }
    }

    private fun beConvertAddressesToInputAddresses(
        sender: Actor, addresses: List<Address>,
        complete: (List<InputAddress>) -> Unit) {
        val result = mutableListOf<InputAddress>()
        for (address in addresses) {
            val newAddress = InputAddress(
                completeInfo = combineAddressCompleteInfo(address),
                latitude = address.latitude,
                longitude = address.longitude,
                nation = address.countryName,
                isoNationCode = address.countryCode,
                locality = address.locality,
                subLocality = address.subLocality,
                administrativeArea = address.adminArea,
                subAdministrativeArea = address.subAdminArea,
                thoroughfare = address.thoroughfare,
                subThoroughfare = address.subThoroughfare
            )
            result.add(newAddress)
        }
        sender.send {
            complete(result)
        }
    }
    private fun beConvertAddressToAddressDqCmd(
        sender: Actor,address: Address,complete: (AddressDqCmd) -> Unit) {
        val dqCMD = AddressDqCmd(
            nation = Input.optional(address.countryName),
            isoNationCode = Input.optional(address.countryCode),
            locality = Input.optional(address.locality),
            subLocality = Input.optional(address.subLocality),
            administrativeArea = Input.optional(address.adminArea),
            subAdministrativeArea = Input.optional(address.subAdminArea),
            postalCode = Input.optional(address.postalCode),
            thoroughfare = Input.optional(address.thoroughfare),
            subThoroughfare = Input.optional(address.subThoroughfare)
        )
        sender.send {
            complete(dqCMD)
        }
    }
    private fun beConvertLocDQDataToGQInputObjects(
        sender: Actor,
        queryData:List<LocationsDynamicQuery.LocationsDynamicQuery?>,
        complete: (List<GQInputObject>) -> Unit) {
        if (queryData.isNotEmpty()) {
            val result = mutableListOf<GQInputObject>()
            for (data in queryData) {
                val branchJson = convertAnyToJson(data?.shopBranch)
                val inputBranch = branchJson.toAny<InputBranch>()
                val dataJsonMap = convertAnyToJson(data).toMap<Any>()?.toMutableMap()
                dataJsonMap?.remove("shopBranch")
                val inputAddress = dataJsonMap?.toJson()?.toAny<InputAddress>()
                val inputObj = GQInputObject(
                    branchId = data?.shopBranch?.uniqueId ?: "",
                    address = inputAddress ?: initInputAddress(),
                    shopBranch = inputBranch ?: initInputBranch()
                )
                result.add(inputObj)
            }
            sender.send {
                complete(result)
            }
        }
    }
    private fun beSearchRangeDataToLocDqCmd(
        sender: Actor,searchData: SearchForRangeQuery.SearchForRange,
        complete: (AddressDqCmd) -> Unit) {
        val dqCmd = AddressDqCmd(
            nation = Input.optional(searchData.nation),
            isoNationCode = Input.optional(searchData.isoNationCode),
            locality = Input.optional(searchData.locality),
            subLocality = Input.optional(searchData.subLocality),
            administrativeArea = Input.optional(searchData.administrativeArea),
            subAdministrativeArea = Input.optional(searchData.subAdministrativeArea),
            postalCode = Input.optional(searchData.postalCode),
            thoroughfare = Input.optional(searchData.thoroughfare),
            subThoroughfare = Input.optional(searchData.subThoroughfare)
        )
        sender.send {
            complete(dqCmd)
        }
    }

    private fun combineAddressCompleteInfo(address: Address): String {
        val stringBuilder = StringBuilder(address.adminArea)
            .append(address.subAdminArea).append(address.locality)
            .append(address.thoroughfare).append(" ")
            .append(address.subThoroughfare)
        return stringBuilder.toString()
    }

}